package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.Ack;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseMove;
import it.polimi.ingsw.ps51.events.events_for_client.MakeDecision;
import it.polimi.ingsw.ps51.events.events_for_client.UnsuccessfulOperation;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the logic to manage a turn done by a player who have the Prometheus god. The player can build
 * also before moving if and only if when he will move his worker he don't moves it up
 */
public class PrometheusController extends NormalGodsController implements GodControllerWithDecision{

    private boolean useGodPower = false;

    public PrometheusController(Card card, Map map, Player player) {
        super(card, map, player);
    }

    /**
     * The method retrieves the list of the coordinates where the worker can be moved and send a {@code ChooseMove}
     * event to the player with this list.
     * If the power of the god is used, before sending the list to the player the coordinates that impose to the worker
     * to moves up are removed from the list and set back the attribute {@code useGodPower} to false
     */
    @Override
    public void searchForMoves() {
        List<Coordinates> validPositions;

        validPositions = card.checkMoves(player, selectedWorker, map);

        if (useGodPower){
            try {
                removeMoveUp(validPositions);
            } catch (OutOfMapException e) {
                searchForMoves();
                return;
            }
            useGodPower = false;
        }

        notify(new ChooseMove(validPositions, player.getNickname()));
    }

    /**
     * The method implements the build action when this one is done before moving the player, it's similar to the standard
     * build except for the fact that at the end of it the turn doesn't end, but the method searchForMoves() will be called
     * @param buildOn the coordinates where the new level must be built
     * @param level the level that must be built
     */
    protected void preBuild(Coordinates buildOn, Level level){
        try {
            Square square = map.getSquare(buildOn.getX(), buildOn.getY());
            card.build(selectedWorker, square, level, map);
            notify(new Ack(player.getNickname(), "Build before Move"));
            if (isWinner()){
                notifyToGame(ControllerToGame.WINNER);
            }else {
                searchForMoves();
            }
        } catch (OutOfMapException e) {
            notify(new UnsuccessfulOperation(player.getNickname()));
            searchForBuild();
        }
    }

    /**
     * The method removes from a list of {@code Coordinates} that ones that force the selected worker to moves up
     * @param positions the list of {@code Coordinates}
     */
    private void removeMoveUp(List<Coordinates> positions) throws OutOfMapException {
        List<Coordinates> temp = new ArrayList<>();

        for (Coordinates coordinates : positions){
            Level workerLevel = selectedWorker.getPosition().getLevel();
            Level positionLevel = map.getSquare(coordinates).getLevel();
            if (workerLevel.ordinal() - positionLevel.ordinal() < 0){
                temp.add(coordinates);
            }
        }

        positions.removeAll(temp);
    }


    /**
     * The method is called as a consequence of receiving a {@code WorkerChoice} event that specifies which of the player's
     * workers must be selected to perform the turn.
     * Then check if the worker can be moved without moving it up, if it's possible, send to the player a {@code MakeDecision}
     * event to ask him if wants to use or not the god's power
     * @param worker the selected worker
     */
    @Override
    public void manageWorkerChoice(Worker worker) {
        List<Coordinates> validPositions;
        //Assign the chosen worker
        for (Worker worker1 : player.getWorkers()){
            if (worker1.equals(worker)){
                selectedWorker = worker1;
            }
        }
        //Search if the selected worker can move without moving up, in the last case send to the player a MakeChoice event
        validPositions = card.checkMoves(player, selectedWorker, map);
        for (Coordinates coordinates : validPositions){
            try {
                Level workerLevel = selectedWorker.getPosition().getLevel();
                Level positionLevel = map.getSquare(coordinates).getLevel();
                if (workerLevel.ordinal() - positionLevel.ordinal() >= 0){
                    notify(new Ack(player.getNickname(), "Worker choice"));
                    notify(new MakeDecision(player.getNickname(), "Do you want to build before move?, If you do this you can't move up"));
                    return;
                }
            } catch (OutOfMapException e) {
                manageWorkerChoice(worker);
                return; ////this abort the previous call to this method that raise the exception
            }
        }
        notify(new Ack(player.getNickname(), "Worker choice"));
        searchForMoves();
    }

    /**
     * The method is called as a consequence of receiving a {@code DecisionTaken} event, the decision is about building
     * before moving or not
     * @param takenDecision is true if the player want to build before moving, false if the player want to play a
     *                      classical turn
     */
    public void decisionManager(boolean takenDecision){
        if (takenDecision){
            useGodPower = true;
            searchForBuild();
        }else {
            searchForMoves();
        }
    }

    /**
     * The method is called as a consequence of receiving a {@code Build} event, if {@code useGodPower} is true this is
     * the build done before moving, so the method preBuild() will be called,otherwise the standard build() method will be called
     * @param buildOn the coordinates where the new level must be built
     * @param level the level that must be built
     */
    @Override
    public void manageBuildChoice(Coordinates buildOn, Level level) {
        if (useGodPower){
            preBuild(buildOn, level);
        }else {
            build(buildOn, level);
        }
    }
}
