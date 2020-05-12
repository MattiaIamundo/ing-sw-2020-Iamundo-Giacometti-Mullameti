package it.polimi.ingsw.ps51.model.gods.opponent_move_manager;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Square;
import it.polimi.ingsw.ps51.model.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement the effect of Athena power over the opponent's turn
 */
public class OpponentAthena implements OpponentTurnGodsManager {

    /**
     * Removes those positions that are at an higher level compared to the worker
     * @param validPositions the list of valid positions if no "Opponent's turn" effect will be applied
     * @param worker the selected worker
     * @param map the game map
     * @return the list with the valid positions after having applied the god's "Opponent's turn" effect
     */
    @Override
    public List<Coordinates> epurateMove(List<Coordinates> validPositions, Worker worker, Map map) {
        List<Coordinates> newPositions = new ArrayList<>();
        for (Coordinates coordinates : validPositions){
            try {
                Square square = map.getSquare(coordinates.getX(), coordinates.getY());
                if (square.getLevel().ordinal() - worker.getPosition().getLevel().ordinal() <= 0){
                    newPositions.add(coordinates);
                }
            } catch (OutOfMapException e) {
                e.printStackTrace();
            }
        }
        return newPositions;
    }

    @Override
    public boolean isValidWin(Worker worker, Map map) {
        return true;
    }
}
