package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ActionTest {

    private Action action = null;

    @Before
    public void setUp() {
        action = new Build(null, null, false);
    }

    @After
    public void tearDown() {
        action = null;
    }

    @Test
    public void getIsUsingGod() {
        Assert.assertFalse(this.action.getIsUsingGod());
    }

    @Test
    public void setIsUsingGod() {
        this.action.setIsUsingGod(true);
        Assert.assertTrue(this.action.getIsUsingGod());
    }
}