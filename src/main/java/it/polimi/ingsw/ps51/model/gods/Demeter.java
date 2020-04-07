package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;

import java.util.List;

/**
 * This class implements the god's power of Demeter
 */
public class Demeter extends CommonAction{

    /**
     * This is an alternative build method that permit to the player to build in two different squares. Can be used also
     * to perform a normal build by passing it a list of a single element
     * @param worker the selected worker
     * @param positions the list of pairs (square, level), where the square indicated where to build and the level
     *                  indicates the level to build
     * @param map the game map
     */
    public synchronized void buildDouble(Worker worker, List<Pair<Square, Level>> positions, Map map) {
        if (positions.size() == 2 && !positions.get(0).getValue0().equals(positions.get(1).getValue0())){
            super.build(worker, positions.get(0).getValue0(), positions.get(0).getValue1(), map);
            super.build(worker, positions.get(1).getValue0(), positions.get(1).getValue1(), map);
        }else if (positions.size() == 1){
            super.build(worker, positions.get(0).getValue0(), positions.get(0).getValue1(), map);
        }
    }
}
