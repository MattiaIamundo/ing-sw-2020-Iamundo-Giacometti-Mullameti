package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class MinotaurTest {
    Map map;
    Minotaur card;
    Worker worker;
    Worker worker2;
    Player player;
    Stub stub;
    Worker opponentWorker;

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        Playground playground = new Playground(Collections.singletonList(player));
        map = playground.getBoardMap();
        try {
            worker = new Worker("Player", map.getSquare(2,3));
            worker2 = new Worker("Player", map.getSquare(0,0));
            opponentWorker = new Worker("Opponent", map.getSquare(3,3));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        player.setWorkers(Arrays.asList(worker, worker2));
        stub = new Stub();
        playground.addObserver(stub);
        card = new Minotaur();
        card.addObserver(opponentWorker);
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        card = null;
        worker = null;
        worker2 = null;
        player = null;
        opponentWorker = null;
        stub = null;
    }

    @Test
    public void checkMovesTest_WorkerInX2Y3OpponentInX3Y3PowerUsable_ListOfEightPossibleMovements() {
        try {
            worker.setPosition(map.getSquare(2,3));
            worker2.setPosition(map.getSquare(0,0));

            opponentWorker.setPosition(map.getSquare(3,3));

            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,2));
            expected.add(new Coordinates(2,2));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(3,4));
            expected.add(new Coordinates(2,4));
            expected.add(new Coordinates(1,4));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, card.checkMoves(player, worker, map));

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMovesTest_WorkerInX2Y3OpponentInX3Y3PowerUsableDifferentHeight_ListOfEightPossibleMovements() {
        try {
            worker.setPosition(map.getSquare(2,3));
            worker2.setPosition(map.getSquare(0,0));

            opponentWorker.setPosition(map.getSquare(3,3));
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

            Assert.assertEquals(expected, card.checkMoves(player, worker, map));

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMovesTest_WorkerInX2Y3OpponentInX3Y3PowerNotUsableDome_ListOfSevenPossibleMovements() {
        try {
            worker.setPosition(map.getSquare(2,3));

            opponentWorker.setPosition(map.getSquare(3,3));
            map.getSquare(4,3).setLevel(Level.DOME);

            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,2));
            expected.add(new Coordinates(2,2));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,4));
            expected.add(new Coordinates(2,4));
            expected.add(new Coordinates(1,4));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, card.checkMoves(player, worker, map));

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMovesTest_WorkerInX2Y3AlliedInX3Y3PowerNotUsableAllied_ListOfSevenPossibleMovements() {
        try {
            worker.setPosition(map.getSquare(2,3));

            worker2.setPosition(map.getSquare(3,3));

            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,2));
            expected.add(new Coordinates(2,2));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,4));
            expected.add(new Coordinates(2,4));
            expected.add(new Coordinates(1,4));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, card.checkMoves(player, worker, map));

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMovesTest_WorkerInX2Y3OpponentInX3Y3PowerNotUsableOtherWorker_ListOfSevenPossibleMovements() {
        Worker thirdWorker = new Worker("Third");
        try {
            worker.setPosition(map.getSquare(2,3));

            opponentWorker.setPosition(map.getSquare(3,3));

            thirdWorker.setPosition(map.getSquare(4,3));

            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,2));
            expected.add(new Coordinates(2,2));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,4));
            expected.add(new Coordinates(2,4));
            expected.add(new Coordinates(1,4));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, card.checkMoves(player, worker, map));

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest_x2y2Tox3y2UsePower_MovePerformed() {
        try {
            worker.setPosition(map.getSquare(2,2));
            opponentWorker.setPosition(map.getSquare(3,2));

            card.move(player, worker, map.getSquare(3,2), map);

            Assert.assertEquals(map.getSquare(3,2), worker.getPosition());
            Assert.assertEquals(map.getSquare(4,2), opponentWorker.getPosition());
            Assert.assertFalse(map.getSquare(2,2).isPresentWorker());
            Assert.assertTrue(map.getSquare(3,2).isPresentWorker());
            Assert.assertTrue(map.getSquare(4,2).isPresentWorker());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void moveTest_x2y3Tox2y2_NormalMoveExecuted(){
        try {
            card.move(player, worker, map.getSquare(2,2), map);

            Assert.assertEquals(map.getSquare(2,2), worker.getPosition());
            Assert.assertTrue(map.getSquare(2,2).isPresentWorker());
            Assert.assertFalse(map.getSquare(2,3).isPresentWorker());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}