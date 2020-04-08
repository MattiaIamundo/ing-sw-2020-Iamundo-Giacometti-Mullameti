package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.Square;
import it.polimi.ingsw.ps51.model.Worker;

/**
 * This class implements the god's power of Pan
 */
public class Pan extends CommonAction{

    /**
     * Checks, after having completed the move, if the worker goes down of two or more levels, in this last case set to
     * true the worker's variable inWinningCondition
     * @param player the player who is playing his game turn
     * @param worker the selected worker
     * @param position the position where the worker must be moved
     * @param map the game map
     */
    @Override
    public synchronized void move(Player player, Worker worker, Square position, Map map) {
        Square oldPosition = worker.getPosition();
        super.move(player, worker, position, map);
        if (oldPosition.getLevel().ordinal() - position.getLevel().ordinal() >= 2){
            worker.setInWinningCondition(true);
        }
    }
}
