package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Square;
import it.polimi.ingsw.ps51.model.Worker;
import it.polimi.ingsw.ps51.model.gods.opponent_move_manager.Gods;

public class Athena extends CommonAction {

    /**
     * The method after performing a normal move, check if the worker is been moved up, in this case notify all the other
     * workers the the power of Athena is been activated
     * @param worker the selected worker
     * @param position the position where to move the worker
     * @param map the game map
     */
    @Override
    public synchronized void move(Worker worker, Square position, Map map) {
        Square oldPosition = worker.getPosition();
        super.move(worker, position, map);
        if (worker.getPosition().getLevel().ordinal() - oldPosition.getLevel().ordinal() > 0){
            notify(Gods.ATHENA);
        }
    }

}
