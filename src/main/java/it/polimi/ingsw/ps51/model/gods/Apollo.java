package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implement the god's power of Apollo
 */
public class Apollo extends CommonAction{

    @Override
    public List<Coordinates> checkMoves(Player player, Worker worker, Map map) {
        Square workerPosition = worker.getPosition();
        List<Square> adjacentSquares = map.getAdjacentSquare(workerPosition);
        List<Coordinates> validCoordinates = new ArrayList<>();
        for (Square square : adjacentSquares){
            if ((square != null) && (square.getLevel().ordinal() - worker.getPosition().getLevel().ordinal() <= 1)
                    && !square.getLevel().equals(Level.DOME)){

                if (square.isPresentWorker() && isAlliedWorker(player.getWorkers(), square)){
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
    public synchronized void move(Player player, Worker worker, Square position, Map map) {
        if (checkMoves(player, worker, map).contains(position.getCoordinates())){
            if (position.isPresentWorker()){
                Square oldPosition = worker.getPosition();
                worker.setPosition(position);
                notify(new Pair<Square, Square>(position, oldPosition));
                position.setPresentWorker(true);
                position.notifyChange();
            }else {
                super.move(player, worker, position, map);
            }
        }
    }

    private boolean isAlliedWorker(List<Worker> workers, Square position){
        for (Worker worker : workers){
            if (worker.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }
}
