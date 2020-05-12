package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ZeusTest {
    Map map;
    Card card;
    Worker worker;

    @Before
    public void setUp() throws Exception {
        map = new Map();
        card = new Zeus();
        try {
            worker = new Worker("Player", map.getSquare(2,2));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        card = null;
        worker = null;
    }

    @Test
    public void checkBuildTest_WorkerOnGround_BuildFirstUnderItself() {
        Pair<Coordinates, List<Level>> expected = new Pair<>(new Coordinates(2,2), Collections.singletonList(Level.FIRST));
        List<Pair<Coordinates, List<Level>>> output;
        output = card.checkBuild(worker, map);

        assertTrue(output.contains(expected));
    }

    @Test
    public void checkBuildTest_WorkerOnFirst_BuildSecondUnderItself() {
        Pair<Coordinates, List<Level>> expected = new Pair<>(new Coordinates(2,2), Collections.singletonList(Level.SECOND));
        List<Pair<Coordinates, List<Level>>> output;
        worker.getPosition().setLevel(Level.FIRST);
        output = card.checkBuild(worker, map);

        assertTrue(output.contains(expected));
    }

    @Test
    public void checkBuildTest_WorkerOnSecond_BuildThirdUnderItself() {
        Pair<Coordinates, List<Level>> expected = new Pair<>(new Coordinates(2,2), Collections.singletonList(Level.THIRD));
        List<Pair<Coordinates, List<Level>>> output;
        worker.getPosition().setLevel(Level.SECOND);
        output = card.checkBuild(worker, map);

        assertTrue(output.contains(expected));
    }

    @Test
    public void checkBuildTest_WorkerOnThird_NotPossibleBuildUnderItself() {
        Pair<Coordinates, List<Level>> expected = new Pair<>(new Coordinates(2,2), Collections.singletonList(Level.DOME));
        List<Pair<Coordinates, List<Level>>> output;
        worker.getPosition().setLevel(Level.THIRD);
        output = card.checkBuild(worker, map);

        assertFalse(output.contains(expected));
    }
}