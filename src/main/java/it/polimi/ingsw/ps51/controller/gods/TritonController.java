package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.MakeDecision;
import it.polimi.ingsw.ps51.events.events_for_client.UnsuccessfulOperation;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.Square;
import it.polimi.ingsw.ps51.model.gods.Card;

public class TritonController extends NormalGodsController implements GodControllerWithDecision {


    public TritonController(Card card, Map map, Player player) {
        super(card, map, player);
    }

    @Override
    public void performMove(Coordinates moveTo) {
        try {
            Square square = map.getSquare(moveTo.getX(), moveTo.getY());
            card.move(player, selectedWorker, square, map);
            if (isWinner()){
                notifyToGame(ControllerToGame.WINNER);
            }else if (isPowerUsable()){
                notify(new MakeDecision(player.getNickname(), "You are in a perimeter square, do you want to move again?"));
            }else {
                searchForBuild();
            }
        } catch (OutOfMapException e) {
            notify(new UnsuccessfulOperation(player.getNickname()));
            searchForMoves();
        }
    }

    @Override
    public void decisionManager(boolean takenDecision) {
        if (takenDecision){
            searchForMoves();
        }else {
            searchForBuild();
        }
    }

    private boolean isPowerUsable(){
        return map.isThisPerimeterSquare(selectedWorker.getPosition());
    }
}
