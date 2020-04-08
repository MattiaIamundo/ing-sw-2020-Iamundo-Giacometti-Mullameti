package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DemeterTest {
    Map map;
    Demeter card = new Demeter();

    @Before
    public void setUp() throws Exception {
        map = new Map();
    }

    @After
    public void tearDown() throws Exception {
        map = null;
    }

    @Test
    public void buildDouble_BuildInx3y3Andx3y2_BuildSuccess() {
        Worker worker = new Worker("Player");
        try {
            List<Pair<Square, Level>> builds = new ArrayList<>();

            worker.setPosition(map.getSquare(2,2));
            builds.add(new Pair<>(map.getSquare(3,3), Level.FIRST));
            builds.add(new Pair<>(map.getSquare(3,2), Level.FIRST));
            card.buildDouble(worker, builds, map);

            Assert.assertEquals(Level.FIRST, map.getSquare(3,3).getLevel());
            Assert.assertEquals(Level.FIRST, map.getSquare(3,2).getLevel());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buildDouble_BuildInx3y3Andx3y3_BuildNotSuccess() {
        Worker worker = new Worker("Player");
        try {
            List<Pair<Square, Level>> builds = new ArrayList<>();

            worker.setPosition(map.getSquare(2,2));
            builds.add(new Pair<>(map.getSquare(3,3), Level.FIRST));
            builds.add(new Pair<>(map.getSquare(3,3), Level.FIRST));
            card.buildDouble(worker, builds, map);

            Assert.assertEquals(Level.GROUND, map.getSquare(3,3).getLevel());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buildDouble_BuildOneButCalledDouble_BuildSuccess() {
        Worker worker = new Worker("Player");
        try {
            List<Pair<Square, Level>> builds = new ArrayList<>();

            worker.setPosition(map.getSquare(2,2));
            builds.add(new Pair<>(map.getSquare(3,3), Level.FIRST));
            card.buildDouble(worker, builds, map);

            Assert.assertEquals(Level.FIRST, map.getSquare(3,3).getLevel());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}