package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.model.gods.Apollo;
import it.polimi.ingsw.ps51.model.gods.Card;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class PlayerTest {

    private Player player = null;

    @Before
    public void setUp()  {
        this.player = new Player("Nickname");
    }

    @After
    public void tearDown()  {
        this.player = null;
    }

    @Test
    public void getGodTest() {
        Assert.assertNull(this.player.getGod());
    }

    @Test
    public void setGodTest() {
        Card card = new Apollo();
        this.player.setGod(card);
        Assert.assertEquals(card, this.player.getGod());
    }

    @Test
    public void getWorkersTest() {
        Assert.assertNull(this.player.getWorkers());
    }

    @Test
    public void setWorkersTest() {
        List<Worker> worker = new ArrayList<>();
        worker.add(new Worker("Nickname"));
        worker.add(new Worker("Nickname"));
        this.player.setWorkers(worker);
        Assert.assertEquals(worker, this.player.getWorkers());
    }

}