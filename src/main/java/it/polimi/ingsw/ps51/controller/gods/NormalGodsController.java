package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.*;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.utility.GodControllerObservable;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This calls is a generic game turn controller, it implement the mechanics to manage a normal game turn, firstly the
 * actual player decide which of its worker he want to use, then moves it and finally build a block
 */
public class NormalGodsController extends GodControllerObservable implements GodController{
    Card card;
    Map map;
    Player player;
    Worker selectedWorker;

    public NormalGodsController(Card card, Map map, Player player) {
        this.card = card;
        this.map = map;
        this.player = player;
    }

    /**
     * This method is that one which must be called to start the turn, it retrieves the list of the workers that can be
     * moved and creates a {@code ChooseWorker} event which is sent to the actual player who choose the worker to use,
     * if none of the workers of the actual player can be moved {@code Game} will be advised that the actual player
     * lost the match
     */
    public void start(){
        List<Worker> validWorkers = new ArrayList<>();

        for (Worker worker : player.getWorkers()){
            if (!card.checkMoves(player, worker, map).isEmpty()){
                validWorkers.add(worker);
            }
        }
        if (validWorkers.isEmpty()){
            notifyToGame(ControllerToGame.LOSER);
        }else {
            notify(new ChooseWorker(validWorkers, player.getNickname()));
        }
    }

    /**
     * The method retrieves the list of the valid positions in which the worker can be moved
     * and send it to the actual player through a {@code ChooseMove} event
     */
    public void searchForMoves(){
        List<Coordinates> validMoves;

        validMoves = card.checkMoves(player, selectedWorker, map);
        notify(new ChooseMove(validMoves, player.getNickname()));
    }

    /**
     * This method move the worker to the selected position and then, if the actual player isn't in
     * a winning condition, retrieve the list of the possible build actions that can be done and send them to the
     * player through a {@code ChooseBuild} event; otherwise if the player is in a winning condition
     * the {@code Game} is advised of this
     * @param moveTo the coordinates where the worker must be moved
     */
    public void effectuateMove(Coordinates moveTo){
        try {
            Square square = map.getSquare(moveTo.getX(), moveTo.getY());
            card.move(player, selectedWorker, square, map);
            if (isWinner()){
                notifyToGame(ControllerToGame.WINNER);
            }
            searchForBuild();
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method implements the search of the square where a new level can be built and send the information to the player
     * @see #effectuateMove(Coordinates)
     */
    public void searchForBuild(){
        List<Pair<Coordinates, List<Level>>> validBuilds;

        validBuilds = card.checkBuild(selectedWorker, map);
        notify(new ChooseBuild(validBuilds, player.getNickname()));
    }

    /**
     * The method perform the build action and if the worker is in a winning condition {@code game} will
     * be advised of this, otherwise {@code Game} will be informed that the turn is ended
     * @param coordinates the coordinates where the new level must be built
     * @param level the level that must be built
     */
    public void build(Coordinates coordinates, Level level){
        try {
            Square square = map.getSquare(coordinates.getX(), coordinates.getY());
            card.build(selectedWorker, square, level, map);
            if (isWinner()){
                notifyToGame(ControllerToGame.WINNER);
            }else {
                notifyToGame(ControllerToGame.END_TURN);
            }
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verify if any of the workers of the actual player is in a winning condition
     * @return true if at least one of the worker is in a winning condition, otherwise false is returned
     */
    protected boolean isWinner(){
        for (Worker worker : player.getWorkers()){
            if (worker.getInWinningCondition()){
                return true;
            }
        }
        return  false;
    }

    /**
     * The method is called as a consequence of receiving a {@code WorkerChoice} event that specifies which of the player's
     * workers must be selected to perform the turn
     * @param worker the selected worker
     */
    @Override
    public void manageWorkerChoice(Worker worker) {
        for (Worker worker1 : player.getWorkers()){
            if (worker1.equals(worker)){
                selectedWorker = worker1;
            }
        }
        searchForMoves();
    }

    /**
     * The method is called as a consequence of receiving a {@code MoveChoice} event that specifies where to move the
     * selected worker
     * @param moveTo the coordinates where the worker must be moved
     */
    @Override
    public void manageMoveChoice(Coordinates moveTo) {
        effectuateMove(moveTo);
    }

    /**
     * The method is called as a consequence of receiving a {@code Build} event that specifies where and at which level
     * must be built a new tower's level.
     * @param buildOn the coordinates where the new level must be built
     * @param level the level that must be built
     */
    @Override
    public void manageBuildChoice(Coordinates buildOn, Level level) {
        build(buildOn, level);
    }

}
