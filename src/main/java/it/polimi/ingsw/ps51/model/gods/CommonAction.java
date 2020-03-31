package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

abstract class CommonAction implements Card {
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
        return validCoordinates;
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
        }
    }

    @Override
    public synchronized void build(Worker worker, Square position, Level level, Map map) {
        Pair<Coordinates, Level> desiredBuild = new Pair<>(position.getCoordinates(), level);
        if (checkBuild(worker, map).contains(desiredBuild)){
            position.setLevel(level);
        }
    }
}
