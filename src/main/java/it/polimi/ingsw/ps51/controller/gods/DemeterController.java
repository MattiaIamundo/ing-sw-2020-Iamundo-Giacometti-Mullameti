package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.controller.Game;
import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.Ack;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseBuild;
import it.polimi.ingsw.ps51.events.events_for_client.MakeDecision;
import it.polimi.ingsw.ps51.events.events_for_client.UnsuccessfulOperation;
import it.polimi.ingsw.ps51.events.events_for_server.DecisionTaken;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import org.javatuples.Pair;

import java.util.List;

public class DemeterController extends NormalGodsController implements GodControllerWithDecision{

    private boolean secondBuild = false;
    private List<Pair<Coordinates, List<Level>>> secondBuildPositions;

    public DemeterController(Card card, Map map, Player player) {
        super(card, map, player);
    }

    /**
     * The method is modified to cope with the gods power of Demeter that permits to build twice, but not on the same
     * square. The first build is activated as normal, but at the end of the procedure, if the player doesn't win,
     * the list of the possible square where a level can be built, excluding the just built one, is saved into {@code secondBuildPositions}
     * a {@link MakeDecision} event will be sent to ask if the player want to build one more level.
     * For the second build the {@code secondBuild} will be set back to false and the super() method will be called
     * @param buildOn the coordinates where the new level must be built
     * @param level the level that must be built
     */
    @Override
    public void build(Coordinates buildOn, Level level) {
        if (secondBuild){
            secondBuild = false;
            super.build(buildOn, level);
        }else {
            try {
                Square square = map.getSquare(buildOn.getX(), buildOn.getY());
                card.build(selectedWorker, square, level, map);
                if (isWinner()){
                    notifyToGame(ControllerToGame.WINNER);
                    return;
                }
                secondBuildPositions = card.checkBuild(selectedWorker, map);
                secondBuildPositions.removeIf(c -> c.getValue0().equals(buildOn));
                if (secondBuildPositions.isEmpty()){
                    notifyToGame(ControllerToGame.END_TURN);
                }else {
                    notify(new MakeDecision(player.getNickname(), "Do you want to build again?"));
                }
            } catch (OutOfMapException e) {
                notify(new UnsuccessfulOperation(player.getNickname()));
                searchForBuild();
            }
        }
    }

    /**
     * The method is called as the consequence of receiving a {@link DecisionTaken} event, if the decision is true the
     * boolean {@code secondBuild} is set to true and a {@link ChooseBuild} event is sent to the player using as list of
     * valid positions the previously saved list by method build(Coordinates, Level). If the decision received is false
     * the {@link Game} will be notified that the turn is ended
     */
    public void decisionManager(boolean takenDecision){
        if (takenDecision){
            secondBuild = true;
            notify(new ChooseBuild(secondBuildPositions, player.getNickname()));
        }else {
            notifyToGame(ControllerToGame.END_TURN);
        }
    }
}
