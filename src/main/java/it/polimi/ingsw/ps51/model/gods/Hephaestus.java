package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import org.javatuples.Pair;

import java.util.List;

public class Hephaestus extends CommonAction{

    @Override
    public List<Pair<Coordinates, List<Level>>> checkBuild(Worker worker, Map map) {
        List<Pair<Coordinates, List<Level>>> oldPositions;
        oldPositions = super.checkBuild(worker, map);

        for (Pair<Coordinates, List<Level>> pair : oldPositions){
            Integer value = pair.getValue1().get(0).ordinal();
            if (Level.getByValue(value + 1) != null && !Level.getByValue(value + 1).equals(Level.DOME)){
                pair.getValue1().add(Level.getByValue(value + 1));
            }
        }
        return oldPositions;
    }
}
