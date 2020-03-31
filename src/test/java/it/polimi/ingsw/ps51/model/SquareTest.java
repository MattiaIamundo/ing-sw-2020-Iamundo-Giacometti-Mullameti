package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SquareTest {

    Square square;

    @Before
    public void setUp(){
        square = new Square(new Coordinates(2,2));
    }

    @After
    public void tierDown(){
        square = null;
    }

    @Test
    public void levelManagement_SetFirst_ReturnFirst(){
        square.setLevel(Level.FIRST);

        Assert.assertEquals(Level.FIRST, square.getLevel());
    }

    @Test
    public void workerManagement_SetPlayer1_GetPlayer1(){
        Worker worker = new Worker("Player1");
        square.setPresentWorker(worker);

        Assert.assertNotEquals(null, square.getPresentWorker());
        Assert.assertEquals(worker, square.getPresentWorker());
    }

    @Test
    public void isPresentWorker_Player1_ReturnTrue(){
        Worker worker = new Worker("Player1");
        square.setPresentWorker(worker);

        Assert.assertTrue(square.isPresentWorker());
    }

    @Test
    public void isPresentWorker_Null_ReturnFalse(){
        Assert.assertFalse(square.isPresentWorker());
    }

    @Test
    public void getPresentWorker_Null_ReturnNull(){
        Assert.assertNull(square.getPresentWorker());
    }
}
