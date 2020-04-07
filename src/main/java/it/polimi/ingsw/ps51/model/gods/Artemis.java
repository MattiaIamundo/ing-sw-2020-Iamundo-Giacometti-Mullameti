package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;

import java.util.ArrayList;
import java.util.List;

public class Artemis extends CommonAction{

    @Override
    public List<Coordinates> checkMoves(Worker worker, Map map) {
        Square workerPosition = worker.getPosition();
        List<Square> adjacentSquares = map.getAdjacentSquare(workerPosition);
        List<Coordinates> validCoordinates = new ArrayList<>(super.checkMoves(worker,map));
        boolean alreadyPresent = false;

        for (Square square : adjacentSquares){

            List<Square> adjacentSquares2 = map.getAdjacentSquare(square);
            for(Square square2 : adjacentSquares2){
                if ((square2 != null) && (square2.getLevel().ordinal() - square.getLevel().ordinal() <= 1)
                            && (!square2.isPresentWorker()) && !square2.getLevel().equals(Level.DOME) && (square2 != workerPosition)){

                    for (Coordinates coordinates : validCoordinates){
                        if(coordinates == square2.getCoordinates()){
                            alreadyPresent=true;
                            break;
                        }

                    }

                    if(!alreadyPresent)
                        validCoordinates.add(square2.getCoordinates());

                }
            }
        }

        return clearPositions(validCoordinates, worker, map);
    }
}
