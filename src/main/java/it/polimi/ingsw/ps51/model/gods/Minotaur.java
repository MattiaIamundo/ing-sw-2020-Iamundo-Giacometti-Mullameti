package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the god's power of Minotaur
 */
public class Minotaur extends CommonAction{

    @Override
    public List<Coordinates> checkMoves(Player player, Worker worker, Map map) {
        Square workerPosition = worker.getPosition();
        List<Square> adjacentSquares = map.getAdjacentSquare(workerPosition);
        List<Coordinates> validCoordinates = new ArrayList<>();
        for (int i = 0; i < adjacentSquares.size(); i++){
            Square square = adjacentSquares.get(i);
            if ((square != null) && (square.getLevel().ordinal() - worker.getPosition().getLevel().ordinal() <= 1)
                    && (!square.getLevel().equals(Level.DOME))
                    && (!(square.isPresentWorker()) || (square.isPresentWorker() && isValidSquareWithWorker(square, i, map)))){

                if (square.isPresentWorker() && isAlliedWorker(player.getWorkers(), square)){
                    continue;
                }
                validCoordinates.add(square.getCoordinates());
            }
        }
        return clearPositions(validCoordinates, worker, map);
    }

    @Override
    public synchronized void move(Player player, Worker worker, Square position, Map map) {
        if (position.isPresentWorker()){
            int dir = map.getAdjacentSquare(worker.getPosition()).indexOf(position);
            Square newPos = map.getAdjacentSquare(position).get(dir);

            notify(new Pair<Square, Square>(position, newPos));
            worker.setPosition(position);
            map.notifyMapUpdate();
        }else {
            super.move(player, worker, position, map);
        }
    }

    /**
     * Check if the worker on opponentPosition can be moved in the same direction, identified by the position i in the
     * list of the adjacent squares
     * @param opponentPosition the square where is present an opponent's worker
     * @param direction the direction in which the worker of the actual player must be moved to encounter the opponent's
     *                  worker, the direction refers to the position into the list of the adjacent squares retrieved from
     *                  the actual player's worker position
     * @param map the game map
     * @return true if the opponent's worker can be moved in the same direction of the actual player's worker, without
     *              restrictions on the difference in the height of the two involved squares. False otherwise
     */
    private boolean isValidSquareWithWorker(Square opponentPosition, int direction, Map map){
        List<Square> adjacentSquares = map.getAdjacentSquare(opponentPosition);
        return adjacentSquares.get(direction).isFreeSquare();
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
