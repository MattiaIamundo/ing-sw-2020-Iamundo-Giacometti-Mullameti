package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuildTest {

    private Build build = null;

    @Before
    public void setUp() {
        Coordinates position = new Coordinates(3,4);
        Level newLevel = Level.FIRST;
        this.build = new Build(position, newLevel, false);
    }

    @After
    public void tearDown() {
        this.build = null;
    }

    @Test
    public void getPosition() {
        Coordinates cord = new Coordinates(3,4);
        Assert.assertEquals( cord.getX(), this.build.getPosition().getX());
        Assert.assertEquals( cord.getY(), this.build.getPosition().getY());
    }

    @Test
    public void getNewLevel() {
        Assert.assertEquals(Level.FIRST, this.build.getNewLevel());
    }
}