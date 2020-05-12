package it.polimi.ingsw.ps51.model.gods.opponent_move_manager;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;

import java.util.List;

public class OpponentHera implements OpponentTurnGodsManager {
    @Override
    public List<Coordinates> epurateMove(List<Coordinates> validPositions, Worker worker, Map map) {
        return validPositions;
    }

    @Override
    public boolean isValidWin(Worker worker, Map map) {
        if (map.isThisPerimeterSquare(worker.getPosition())){
            return false;
        }else {
            return true;
        }
    }
}
