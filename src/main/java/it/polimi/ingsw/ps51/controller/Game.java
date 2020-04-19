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

public class Game extends Observable<EventForClient> implements GameObserver {

    private Playground gameRoom;
    private List<Gods> godsDeck;
    private VisitorController visitor;
    private Player actualPlayer;
    private Map<Player, GodController> godControllersMap;
    protected ThirdPhase thirdPhase = new ThirdPhase();

    public Game(Playground gameRoom) {
        this.gameRoom = gameRoom;
        godControllersMap = new HashMap<>();
        visitor = new VisitorController(this);
    }

    public void startGame() {
        actualPlayer = gameRoom.getPlayers().get(new Random().nextInt(gameRoom.getPlayers().size()));
        notify(new ChooseGodsDeck(actualPlayer.getNickname()));
    }

    public void startGamePhaseTwo(List<Gods> godsList){
        godsDeck = new ArrayList<>(godsList);
        actualPlayer = gameRoom.getNextPlayer();
        notify(new ChooseGod(actualPlayer.getNickname(), godsDeck));
    }

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

    protected class ThirdPhase extends Thread{

        private Coordinates position = null;

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

        public void setPosition(Coordinates coordinates){
            position = coordinates;
            synchronized (this){
                notifyAll();
            }
        }
    }


    public GodController getActualController(){
        return godControllersMap.get(actualPlayer);
    }

    @Override
    public void update(EventForServer message) {
        message.acceptVisitor(visitor);
    }

    @Override
    public void update(ControllerToGame message) {
        switch (message){
            case END_TURN:
                actualPlayer = gameRoom.getNextPlayer();
                godControllersMap.get(actualPlayer).start();
                break;
            case LOSER:
                notify(new Lose(actualPlayer.getNickname()));
                if (gameRoom.getPlayers().size() > 2){
                    Player toRemove = actualPlayer;
                    actualPlayer = gameRoom.getNextPlayer();
                    gameRoom.removePlayer(toRemove);
                    godControllersMap.get(actualPlayer).start();
                }else {
                    int loserIndex = gameRoom.getPlayers().indexOf(actualPlayer);
                    int winnerIndex = (loserIndex + 1) % gameRoom.getPlayers().size();
                    notify(new Win(gameRoom.getPlayers().get(winnerIndex).getNickname()));
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
