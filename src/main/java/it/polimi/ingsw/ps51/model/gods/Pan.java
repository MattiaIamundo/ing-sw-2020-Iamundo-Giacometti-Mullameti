package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Square;
import it.polimi.ingsw.ps51.model.Worker;

public class Pan extends WinType{

    @Override
    public synchronized void move(Worker worker, Square position, Map map) {
        Square oldPosition = worker.getPosition();
        super.move(worker, position, map);
        if (oldPosition.getLevel().ordinal() - position.getLevel().ordinal() >= 2){
            worker.setInWinningCondition(true);
        }
    }
}
