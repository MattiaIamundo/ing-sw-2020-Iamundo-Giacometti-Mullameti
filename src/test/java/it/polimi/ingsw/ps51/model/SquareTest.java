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
    public void isPresentWorker_Player1_ReturnTrue(){
        Worker worker = new Worker("Player1", square);

        Assert.assertTrue(square.isPresentWorker());
    }

    @Test
    public void isPresentWorker_Null_ReturnFalse(){
        Assert.assertFalse(square.isPresentWorker());
    }

    @Test
    public void equalsTest_PassedItself_ReturnTrue(){
        Assert.assertTrue(square.equals(square));
    }

    @Test
    public void equalsTest_PassedEquivalentSquare_ReturnTrue(){
        Square squareEquivalent = new Square(new Coordinates(2,2));

        Assert.assertTrue(square.equals(squareEquivalent));
    }

    @Test
    public void equalsTest_PassedInequivalentSquare_ReturnFalse(){
        Square squareEquivalent = new Square(new Coordinates(3,2));

        Assert.assertFalse(square.equals(squareEquivalent));
    }

    @Test
    public void equalsTest_PassedNull_ReturnFalse(){
        Assert.assertFalse(square.equals(null));
    }

}
