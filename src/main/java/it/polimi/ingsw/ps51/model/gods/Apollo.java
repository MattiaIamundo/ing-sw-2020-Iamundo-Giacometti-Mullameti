package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implement the god's power of Apollo
 */
public class Apollo extends CommonAction{

    @Override
    public List<Coordinates> checkMoves(Worker worker, Map map) {
        Square workerPosition = worker.getPosition();
        List<Square> adjacentSquares = map.getAdjacentSquare(workerPosition);
        List<Coordinates> validCoordinates = new ArrayList<>();
        for (Square square : adjacentSquares){
            if ((square != null) && (square.getLevel().ordinal() - worker.getPosition().getLevel().ordinal() <= 1)
                    && !square.getLevel().equals(Level.DOME)){

                if (square.isPresentWorker() && worker.getNamePlayer().equals(square.getPresentWorker().getNamePlayer())){
                    continue;
                }
                validCoordinates.add(square.getCoordinates());
            }
        }
        return clearPositions(validCoordinates, worker, map);
    }

    /**
     * In case of a square where there is an opponent's worker their positions are swapped
     * @param worker the selected worker
     * @param position the position where the worker must be moved
     * @param map the game map
     */
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
