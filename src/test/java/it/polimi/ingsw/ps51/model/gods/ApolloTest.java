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

public class ApolloTest {
    Map map;
    Apollo card;
    Worker worker;
    Worker worker2;
    Player player;
    Worker opponentWorker;
    Stub stub;

    @Before
    public void setUp() throws Exception {
        player= new Player("Player");
        Playground playground = new Playground(Collections.singletonList(player));
        map = playground.getBoardMap();
        try {
            worker = new Worker("Player", map.getSquare(2,2));
            worker2 = new Worker("Player", map.getSquare(0,0));
            opponentWorker = new Worker("Opponent", map.getSquare(3,2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.setWorkers(Arrays.asList(worker, worker2));
        stub = new Stub();
        playground.addObserver(stub);
        card = new Apollo();
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
    public void checkMoves_PlayerInx2y2OpponentInx3y2_ReturnEightValidPositions() {
        List<Coordinates> out;
        List<Coordinates> expected = new ArrayList<>();

        out = card.checkMoves(player, worker, map);

        expected.add(new Coordinates(1,1));
        expected.add(new Coordinates(2,1));
        expected.add(new Coordinates(3,1));
        expected.add(new Coordinates(3,2));
        expected.add(new Coordinates(3,3));
        expected.add(new Coordinates(2,3));
        expected.add(new Coordinates(1,3));
        expected.add(new Coordinates(1,2));

        Assert.assertEquals(expected, out);

    }

    @Test
    public void checkMoves_PlayerWorkerInx2y2PlayerWorkerInx1y2_ReturnSevenValidPositions() {
        List<Coordinates> out;
        List<Coordinates> expected = new ArrayList<>();
        try {
            worker2.setPosition(map.getSquare(1,2));
            out = card.checkMoves(player, worker, map);

            expected.add(new Coordinates(1,1));
            expected.add(new Coordinates(2,1));
            expected.add(new Coordinates(3,1));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(2,3));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, out);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void move_ToOpponentPosition_WorkersPositionsAreSwapped() {
        try {
            worker2.setPosition(map.getSquare(0,0));
            opponentWorker.setPosition(map.getSquare(1,1));

            card.move(player, worker, map.getSquare(1,1), map);
            Assert.assertEquals(map.getSquare(1,1), worker.getPosition());
            Assert.assertEquals(map.getSquare(2,2), opponentWorker.getPosition());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void move_ToFreePosition_NormalMove() {
        try {
            opponentWorker.setPosition(map.getSquare(0,0));

            card.move(player, worker, map.getSquare(3,2), map);
            Assert.assertEquals(map.getSquare(3,2), worker.getPosition());
            Assert.assertEquals(map.getSquare(0,0), opponentWorker.getPosition());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }
}