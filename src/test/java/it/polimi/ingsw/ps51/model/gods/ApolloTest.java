package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ApolloTest {
    Map map;
    Apollo card = new Apollo();
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
    public void checkMoves_PlayerInx2y2OpponentInx3y2_ReturnEightValidPositions() {
        List<Coordinates> out;
        List<Coordinates> expected = new ArrayList<>();
        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);
            opponentWorker.setPosition(map.getSquare(3,2));
            map.getSquare(3,2).setPresentWorker(opponentWorker);
            out = card.checkMoves(worker, map);

            expected.add(new Coordinates(1,1));
            expected.add(new Coordinates(2,1));
            expected.add(new Coordinates(3,1));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(2,3));
            expected.add(new Coordinates(1,3));
            expected.add(new Coordinates(1,2));

            Assert.assertEquals(expected, out);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkMoves_PlayerWorkerInx2y2PlayerWorkerInx1y2_ReturnSevenValidPositions() {
        List<Coordinates> out;
        List<Coordinates> expected = new ArrayList<>();
        try {
            Worker worker2 = new Worker("Player");


            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);
            worker2.setPosition(map.getSquare(1,2));
            map.getSquare(1,2).setPresentWorker(worker2);
            opponentWorker.setPosition(map.getSquare(3,2));
            map.getSquare(3,2).setPresentWorker(opponentWorker);
            out = card.checkMoves(worker, map);

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
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);
            opponentWorker.setPosition(map.getSquare(1,1));
            map.getSquare(1,1).setPresentWorker(opponentWorker);

            card.move(worker, map.getSquare(1,1), map);
            Assert.assertEquals(map.getSquare(1,1), worker.getPosition());
            Assert.assertEquals(map.getSquare(2,2), opponentWorker.getPosition());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void move_ToFreePosition_NormalMove() {
        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);
            opponentWorker.setPosition(map.getSquare(0,0));
            map.getSquare(0,0).setPresentWorker(opponentWorker);

            card.move(worker, map.getSquare(3,2), map);
            Assert.assertEquals(map.getSquare(3,2), worker.getPosition());
            Assert.assertEquals(map.getSquare(0,0), opponentWorker.getPosition());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }
}