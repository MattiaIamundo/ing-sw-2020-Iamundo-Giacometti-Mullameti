package it.polimi.ingsw.ps51.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CoordinatesTest {
    Coordinates coordinates;

    @Before
    public void setUp() throws Exception {
        coordinates = new Coordinates(3,2);
    }

    @After
    public void tearDown() throws Exception {
        coordinates = null;
    }

    @Test
    public void getCoordinates_x3y2_ExpectedListThreeTwo() {
        List<Integer> output = coordinates.getCoordinates();
        List<Integer> expected = new ArrayList<>(Arrays.asList(3,2));

        Assert.assertEquals(expected, output);
    }

    @Test
    public void testEquals_Itself_ReturnTrue() {
        Assert.assertTrue(coordinates.equals(coordinates));
    }

    @Test
    public void testEquals_EquivalentObject_ReturnTrue() {
        Coordinates coordinates2 = new Coordinates(3,2);
        Assert.assertTrue(coordinates.equals(coordinates2));
    }

    @Test
    public void testEquals_Null_ReturnFalse() {
        Assert.assertFalse(coordinates.equals(null));
    }

    @Test
    public void testHashCode() {
        Assert.assertEquals(5, coordinates.hashCode());
    }
}