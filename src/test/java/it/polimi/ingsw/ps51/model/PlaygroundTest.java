package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlaygroundTest {
    Player player1 = new Player("Player1");
    Player player2 = new Player("Player2");
    Playground playground;

    @Before
    public void setUp(){
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        playground = new Playground(players);
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
    public void setActualPlayer_Player1_ReturnPlayer1(){
        playground.setActualPlayer(player1);

        Assert.assertEquals(player1, playground.getActualPlayer());
    }
}
