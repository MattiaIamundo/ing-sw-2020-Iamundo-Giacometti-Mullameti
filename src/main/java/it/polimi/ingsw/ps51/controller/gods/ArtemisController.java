package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseMove;
import it.polimi.ingsw.ps51.events.events_for_client.MakeDecision;
import it.polimi.ingsw.ps51.events.events_for_client.UnsuccessfulOperation;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.Square;
import it.polimi.ingsw.ps51.model.gods.Card;

import java.util.List;

public class ArtemisController extends NormalGodsController implements GodControllerWithDecision{

    private boolean usePower = false;
    private Coordinates movedFrom;

    public ArtemisController(Card card, Map map, Player player) {
        super(card, map, player);
    }

    @Override
    public void searchForMoves() {
        if (!usePower){
            super.searchForMoves();
        }else {
            List<Coordinates> validSquares;
            validSquares = card.checkMoves(player, selectedWorker, map);
            validSquares.remove(movedFrom);
            notify(new ChooseMove(validSquares, player.getNickname()));
        }
    }

    @Override
    public void performMove(Coordinates moveTo) {
        if (!usePower){
            try {
                Square square = map.getSquare(moveTo.getX(), moveTo.getY());
                movedFrom = selectedWorker.getPosition().getCoordinates();
                card.move(player, selectedWorker, square, map);
                if (isWinner()){
                    movedFrom = null;
                    notifyToGame(ControllerToGame.WINNER);
                }else {
                    notify(new MakeDecision(player.getNickname(), "Do you want to move once more, not back to your previous position?"));
                }
            } catch (OutOfMapException e) {
                notify(new UnsuccessfulOperation(player.getNickname()));
                searchForMoves();
            }
        }else {
            usePower = false;
            movedFrom = null;
            super.performMove(moveTo);
        }
    }


    @Override
    public void decisionManager(boolean takenDecision) {
        if (takenDecision){
            usePower = true;
            searchForMoves();
        }else {
            movedFrom = null;
            searchForBuild();
        }
    }
}
