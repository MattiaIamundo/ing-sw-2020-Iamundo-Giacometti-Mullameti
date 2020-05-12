package it.polimi.ingsw.ps51.model.gods.opponent_move_manager;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;

import java.util.List;

/**
 * Manage the effects of those gods that have a power that affects the opponent's turn
 */
public interface OpponentTurnGodsManager {

    /**
     * Adjust the list of valid positions where to move the worker accordingly with the effect of the
     * God in the opponent's turn
     * @param validPositions the list of valid positions if no "Opponent's turn" effect will be applied
     * @param worker the selected worker
     * @param map the game map
     * @return the list with the valid positions after having applied the god's "Opponent's turn" effect
     */
    List<Coordinates> epurateMove(List<Coordinates> validPositions, Worker worker, Map map);

    boolean isValidWin(Worker worker, Map map);
}
