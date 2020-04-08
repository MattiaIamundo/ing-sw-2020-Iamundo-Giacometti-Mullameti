package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HephaestusTest {
    Map map;
    Hephaestus card = new Hephaestus();
    Worker worker;

    @Before
    public void setUp() throws Exception {
        try {
            map = new Map();
            worker = new Worker("Player");
            worker.setPosition(map.getSquare(2,2));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        worker = null;
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