package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class AthenaTest {
    Athena card;
    Map map;
    Worker worker;
    Worker worker2;
    Player player;
    Worker oppositeWorker1;
    Worker oppositeWorker2;
    Stub stub;

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        Playground playground = new Playground(Collections.singletonList(player));
        map = playground.getBoardMap();
        try {
            worker = new Worker("Player", map.getSquare(2,1));
            worker2 = new Worker("Player", map.getSquare(0,0));
            oppositeWorker1 = new Worker("Opposite", map.getSquare(4,4));
            oppositeWorker2 = new Worker("Opposite", map.getSquare(1,1));

            map.getSquare(2,2).setLevel(Level.FIRST);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        stub = new Stub();
        playground.addObserver(stub);
        card = new Athena();
        card.addObserver(oppositeWorker1);
        card.addObserver(oppositeWorker2);
    }

    @After
    public void tearDown() throws Exception {
        card = null;
        worker = null;
        worker2 = null;
        player = null;
        oppositeWorker1 = null;
        oppositeWorker2 = null;
        map = null;
        stub = null;
    }

    @Test
    public void move_GoesUp_TheOppositeWorkersAreNotified(){
        try {
            card.move(player, worker, map.getSquare(2,2), map);

            Assert.assertTrue(oppositeWorker1.getActiveGods().contains(Gods.ATHENA));
            Assert.assertTrue(oppositeWorker2.getActiveGods().contains(Gods.ATHENA));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void move_DoNotGoesUp_TheOppositeWorkersAreNotNotified(){
        try {
            card.move(player, worker, map.getSquare(2,0), map);

            Assert.assertFalse(oppositeWorker1.getActiveGods().contains(Gods.ATHENA));
            Assert.assertFalse(oppositeWorker2.getActiveGods().contains(Gods.ATHENA));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}