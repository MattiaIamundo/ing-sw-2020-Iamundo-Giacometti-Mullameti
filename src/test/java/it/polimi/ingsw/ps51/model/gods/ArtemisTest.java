package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArtemisTest {

    Artemis artemis;
    Worker worker;
    Worker worker2;
    Player player;
    Map map;

    @Before
    public void setUp() {
        artemis = new Artemis();
        map = new Map();
        player = new Player("Merita");
        try {
            worker = new Worker("Merita", map.getSquare(2,2));
            worker2 = new Worker("Merita", map.getSquare(4,1));

            map.getSquare(0,0).setLevel(Level.DOME);
            map.getSquare(1,0).setLevel(Level.THIRD);
            map.getSquare(3,0).setLevel(Level.SECOND);
            map.getSquare(0,1).setLevel(Level.THIRD);
            map.getSquare(1,1).setLevel(Level.SECOND);
            map.getSquare(4,1).setLevel(Level.SECOND);
            map.getSquare(1,2).setLevel(Level.SECOND);
            map.getSquare(3,2).setLevel(Level.SECOND);
            map.getSquare(0,3).setLevel(Level.DOME);
            map.getSquare(1,3).setLevel(Level.FIRST);
            map.getSquare(3,3).setLevel(Level.DOME);
            map.getSquare(1,4).setLevel(Level.THIRD);
            map.getSquare(2,4).setLevel(Level.FIRST);
            map.getSquare(4,4).setLevel(Level.FIRST);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        artemis = null;
        worker = null;
        worker2 = null;
        player = null;
        map = null;
    }

    @Test
    public void checkMovesTest_OnWorker_11ValidPositions() {
        List<Coordinates> expected = new ArrayList<>();
        List<Coordinates> outcome;

        outcome = artemis.checkMoves(player, worker, map);

        expected.add(new Coordinates(2,0));
        expected.add(new Coordinates(4,0));
        expected.add(new Coordinates(2,1));
        expected.add(new Coordinates(3,1));
        expected.add(new Coordinates(0,2));
        expected.add(new Coordinates(4,2));
        expected.add(new Coordinates(1,3));
        expected.add(new Coordinates(2,3));
        expected.add(new Coordinates(0,4));
        expected.add(new Coordinates(2,4));
        expected.add(new Coordinates(4,4));

        assertTrue(expected.containsAll(outcome));
        assertTrue(outcome.containsAll(expected));
        assertEquals(expected.size(), outcome.size());
    }

    @Test
    public void checkMovesTest_OnWorker2_8ValidPositions(){
        List<Coordinates> expected = new ArrayList<>();
        List<Coordinates> outcome;

        outcome = artemis.checkMoves(player, worker2, map);

        expected.add(new Coordinates(3,0));
        expected.add(new Coordinates(4,0));
        expected.add(new Coordinates(2,1));
        expected.add(new Coordinates(3,1));
        expected.add(new Coordinates(3,2));
        expected.add(new Coordinates(4,2));
        expected.add(new Coordinates(2,3));
        expected.add(new Coordinates(4,3));

        assertTrue(expected.containsAll(outcome));
        assertTrue(outcome.containsAll(expected));
        assertEquals(expected.size(), outcome.size());
    }
}