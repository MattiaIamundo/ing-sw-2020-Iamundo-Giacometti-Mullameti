package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the god Atlas
 * @author Luca Giacometti
 */
public class Atlas extends CommonAction {

    @Override
    public List<Pair<Coordinates, Level>> checkBuild(Worker worker, Map map) {

        //getting the adjacent squares from the worker position
        List<Square> adjacentSquaresAtlas = map.getAdjacentSquare(worker.getPosition());
        //getting the common build action calling the super method checkBuild
        List<Pair<Coordinates, Level>> validBuildsAtlas = new ArrayList<>(super.checkBuild(worker, map));

        for (Square square : adjacentSquaresAtlas){
            //excluding the third level because the dome construction on the third level
            // is already present in validBuildsAtlas
            if ( (square != null) && !(square.getLevel().equals(Level.DOME)) && !(square.isPresentWorker())
                    && !(square.getLevel().equals(Level.THIRD)) ) {

                validBuildsAtlas.add(new Pair<>(square.getCoordinates(), Level.DOME));
            }

        }

        return validBuildsAtlas;
    }

}
