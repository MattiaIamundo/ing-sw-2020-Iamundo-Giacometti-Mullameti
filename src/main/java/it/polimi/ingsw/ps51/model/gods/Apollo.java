package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Square;
import it.polimi.ingsw.ps51.model.Worker;

import java.util.List;

/**
 * This class implement the God Apollo who's effect give you the possibility to move your worker to a square occupied
 * by an opponent's worker swapping their positions
 */
public class Apollo extends CommonAction{

    @Override
    public List<Coordinates> checkMoves(Worker worker, Map map) {
        List<Coordinates> positions;
        positions = super.checkMoves(worker, map);
        for (Square square : map){
            if (square.isPresentWorker() && !worker.getNamePlayer().equals(square.getPresentWorker().getNamePlayer())){
                positions.add(square.getCoordinates());
            }
        }
        return clearPositions(positions, worker, map);
    }

    @Override
    public synchronized void move(Worker worker, Square position, Map map) {
        if (checkMoves(worker, map).contains(position.getCoordinates())){
            if (position.isPresentWorker()){
                //save the opponent worker
                Worker otherWorker = position.getPresentWorker();
                //save the starting position of the worker who did the move
                Square oldPosition = worker.getPosition();

                //set the new position for the worker that made the move
                position.setPresentWorker(worker);
                worker.setPosition(position);
                //set the position of the opponent worker to the previous position of the worker who moved
                otherWorker.setPosition(oldPosition);
                oldPosition.setPresentWorker(otherWorker);
            }else {
                worker.setPosition(position);
                position.setPresentWorker(worker);
            }
        }
    }

}
