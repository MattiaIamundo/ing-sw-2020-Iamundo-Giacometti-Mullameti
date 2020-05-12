package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseBuild;
import it.polimi.ingsw.ps51.events.events_for_client.MakeDecision;
import it.polimi.ingsw.ps51.events.events_for_client.UnsuccessfulOperation;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import org.javatuples.Pair;

import java.util.List;

public class HestiaController extends NormalGodsController implements GodControllerWithDecision{

    private boolean secondBuild = false;
    private List<Pair<Coordinates, List<Level>>> secondBuildPositions;

    public HestiaController(Card card, Map map, Player player) {
        super(card, map, player);
    }


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
                secondBuildPositions.removeIf(c -> map.isThisPerimeterSquare(c.getValue0()));
                if (secondBuildPositions.isEmpty()){
                    notifyToGame(ControllerToGame.END_TURN);
                }else {
                    notify(new MakeDecision(player.getNickname(), "Do you want to build again?, you cannot build in a perimeter space"));
                }
            } catch (OutOfMapException e) {
                notify(new UnsuccessfulOperation(player.getNickname()));
                searchForBuild();
            }
        }
    }

    @Override
    public void decisionManager(boolean takenDecision) {
        if (takenDecision){
            secondBuild = true;
            notify(new ChooseBuild(secondBuildPositions, player.getNickname()));
        }else {
            notifyToGame(ControllerToGame.END_TURN);
        }
    }
}
