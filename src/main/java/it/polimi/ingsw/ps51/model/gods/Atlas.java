package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the god's power of Atlas
 * @author Luca Giacometti
 */
public class Atlas extends CommonAction {

    @Override
    public List<Pair<Coordinates, List<Level>>> checkBuild(Worker worker, Map map) {

        //getting the common build action calling the super method checkBuild
        List<Pair<Coordinates, List<Level>>> validBuildsAtlas = new ArrayList<>(super.checkBuild(worker, map));

        //adding Dome level to each position
        for (Pair<Coordinates, List<Level>> listPair : validBuildsAtlas){
            if (!listPair.getValue1().contains(Level.DOME)){
                listPair.getValue1().add(Level.DOME);
            }
        }

        return validBuildsAtlas;
    }

}
