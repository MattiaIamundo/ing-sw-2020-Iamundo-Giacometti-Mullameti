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

public class PanTest {
    Map map;
    Pan card = new Pan();
    Worker worker;
    Worker worker2;
    Stub stub;
    Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        Playground playground = new Playground(Collections.singletonList(player));
        map = playground.getBoardMap();
        try {
            worker = new Worker("player", map.getSquare(2,2));
            worker2 = new Worker("Player", map.getSquare(1,2));
        } catch (OutOfMapException e) {
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
        stub = null;
        player = null;
    }

    @Test
    public void moveTest_FromThirdToFirstLevel_WinningTrue() {
        try {
            map.getSquare(2,2).setLevel(Level.THIRD);

            map.getSquare(2,3).setLevel(Level.FIRST);

            card.move(player, worker, map.getSquare(2,3), map);
            Assert.assertTrue(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest_FromThirdToGroundLevel_WinningTrue() {
        try {
            map.getSquare(2,2).setLevel(Level.THIRD);

            card.move(player, worker, map.getSquare(2,3), map);
            Assert.assertTrue(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest_FromSecondToFirstLevel_WinningFalse() {
        try {
            map.getSquare(2,2).setLevel(Level.SECOND);

            map.getSquare(2,3).setLevel(Level.FIRST);

            card.move(player, worker, map.getSquare(2,3), map);
            Assert.assertFalse(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest_FromThirdToSecondLevel_WinningFalse() {
        try {
            map.getSquare(2,2).setLevel(Level.THIRD);

            map.getSquare(2,3).setLevel(Level.SECOND);

            card.move(player, worker, map.getSquare(2,3), map);
            Assert.assertFalse(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest_FromThirdToThirdLevel_WinningFalse() {
        try {
            map.getSquare(2,2).setLevel(Level.THIRD);

            map.getSquare(2,3).setLevel(Level.THIRD);

            card.move(player, worker, map.getSquare(2,3), map);
            Assert.assertFalse(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}