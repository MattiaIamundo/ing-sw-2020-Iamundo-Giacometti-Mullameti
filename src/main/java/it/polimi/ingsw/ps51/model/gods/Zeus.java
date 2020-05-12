package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import org.javatuples.Pair;

import java.util.Collections;
import java.util.List;

public class Zeus extends CommonAction{

    @Override
    public List<Pair<Coordinates, List<Level>>> checkBuild(Worker worker, Map map) {
        List<Pair<Coordinates, List<Level>>> standardBuild = super.checkBuild(worker, map);
        if (!worker.getPosition().getLevel().equals(Level.THIRD)){
            Level workerLevel = worker.getPosition().getLevel();
            standardBuild.add(new Pair<Coordinates, List<Level>>(worker.getPosition().getCoordinates(),
                    Collections.singletonList(Level.getByValue(workerLevel.ordinal() + 1))));
        }

        return standardBuild;
    }
}
