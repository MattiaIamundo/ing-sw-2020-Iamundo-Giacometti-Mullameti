package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HeraTest {

    Map map;
    Player player;
    Player oppPlayer;
    Worker worker;
    Worker oppositeWorker;
    Card card;
    Card oppCard;

    private class GenCard extends CommonAction{}

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        oppPlayer = new Player("OppPlayer");
        Playground playground = new Playground(Arrays.asList(player, oppPlayer));
        map = playground.getBoardMap();
        try {
            worker = new Worker("Player", map.getSquare(2,2));
            oppositeWorker = new Worker("OppPlayer", map.getSquare(0,3));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        player.addWorker(worker);
        oppPlayer.addWorker(oppositeWorker);
        oppCard = new GenCard();
        card = new Hera();
        ((Hera) card).addObserver(oppositeWorker);
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        player = null;
        oppPlayer = null;
        worker = null;
        oppositeWorker = null;
        card = null;
        oppCard = null;
    }

    @Test
    public void moveTest_OpponentWorkerAdvised() {
        try {
            card.move(player, worker, map.getSquare(2,3), map);

            assertTrue(oppositeWorker.getActiveGods().contains(Gods.HERA));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void OpponentCannotWinOnPerimeterSquare(){
        try {
            map.getSquare(0,4).setLevel(Level.THIRD);
            map.getSquare(0,3).setLevel(Level.SECOND);
            card.move(player, worker, map.getSquare(2,3), map);
            oppCard.move(oppPlayer, oppositeWorker, map.getSquare(0,4), map);

            assertFalse(oppositeWorker.isInWinningCondition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}