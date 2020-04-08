package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.Worker;
import it.polimi.ingsw.ps51.model.gods.opponent_move_manager.Gods;
import it.polimi.ingsw.ps51.utility.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AthenaTest {
    Athena card;
    Map map;
    Worker worker;
    Player player;
    Worker oppositeWorker1;
    Worker oppositeWorker2;

    @Before
    public void setUp() throws Exception {
        card = new Athena();
        worker = new Worker("Player");
        player = new Player("Player");
        oppositeWorker1 = new Worker("Opposite");
        oppositeWorker2 = new Worker("Opposite");
        card.addObserver(oppositeWorker1);
        card.addObserver(oppositeWorker2);
        map = new Map();
        try {
            map.getSquare(2,2).setLevel(Level.FIRST);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        card = null;
        worker = null;
        player = null;
        oppositeWorker1 = null;
        oppositeWorker2 = null;
        map = null;
    }

    @Test
    public void move_GoesUp_TheOppositeWorkersAreNotified(){
        try {
            worker.setPosition(map.getSquare(2,1));
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
            worker.setPosition(map.getSquare(2,1));
            card.move(player, worker, map.getSquare(2,0), map);

            Assert.assertFalse(oppositeWorker1.getActiveGods().contains(Gods.ATHENA));
            Assert.assertFalse(oppositeWorker2.getActiveGods().contains(Gods.ATHENA));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}