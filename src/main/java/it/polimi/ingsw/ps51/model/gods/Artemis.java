package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the god's power of Artemis
 */
public class Artemis extends CommonAction{

    @Override
    public List<Coordinates> checkMoves(Player player, Worker worker, Map map) {
        Square workerPosition = worker.getPosition();
        List<Coordinates> validCoordinates = new ArrayList<>(super.checkMoves(player, worker, map));

        Coordinates workerCoord = worker.getPosition().getCoordinates();
        Square additional;
        for (int y2 = -2; y2 < 3; y2 = y2 + 2){
            for (int x2 = -2; x2 < 3; x2 = x2 + 2){

                try {
                    additional = map.getSquare(workerCoord.getX() + x2, workerCoord.getY() + y2);
                    if ((additional != null) && (additional.getLevel().ordinal() - workerPosition.getLevel().ordinal() <= 1)
                            && (!additional.isPresentWorker()) && !additional.getLevel().equals(Level.DOME)){

                        validCoordinates.add(additional.getCoordinates());
                    }

                } catch (OutOfMapException e) {
                    additional = null;
                }
            }
        }

        return clearPositions(validCoordinates, worker, map);
    }
}
