package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.gods.Apollo;
import it.polimi.ingsw.ps51.model.gods.Artemis;
import it.polimi.ingsw.ps51.model.gods.Athena;
import it.polimi.ingsw.ps51.model.gods.Demeter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaygroundTest {
    Player player1;
    Player player2;
    Worker worker11;
    Worker worker12;
    Worker worker21;
    Worker worker22;
    Playground playground;

    @Before
    public void setUp(){
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        playground = new Playground(Arrays.asList(player1, player2));
        Map map = playground.getBoardMap();

        try {
            worker11 = new Worker("Player1", map.getSquare(0,0));
            worker12 = new Worker("Player1", map.getSquare(1,3));
            worker21 = new Worker("Player2", map.getSquare(2,4));
            worker22 = new Worker("Player2", map.getSquare(0,3));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        player1.setWorkers(Arrays.asList(worker11, worker12));
        player1.setGod(new Artemis());
        player2.setWorkers(Arrays.asList(worker21, worker22));
        player2.setGod(new Demeter());
    }

    @After
    public void tierDown(){
        playground = null;
    }

    @Test
    public void getPlayers_ReturnTwoPlayers(){
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Assert.assertEquals(players, playground.getPlayers());
    }

    @Test
    public void getBoardMap_ReturnNotNull(){
        Assert.assertNotNull(playground.getBoardMap());
    }

    @Test
    public void getLog_ReturnNotNull(){
        Assert.assertNotNull(playground.getLog());
    }

    @Test
    public void getActualTurn_NoInput_Return0(){
        Assert.assertEquals(0, (int) playground.getActualTurn());
    }

    @Test
    public void setActualTurn_IncreaseOne_Return1(){
        playground.setActualTurn();

        Assert.assertEquals(1, (int) playground.getActualTurn());
    }

    @Test
    public void getActualPlayer_NoInput_ReturnNull(){
        Assert.assertNull(playground.getActualPlayer());
    }

    @Test
    public void getNextPlayerTest(){
        player1.setGod(new Athena());
        player2.setGod(new Apollo());
        playground.setActualPlayer(player1);
        Player next = playground.getNextPlayer();

        Assert.assertEquals(player2, next);
    }

    @Test
    public void getNextPlayerTest_ActualIsTheLastOfTheList(){
        player1.setGod(new Athena());
        player2.setGod(new Apollo());
        playground.setActualPlayer(player2);
        Player next = playground.getNextPlayer();

        Assert.assertEquals(player1, next);
    }

    @Test
    public void removePLayerTest_Player2_Player2IsRemoved(){
        playground.removePlayer(player2);

        try {
            Assert.assertEquals(1, playground.getPlayers().size());
            Assert.assertFalse(playground.getBoardMap().getSquare(2,4).isPresentWorker());
            Assert.assertFalse(playground.getBoardMap().getSquare(0,3).isPresentWorker());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}
