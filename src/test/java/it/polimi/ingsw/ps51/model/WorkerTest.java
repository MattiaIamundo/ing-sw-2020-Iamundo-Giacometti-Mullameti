package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorkerTest {

    private Worker worker = null;
    @Before
    public void setUp()  {
        this.worker=new Worker("Nickname");
    }

    @After
    public void tearDown()  {
        this.worker=null;
    }

    @Test
    public void getNamePlayerTest() {
        Assert.assertEquals("Nickname" ,this.worker.getNamePlayer());
    }

    @Test
    public void setPositionTest() {
        Square position = new Square(null);
        this.worker.setPosition(position);
        Assert.assertEquals(position, this.worker.getPosition());
    }

    @Test
    public void getPositionTest() {
        Assert.assertNull(this.worker.getPosition());
    }

    @Test
    public void getInWinningConditionTest() {
        Assert.assertFalse(this.worker.getInWinningCondition());
    }

    @Test
    public void setInWinningConditionTest() {
        this.worker.setInWinningCondition(true);
        Assert.assertTrue(this.worker.getInWinningCondition());
    }
}