package it.polimi.ingsw.ps51.utility;

import it.polimi.ingsw.ps51.model.Square;
import it.polimi.ingsw.ps51.model.gods.Gods;
import org.javatuples.Pair;

public interface WorkerObserver extends Observer{

    void updateGods(Gods message);

    void updatePosition(Pair<Square, Square> message);

    @Override
    default void update(Object message) {
        if (message instanceof Gods){
            updateGods((Gods) message);
        }else if (message instanceof Pair){
            updatePosition((Pair<Square, Square>) message);
        }
    }
}
