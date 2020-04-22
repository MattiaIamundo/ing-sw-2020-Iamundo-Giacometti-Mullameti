package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.MapUpdate;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.utility.Observer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Test
    public void isThisPerimeterSquare_x0y3_ReturnTrue(){
        Square square = new Square(new Coordinates(0, 3));
        
        Assert.assertTrue(map.isThisPerimeterSquare(square));
    }

    @Test
    public void isThisPerimeterSquare_x2y0_ReturnTrue(){
        Square square = new Square(new Coordinates(2, 0));

        Assert.assertTrue(map.isThisPerimeterSquare(square));
    }

    @Test
    public void isThisPerimeterSquare_x4y3_ReturnTrue(){
        Square square = new Square(new Coordinates(4, 3));

        Assert.assertTrue(map.isThisPerimeterSquare(square));
    }

    @Test
    public void isThisPerimeterSquare_x3y4_ReturnTrue(){
        Square square = new Square(new Coordinates(3, 4));

        Assert.assertTrue(map.isThisPerimeterSquare(square));
    }

    @Test
    public void isThisPerimeterSquare_x0y0_ReturnTrue(){
        Square square = new Square(new Coordinates(0, 0));

        Assert.assertTrue(map.isThisPerimeterSquare(square));
    }

    @Test
    public void isThisPerimeterSquare_x4y0_ReturnTrue(){
        Square square = new Square(new Coordinates(4, 0));

        Assert.assertTrue(map.isThisPerimeterSquare(square));
    }

    @Test
    public void isThisPerimeterSquare_x4y4_ReturnTrue(){
        Square square = new Square(new Coordinates(4, 4));

        Assert.assertTrue(map.isThisPerimeterSquare(square));
    }

    @Test
    public void isThisPerimeterSquare_x2y3_ReturnFalse(){
        Square square = new Square(new Coordinates(2, 3));

        Assert.assertFalse(map.isThisPerimeterSquare(square));
    }

    @Test
    public void AlternativeConstructorTest(){
        map = new Map(8, 8);

        try {
            Assert.assertEquals(map.getSquare(6,6), new Square(new Coordinates(6,6)));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void iteratorTest_WhileHasNext(){
        Iterator<Square> iter = map.iterator();
        List<Square> out = new ArrayList<>();
        List<Square> expected = new ArrayList<>();

        expected.add(new Square(new Coordinates(0,0)));
        expected.add(new Square(new Coordinates(1,0)));
        expected.add(new Square(new Coordinates(2,0)));
        expected.add(new Square(new Coordinates(3,0)));
        expected.add(new Square(new Coordinates(4,0)));
        expected.add(new Square(new Coordinates(0,1)));
        expected.add(new Square(new Coordinates(1,1)));
        expected.add(new Square(new Coordinates(2,1)));
        expected.add(new Square(new Coordinates(3,1)));
        expected.add(new Square(new Coordinates(4,1)));
        expected.add(new Square(new Coordinates(0,2)));
        expected.add(new Square(new Coordinates(1,2)));
        expected.add(new Square(new Coordinates(2,2)));
        expected.add(new Square(new Coordinates(3,2)));
        expected.add(new Square(new Coordinates(4,2)));
        expected.add(new Square(new Coordinates(0,3)));
        expected.add(new Square(new Coordinates(1,3)));
        expected.add(new Square(new Coordinates(2,3)));
        expected.add(new Square(new Coordinates(3,3)));
        expected.add(new Square(new Coordinates(4,3)));
        expected.add(new Square(new Coordinates(0,4)));
        expected.add(new Square(new Coordinates(1,4)));
        expected.add(new Square(new Coordinates(2,4)));
        expected.add(new Square(new Coordinates(3,4)));
        expected.add(new Square(new Coordinates(4,4)));


        while (iter.hasNext()){
            out.add(iter.next());
        }

        Assert.assertEquals(expected, out);
    }

    @Test
    public void iteratorTest_ForEachLoop(){
        List<Square> out = new ArrayList<>();
        List<Square> expected = new ArrayList<>();

        expected.add(new Square(new Coordinates(0,0)));
        expected.add(new Square(new Coordinates(1,0)));
        expected.add(new Square(new Coordinates(2,0)));
        expected.add(new Square(new Coordinates(3,0)));
        expected.add(new Square(new Coordinates(4,0)));
        expected.add(new Square(new Coordinates(0,1)));
        expected.add(new Square(new Coordinates(1,1)));
        expected.add(new Square(new Coordinates(2,1)));
        expected.add(new Square(new Coordinates(3,1)));
        expected.add(new Square(new Coordinates(4,1)));
        expected.add(new Square(new Coordinates(0,2)));
        expected.add(new Square(new Coordinates(1,2)));
        expected.add(new Square(new Coordinates(2,2)));
        expected.add(new Square(new Coordinates(3,2)));
        expected.add(new Square(new Coordinates(4,2)));
        expected.add(new Square(new Coordinates(0,3)));
        expected.add(new Square(new Coordinates(1,3)));
        expected.add(new Square(new Coordinates(2,3)));
        expected.add(new Square(new Coordinates(3,3)));
        expected.add(new Square(new Coordinates(4,3)));
        expected.add(new Square(new Coordinates(0,4)));
        expected.add(new Square(new Coordinates(1,4)));
        expected.add(new Square(new Coordinates(2,4)));
        expected.add(new Square(new Coordinates(3,4)));
        expected.add(new Square(new Coordinates(4,4)));

        for (Square square : map){
            out.add(square);
        }

        Assert.assertEquals(expected, out);
    }

    @Test
    public void equalsTest_Itself_ReturnTrue() {
        Assert.assertEquals(map, map);
    }

    @Test
    public void equalsTest_EqualObject_ReturnFalse(){
        Map eqMap = new Map();

        Assert.assertEquals(eqMap, map);
    }

    @Test
    public void equalsTest_Null_ReturnFalse(){
        Assert.assertFalse(map.equals(null));
    }

    @Test
    public void equalsTest_DifferentMap_ReturnFalse(){
        Map map2 = new Map();
        try {
            map2.getSquare(2,2).setLevel(Level.FIRST);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(map.equals(map2));
    }

    @Test
    public void cloneTest_VerifyCopyIsEqualToTheOriginal(){
        List<Square> copy = new ArrayList<>();
        List<Square> original = new ArrayList<>();

        try {
            Map deepCopy = (Map) map.clone();

            for (Square square : map){
                original.add(square);
            }

            for (Square square : deepCopy){
                copy.add(square);
            }

            Assert.assertEquals(copy, original);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cloneTest_ChangeLevel_CopyMustBeDifferent(){
        List<Square> copy = new ArrayList<>();
        List<Square> original = new ArrayList<>();

        try {
            Map deepCopy = (Map) map.clone();

            for (Square square : map){
                original.add(square);
            }

            for (Square square : deepCopy){
                copy.add(square);
            }

            map.getSquare(2,2).setLevel(Level.FIRST);

            Assert.assertNotEquals(map.getSquare(2,2), deepCopy.getSquare(2,2));

            original.remove(map.getSquare(2,2));
            copy.remove(deepCopy.getSquare(2,2));

            Assert.assertEquals(original, copy);
        } catch (CloneNotSupportedException | OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cloneTest_ChangeIsPresentWorker_CopyMustBeDifferent(){
        List<Square> copy = new ArrayList<>();
        List<Square> original = new ArrayList<>();

        try {
            Map deepCopy = (Map) map.clone();

            for (Square square : map){
                original.add(square);
            }

            for (Square square : deepCopy){
                copy.add(square);
            }

            map.getSquare(2,2).setPresentWorker(true);

            Assert.assertNotEquals(map.getSquare(2,2).isPresentWorker(), deepCopy.getSquare(2,2).isPresentWorker());

            original.remove(map.getSquare(2,2));
            copy.remove(deepCopy.getSquare(2,2));

            Assert.assertEquals(original, copy);
        } catch (CloneNotSupportedException | OutOfMapException e) {
            e.printStackTrace();
        }
    }


    private class Messagereceiver implements Observer<EventForClient>{

        EventForClient event;

        @Override
        public void update(EventForClient message) {
            event = message;
        }
    }
}
