package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoveTest {

    private Move move = null;

    @Before
    public void setUp() {
        Coordinates prev = new Coordinates(2,3);
        Coordinates next = new Coordinates(3,3);
        Level prevHeight = Level.SECOND;
        Level nextHeight = Level.THIRD;
        move = new Move(prev,next, prevHeight, nextHeight, false,false);
    }

    @After
    public void tearDown() {
        this.move = null;
    }

    @Test
    public void getPrevCord() {
        Coordinates cord = new Coordinates(2,3);
        Assert.assertEquals(cord.getX(), this.move.getPrevCord().getX());
        Assert.assertEquals(cord.getY(), this.move.getPrevCord().getY());
    }

    @Test
    public void getNextCord() {
        Coordinates cord = new Coordinates(3,3);
        Assert.assertEquals(cord.getX(), this.move.getNextCord().getX());
        Assert.assertEquals(cord.getY(), this.move.getNextCord().getY());
    }

    @Test
    public void getPrevHeight() {
        Assert.assertEquals(Level.SECOND, this.move.getPrevHeight());
    }

    @Test
    public void getNextHeight() {
        Assert.assertEquals(Level.THIRD, this.move.getNextHeight());
    }

    @Test
    public void getIsForced() {
        Assert.assertFalse(this.move.getIsForced());
    }
}