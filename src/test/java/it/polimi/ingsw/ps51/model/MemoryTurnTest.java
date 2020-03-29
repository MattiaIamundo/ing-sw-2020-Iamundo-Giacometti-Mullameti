package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;


public class MemoryTurnTest {

    private MemoryTurn memoryTurn = null;

    @Before
    public void setUp() {
        memoryTurn = new MemoryTurn();
    }

    @After
    public void tearDown() {
        memoryTurn = null;
    }

    @Test
    public void getListOfActionTest() {
        Assert.assertNotNull(this.memoryTurn.getListOfAction());
    }

}
