package it.polimi.ingsw.ps51.controller;

import it.polimi.ingsw.ps51.controller.gods.GodController;
import it.polimi.ingsw.ps51.controller.gods.GodsControllerFactory;
import it.polimi.ingsw.ps51.controller.gods.NormalGodsController;
import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.*;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.CardFactory;
import it.polimi.ingsw.ps51.model.gods.CommonAction;
import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.utility.GameObserver;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.Observer;

import java.util.*;
import java.util.Map;

/**
 * The class manage a game match, from its setup to the end of it
 */
public class Game extends Observable<EventForClient> implements GameObserver {

    private Playground gameRoom;
    private List<Gods> godsDeck;
    private VisitorController visitor;
    private Player actualPlayer;
    private Map<Player, GodController> godControllersMap;
    protected ThirdPhase thirdPhase = new ThirdPhase();

    /**
     * This is the constructor of the class
     * @param gameRoom it's the {@code Playground} that gives to the controller the access to the model
     */
    public Game(Playground gameRoom) {
        this.gameRoom = gameRoom;
        godControllersMap = new HashMap<>();
        visitor = new VisitorController(this);
    }

    /**
     * This method start the procedure to setup correctly the playground, assigning gods to the player and place their
     * workers on the map, this method in particular extract casually a player and ask him to choose as many gods as the
     * number of players in the match
     */
    public void startGame() {
        actualPlayer = gameRoom.getPlayers().get(new Random().nextInt(gameRoom.getPlayers().size()));
        notify(new ChooseGodsDeck(actualPlayer.getNickname()));
    }

    /**
     * This method continues the setup of the match, it's the phase two, where, after receiving the list of gods from
     * the challenger, is asked to each player to choose a god from the previously mentioned list. The first player that
     * is asked to choose is the successive of the challenger and so on until all the chose a god
     * @param godsList the list of gods chosen by the challenger
     */
    public void startGamePhaseTwo(List<Gods> godsList){
        godsDeck = new ArrayList<>(godsList);
        actualPlayer = gameRoom.getNextPlayer();
        notify(new ChooseGod(actualPlayer.getNickname(), godsDeck));
    }

    /**
     * This method assign to the actual player the God he chose, initialize all the needed classes as the right controller
     * for the game turn and update the map that save the correspondences between player and relative GodController, finally
     * asks to the next player to choose a god from the remaining ones if he doesn't already done this.
     * When all the player have chosen a god the method start the thirdPhase
     * @param god the God chosen by the actual player
     */
    public void assignController(Gods god){
        Card card = CardFactory.getCard(god);
        actualPlayer.setGod(card);

        godControllersMap.put(actualPlayer, GodsControllerFactory.getController(god, actualPlayer, gameRoom.getBoardMap(),card));
        ((NormalGodsController) godControllersMap.get(actualPlayer)).addGame(this);
        for (Observer observer : this.copyObservers()){
            ((NormalGodsController) godControllersMap.get(actualPlayer)).addObserver(observer);
        }


        actualPlayer = gameRoom.getNextPlayer();
        if (actualPlayer.getGod() == null){
            godsDeck.remove(god);
            notify(new ChooseGod(actualPlayer.getNickname(), godsDeck));
        }else {
            thirdPhase.start();
        }
    }

    /**
     * This thread is needed to manage the third phase of the setup, that one where the players must choose where
     * to collocates their workers
     */
    protected class ThirdPhase extends Thread{

        private Coordinates position = null;

        /**
         * The method cycles through all the players, and ask them where they want to collocates their workers.
         * The asking procedure is done for one worker at a time, when the request is sent the thread go to wait() until
         * an answer, when the answer is received, if it's applicable the worker is created and put on the map, otherwise
         * the request is redone. After having placed two worker for each player the first turn start. The first
         * player that moves is the subsequent of the challenger
         */
        @Override
        public void run() {
            try {
                for (int player = 0; player < gameRoom.getPlayers().size(); player++){
                    int workerNum = 1;
                    while (workerNum < 3){
                        Game.this.notify(new ChooseWorkerPosition(actualPlayer.getNickname(), workerNum));

                        while (position == null){
                            synchronized (this){
                                wait();
                            }
                        }

                        Square square = gameRoom.getBoardMap().getSquare(position.getX(), position.getY());
                        if (!square.isPresentWorker()){
                            actualPlayer.addWorker(new Worker(actualPlayer.getNickname(), square));
                            position = null;
                            workerNum++;
                        }
                    }

                    actualPlayer = gameRoom.getNextPlayer();
                }

                finalizeGameSetting();
                getActualController().start();
            } catch (InterruptedException | OutOfMapException e) {
                e.printStackTrace();
            }
        }

        /**
         * The method initialize correctly all the observers for the God's cards
         */
        private void finalizeGameSetting(){
            for (Player player : gameRoom.getPlayers()){
                for (Player opponent : gameRoom.getPlayers()){
                    if (!opponent.equals(player)){
                        for (Worker worker : opponent.getWorkers()){
                            ((CommonAction) player.getGod()).addObserver(worker);
                        }
                    }
                }
            }
        }

        /**
         * @param coordinates the coordinates where the player want to collocates one of his worker
         */
        public void setPosition(Coordinates coordinates){
            position = coordinates;
            synchronized (this){
                notifyAll();
            }
        }
    }


    /**
     * The method retrieve the GodController associated to the actual player
     * @return the GodController of the actual player
     */
    public GodController getActualController(){
        return godControllersMap.get(actualPlayer);
    }

    /**
     * Manage the receiving of a message coming from the client, the management is delegated to the visitor
     * @param message the object which have to be updated
     */
    @Override
    public void update(EventForServer message) {
        message.acceptVisitor(visitor);
    }

    /**
     * Menage a message coming from the GodController of the actual player
     * @param message the event sent by the GodController
     */
    @Override
    public void update(ControllerToGame message) {
        switch (message){
            case END_TURN:
                actualPlayer = gameRoom.getNextPlayer();
                getActualController().start();
                break;
            case LOSER:
                notify(new Lose(actualPlayer.getNickname()));
                if (gameRoom.getPlayers().size() > 2){
                    Player toRemove = actualPlayer;
                    actualPlayer = gameRoom.getNextPlayer();
                    gameRoom.removePlayer(toRemove);
                    getActualController().start();
                }else {
                    actualPlayer = gameRoom.getNextPlayer();
                    notify(new Win(actualPlayer.getNickname()));
                }
                break;
            case WINNER:
                notify(new Win(actualPlayer.getNickname()));
                Player toRemove = actualPlayer;
                actualPlayer = gameRoom.getNextPlayer();
                gameRoom.removePlayer(toRemove);
                for (Player player : gameRoom.getPlayers()){
                    notify(new Lose(player.getNickname()));
                }
                break;
        }
    }
}
