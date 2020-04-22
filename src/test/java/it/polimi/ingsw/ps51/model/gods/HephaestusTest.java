package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class HephaestusTest {
    Map map;
    Hephaestus card = new Hephaestus();
    Player player;
    Worker worker;
    Worker worker2;
    Stub stub;

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        Playground playground = new Playground(Collections.singletonList(player));
        map = playground.getBoardMap();
        try {
            worker = new Worker("Player", map.getSquare(2,2));
            worker2 = new Worker("Player", map.getSquare(1,1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.setWorkers(Arrays.asList(worker, worker2));
        stub = new Stub();
        playground.addObserver(stub);
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        worker = null;
        worker2 = null;
        player = null;
        stub = null;
    }

    @Test
    public void checkBuild_CanBuildDoubleOnGround_DoubleBuild() {
        try {
            card.build(worker, map.getSquare(2,3), Level.SECOND, map);
            Assert.assertEquals(Level.SECOND, map.getSquare(2,3).getLevel());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkBuild_CanBuildDoubleOnFirst_DoubleBuild() {
        try {
            map.getSquare(2,3).setLevel(Level.FIRST);
            card.build(worker, map.getSquare(2,3), Level.THIRD, map);
            Assert.assertEquals(Level.THIRD, map.getSquare(2,3).getLevel());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkBuild_CanBuildDoubleButBuildSingleOnFirst_SingleBuild() {
        try {
            map.getSquare(2,3).setLevel(Level.FIRST);
            card.build(worker, map.getSquare(2,3), Level.SECOND, map);
            Assert.assertEquals(Level.SECOND, map.getSquare(2,3).getLevel());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkBuild_CanNotBuildDoubleOnSecond_NotBuild() {
        try {
            map.getSquare(2,3).setLevel(Level.SECOND);
            card.build(worker, map.getSquare(2,3), Level.DOME, map);
            Assert.assertEquals(Level.SECOND, map.getSquare(2,3).getLevel());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}