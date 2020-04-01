package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.opponent_move_manager.Gods;
import it.polimi.ingsw.ps51.model.gods.opponent_move_manager.OpponentGodsFactory;
import it.polimi.ingsw.ps51.model.gods.opponent_move_manager.OpponetTurnGodsManager;
import it.polimi.ingsw.ps51.utility.Observable;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

abstract class CommonAction extends Observable<Gods> implements Card {

    @Override
    public List<Coordinates> checkMoves(Worker worker, Map map) {
        Square workerPosition = worker.getPosition();
        List<Square> adjacentSquares = map.getAdjacentSquare(workerPosition);
        List<Coordinates> validCoordinates = new ArrayList<>();
        for (Square square : adjacentSquares){
            if ((square != null) && (square.getLevel().ordinal() - worker.getPosition().getLevel().ordinal() <= 1)
            && (!square.isPresentWorker()) && !square.getLevel().equals(Level.DOME)){
                validCoordinates.add(square.getCoordinates());
            }
        }
        return clearPositions(validCoordinates, worker, map);
    }

    @Override
    public List<Pair<Coordinates, Level>> checkBuild(Worker worker, Map map) {
        List<Pair<Coordinates, Level>> validBuilds = new ArrayList<>();
        List<Square> adjacentSquares = map.getAdjacentSquare(worker.getPosition());

        for (Square square : adjacentSquares){
            if ((square != null) && !(square.getLevel().equals(Level.DOME)) && !(square.isPresentWorker())){
                switch (square.getLevel()){
                    case GROUND:
                        validBuilds.add(new Pair<>(square.getCoordinates(), Level.FIRST));
                        break;
                    case FIRST:
                        validBuilds.add(new Pair<>(square.getCoordinates(), Level.SECOND));
                        break;
                    case SECOND:
                        validBuilds.add(new Pair<>(square.getCoordinates(), Level.THIRD));
                        break;
                    case THIRD:
                        validBuilds.add(new Pair<>(square.getCoordinates(), Level.DOME));
                        break;
                    default:
                        break;
                }
            }
        }

        return validBuilds;
    }

    @Override
    public synchronized void move(Worker worker, Square position, Map map) {
        if (checkMoves(worker, map).contains(position.getCoordinates())){
            position.setPresentWorker(worker);
            worker.setPosition(position);
            if (position.getLevel().equals(Level.THIRD)){
                worker.setInWinningCondition(true);
            }
        }
    }

    @Override
    public synchronized void build(Worker worker, Square position, Level level, Map map) {
        Pair<Coordinates, Level> desiredBuild = new Pair<>(position.getCoordinates(), level);
        if (checkBuild(worker, map).contains(desiredBuild)){
            position.setLevel(level);
        }
    }

    /**
     * This method must be called before returning the List of the position where to move the worker to be sure that
     * eventual "Opponent's turn" effects are applied
     * @param positions the list of the valid position, without considering "Opponent's turn" effects
     * @param worker the selected worker
     * @param map the game map
     * @return the List of the coordinates where the selected worker can be moved
     */
    protected List<Coordinates> clearPositions(List<Coordinates> positions, Worker worker, Map map){
        OpponetTurnGodsManager manager;
        OpponentGodsFactory factory = new OpponentGodsFactory();
        for (Gods god : worker.getActiveGods()){
            manager = factory.getGod(god);
            positions = manager.epurateMove(positions, worker, map);
        }
        return positions;
    }
}
