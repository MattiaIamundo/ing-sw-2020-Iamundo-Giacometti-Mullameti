package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    public void getCanDoLevelUpTest() {
        Assert.assertFalse(this.worker.getCanDoLevelUp());
    }

    @Test
    public void setCanDoLevelUpTest() {
        this.worker.setCanDoLevelUp(true);
        Assert.assertTrue(this.worker.getCanDoLevelUp());
    }
}