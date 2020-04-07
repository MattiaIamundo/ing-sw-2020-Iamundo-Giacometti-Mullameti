package it.polimi.ingsw.ps51.model.gods.opponent_move_manager;

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

public class OpponentAthenaTest {

    Map map;
    Worker worker;
    OpponentAthena specialPower = new OpponentAthena();

    @Before
    public void setUp() throws Exception {
        map = new Map();
        worker = new Worker("Player");
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        worker = null;
    }

    @Test
    public void epurateMoveTest_AllValidPositions_EqualPositionsList() {
        List<Coordinates> given = new ArrayList<>();
        List<Coordinates> expected = new ArrayList<>();
        List<Coordinates> out;

        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);

            given.add(new Coordinates(1,1));
            given.add(new Coordinates(2,1));
            given.add(new Coordinates(3,1));
            given.add(new Coordinates(3,2));
            given.add(new Coordinates(3,3));
            given.add(new Coordinates(2,3));
            given.add(new Coordinates(1,3));
            given.add(new Coordinates(1,2));

            expected.add(new Coordinates(1,1));
            expected.add(new Coordinates(2,1));
            expected.add(new Coordinates(3,1));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(2,3));
            expected.add(new Coordinates(1,3));
            expected.add(new Coordinates(1,2));

            out = specialPower.epurateMove(given, worker, map);

            Assert.assertEquals(expected, out);

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void epurateMoveTest_TwoInvalidPositions_ShorterPositionsList() {
        List<Coordinates> given = new ArrayList<>();
        List<Coordinates> expected = new ArrayList<>();
        List<Coordinates> out;

        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);

            map.getSquare(1,3).setLevel(Level.FIRST);
            map.getSquare(3,2).setLevel(Level.SECOND);

            given.add(new Coordinates(1,1));
            given.add(new Coordinates(2,1));
            given.add(new Coordinates(3,1));
            given.add(new Coordinates(3,2));
            given.add(new Coordinates(3,3));
            given.add(new Coordinates(2,3));
            given.add(new Coordinates(1,3));
            given.add(new Coordinates(1,2));

            expected.add(new Coordinates(1,1));
            expected.add(new Coordinates(2,1));
            expected.add(new Coordinates(3,1));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(2,3));
            expected.add(new Coordinates(1,2));

            out = specialPower.epurateMove(given, worker, map);

            Assert.assertEquals(expected, out);

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

}