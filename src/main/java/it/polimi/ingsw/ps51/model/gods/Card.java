package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;

import java.util.List;

public interface Card {

    List<Coordinates> checkMoves(Worker worker, Map map);

    void move(Worker worker, Square position, Map map);

    List<Pair<Coordinates, Level>> checkBuild(Worker worker, Map map);

    void build(Worker worker, Square position, Level level, Map map);
}
