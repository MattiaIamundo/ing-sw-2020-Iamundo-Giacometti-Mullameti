package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;

import java.util.List;

public class Demeter extends CommonAction{

    public synchronized void buildDouble(Worker worker, List<Pair<Square, Level>> positions, Map map) {
        if (positions.size() == 2 && !positions.get(0).getValue0().equals(positions.get(1).getValue0())){
            super.build(worker, positions.get(0).getValue0(), positions.get(0).getValue1(), map);
            super.build(worker, positions.get(1).getValue0(), positions.get(1).getValue1(), map);
        }else if (positions.size() == 1){
            super.build(worker, positions.get(0).getValue0(), positions.get(0).getValue1(), map);
        }
    }
}
