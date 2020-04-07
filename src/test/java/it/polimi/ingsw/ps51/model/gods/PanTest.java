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

public class PanTest {
    Map map;
    Pan card = new Pan();
    Worker worker;

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
    public void moveTest_FromThirdToFirstLevel_WinningTrue() {
        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);
            map.getSquare(2,2).setLevel(Level.THIRD);

            map.getSquare(2,3).setLevel(Level.FIRST);

            card.move(worker, map.getSquare(2,3), map);
            Assert.assertTrue(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest_FromThirdToGroundLevel_WinningTrue() {
        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);
            map.getSquare(2,2).setLevel(Level.THIRD);

            card.move(worker, map.getSquare(2,3), map);
            Assert.assertTrue(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest_FromSecondToFirstLevel_WinningFalse() {
        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);
            map.getSquare(2,2).setLevel(Level.SECOND);

            map.getSquare(2,3).setLevel(Level.FIRST);

            card.move(worker, map.getSquare(2,3), map);
            Assert.assertFalse(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest_FromThirdToSecondLevel_WinningFalse() {
        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);
            map.getSquare(2,2).setLevel(Level.THIRD);

            map.getSquare(2,3).setLevel(Level.SECOND);

            card.move(worker, map.getSquare(2,3), map);
            Assert.assertFalse(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest_FromThirdToThirdLevel_WinningFalse() {
        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setPresentWorker(worker);
            map.getSquare(2,2).setLevel(Level.THIRD);

            map.getSquare(2,3).setLevel(Level.THIRD);

            card.move(worker, map.getSquare(2,3), map);
            Assert.assertFalse(worker.getInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}