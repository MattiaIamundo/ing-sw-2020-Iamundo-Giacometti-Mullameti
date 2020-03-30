package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MapTest {

    private Map map;

    @Before
    public void initializeClass(){
        map = new Map();
    }

    @After
    public void tierDown(){
        map = null;
    }

    @Test
    public void getSquare_x2y2_ShouldReturnSquare(){
        try {
            Square square = map.getSquare(2,2);
            Square expectedSquare = new Square(new Coordinates(2, 2));
            Assert.assertEquals(expectedSquare, square);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = OutOfMapException.class)
    public void getSquare_x5y5_ShouldThrowException() throws OutOfMapException {
        Square square = map.getSquare(5,5);
    }

    @Test
    public void getAdjacentSquare_x3y3_ListFullOfSquare(){
        try {
            Square square = map.getSquare(3,3);
            List<Square> adjacentSquare;
            List<Square> expectedList = new ArrayList<>();
            adjacentSquare = map.getAdjacentSquare(square);
            expectedList.add(new Square(new Coordinates(2,2)));
            expectedList.add(new Square(new Coordinates(3,2)));
            expectedList.add(new Square(new Coordinates(4,2)));
            expectedList.add(new Square(new Coordinates(4,3)));
            expectedList.add(new Square(new Coordinates(4,4)));
            expectedList.add(new Square(new Coordinates(3,4)));
            expectedList.add(new Square(new Coordinates(2,4)));
            expectedList.add(new Square(new Coordinates(2,3)));

            Assert.assertEquals(expectedList, adjacentSquare);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAdjacentSquare_x0y0_ListWithThreeElements(){
        try {
            Square square = map.getSquare(0,0);
            List<Square> adjacentSquare;
            List<Square> expectedList = new ArrayList<>();
            adjacentSquare = map.getAdjacentSquare(square);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(new Square(new Coordinates(1,0)));
            expectedList.add(new Square(new Coordinates(1,1)));
            expectedList.add(new Square(new Coordinates(0,1)));
            expectedList.add(null);
            expectedList.add(null);

            Assert.assertEquals(expectedList, adjacentSquare);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAdjacentSquare_x4y0_ListWithThreeElements(){
        try {
            Square square = map.getSquare(4,0);
            List<Square> adjacentSquare;
            List<Square> expectedList = new ArrayList<>();
            adjacentSquare = map.getAdjacentSquare(square);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(new Square(new Coordinates(4,1)));
            expectedList.add(new Square(new Coordinates(3,1)));
            expectedList.add(new Square(new Coordinates(3,0)));

            Assert.assertEquals(expectedList, adjacentSquare);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAdjacentSquare_x4y4_ListWithThreeElements(){
        try {
            Square square = map.getSquare(4,4);
            List<Square> adjacentSquare;
            List<Square> expectedList = new ArrayList<>();
            adjacentSquare = map.getAdjacentSquare(square);
            expectedList.add(new Square(new Coordinates(3,3)));
            expectedList.add(new Square(new Coordinates(4,3)));
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(new Square(new Coordinates(3,4)));

            Assert.assertEquals(expectedList, adjacentSquare);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAdjacentSquare_x0y4_ListWithThreeElements(){
        try {
            Square square = map.getSquare(0,4);
            List<Square> adjacentSquare;
            List<Square> expectedList = new ArrayList<>();
            adjacentSquare = map.getAdjacentSquare(square);
            expectedList.add(null);
            expectedList.add(new Square(new Coordinates(0,3)));
            expectedList.add(new Square(new Coordinates(1,3)));
            expectedList.add(new Square(new Coordinates(1,4)));
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);

            Assert.assertEquals(expectedList, adjacentSquare);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAdjacentSquare_x3y0_ListWithFiveElements(){
        try {
            Square square = map.getSquare(3,0);
            List<Square> adjacentSquare;
            List<Square> expectedList = new ArrayList<>();
            adjacentSquare = map.getAdjacentSquare(square);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(new Square(new Coordinates(4,0)));
            expectedList.add(new Square(new Coordinates(4,1)));
            expectedList.add(new Square(new Coordinates(3,1)));
            expectedList.add(new Square(new Coordinates(2,1)));
            expectedList.add(new Square(new Coordinates(2,0)));

            Assert.assertEquals(expectedList, adjacentSquare);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAdjacentSquare_x4y3_ListWithFiveElements(){
        try {
            Square square = map.getSquare(4,3);
            List<Square> adjacentSquare;
            List<Square> expectedList = new ArrayList<>();
            adjacentSquare = map.getAdjacentSquare(square);
            expectedList.add(new Square(new Coordinates(3,2)));
            expectedList.add(new Square(new Coordinates(4,2)));
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(new Square(new Coordinates(4,4)));
            expectedList.add(new Square(new Coordinates(3,4)));
            expectedList.add(new Square(new Coordinates(3,3)));

            Assert.assertEquals(expectedList, adjacentSquare);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAdjacentSquare_x3y4_ListWithFiveElements(){
        try {
            Square square = map.getSquare(3,4);
            List<Square> adjacentSquare;
            List<Square> expectedList = new ArrayList<>();
            adjacentSquare = map.getAdjacentSquare(square);
            expectedList.add(new Square(new Coordinates(2,3)));
            expectedList.add(new Square(new Coordinates(3,3)));
            expectedList.add(new Square(new Coordinates(4,3)));
            expectedList.add(new Square(new Coordinates(4,4)));
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(null);
            expectedList.add(new Square(new Coordinates(2,4)));

            Assert.assertEquals(expectedList, adjacentSquare);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAdjacentSquare_x0y3_ListWithFiveElements(){
        try {
            Square square = map.getSquare(0,3);
            List<Square> adjacentSquare;
            List<Square> expectedList = new ArrayList<>();
            adjacentSquare = map.getAdjacentSquare(square);
            expectedList.add(null);
            expectedList.add(new Square(new Coordinates(0,2)));
            expectedList.add(new Square(new Coordinates(1,2)));
            expectedList.add(new Square(new Coordinates(1,3)));
            expectedList.add(new Square(new Coordinates(1,4)));
            expectedList.add(new Square(new Coordinates(0,4)));
            expectedList.add(null);
            expectedList.add(null);

            Assert.assertEquals(expectedList, adjacentSquare);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }
}
