package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MinotaurTest {
    Map map;
    Minotaur card = new Minotaur();
    Worker worker;
    Worker opponentWorker;

    @Before
    public void setUp() throws Exception {
        map = new Map();
        worker = new Worker("Player");
        opponentWorker = new Worker("Opponent");
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        worker = null;
        opponentWorker = null;
    }

    @Test
    public void checkMovesTest_WorkerInX2Y3OpponentInX3Y3PowerUsable_ListOfEightPossibleMovements() {
        try {
            worker.setPosition(map.getSquare(2,3));
            map.getSquare(2,3).setPresentWorker(worker);

            opponentWorker.setPosition(map.getSquare(3,3));
            map.getSquare(2,3).setPresentWorker(opponentWorker);

            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,2));
            expected.add(new Coordinates(2,2));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(3,4));
            expected.add(new Coordinates(2,4));
            expected.add(new Coordinates(1,4));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, card.checkMoves(worker, map));

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMovesTest_WorkerInX2Y3OpponentInX3Y3PowerUsableDifferentHeight_ListOfEightPossibleMovements() {
        try {
            worker.setPosition(map.getSquare(2,3));
            map.getSquare(2,3).setPresentWorker(worker);

            opponentWorker.setPosition(map.getSquare(3,3));
            map.getSquare(3,3).setPresentWorker(opponentWorker);
            map.getSquare(4,3).setLevel(Level.THIRD);

            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,2));
            expected.add(new Coordinates(2,2));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(3,4));
            expected.add(new Coordinates(2,4));
            expected.add(new Coordinates(1,4));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, card.checkMoves(worker, map));

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMovesTest_WorkerInX2Y3OpponentInX3Y3PowerNotUsableDome_ListOfSevenPossibleMovements() {
        try {
            worker.setPosition(map.getSquare(2,3));
            map.getSquare(2,3).setPresentWorker(worker);

            opponentWorker.setPosition(map.getSquare(3,3));
            map.getSquare(3,3).setPresentWorker(opponentWorker);
            map.getSquare(4,3).setLevel(Level.DOME);

            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,2));
            expected.add(new Coordinates(2,2));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,4));
            expected.add(new Coordinates(2,4));
            expected.add(new Coordinates(1,4));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, card.checkMoves(worker, map));

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMovesTest_WorkerInX2Y3OpponentInX3Y3PowerNotUsableOtherWorker_ListOfSevenPossibleMovements() {
        Worker thirdWorker = new Worker("Third");
        try {
            worker.setPosition(map.getSquare(2,3));
            map.getSquare(2,3).setPresentWorker(worker);

            opponentWorker.setPosition(map.getSquare(3,3));
            map.getSquare(3,3).setPresentWorker(opponentWorker);

            thirdWorker.setPosition(map.getSquare(4,3));
            map.getSquare(4,3).setPresentWorker(thirdWorker);

            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,2));
            expected.add(new Coordinates(2,2));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,4));
            expected.add(new Coordinates(2,4));
            expected.add(new Coordinates(1,4));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, card.checkMoves(worker, map));

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}