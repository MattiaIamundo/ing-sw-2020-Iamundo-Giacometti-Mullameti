package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonActionTest {
    Map map;
    Worker worker;
    Player player;
    PhantomCard card = new PhantomCard();

    private static class PhantomCard extends CommonAction{

    }

    @Before
    public void setUp(){
        map = new Map();
        worker = new Worker("Player");
        player = new Player("Player");
    }

    @After
    public void tearDown(){
        map = null;
        worker = null;
        player = null;
    }

    @Test
    public void checkMoves_x0y0_ReturnThreePositions() {
        try {
            worker.setPosition(map.getSquare(0,0));

            map.getSquare(1,0).setLevel(Level.FIRST);

            List<Coordinates> output = card.checkMoves(player, worker, map);
            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,0));
            expected.add(new Coordinates(1,1));
            expected.add(new Coordinates(0,1));

            Assert.assertEquals(expected, output);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkMoves_x2y2WithTwoToHighTower_ReturnSixPositions() {
        try {
            worker.setPosition(map.getSquare(2,2));

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(3,1).setLevel(Level.THIRD);
            map.getSquare(2,3).setLevel(Level.FIRST);
            map.getSquare(1,2).setLevel(Level.SECOND);

            List<Coordinates> output = card.checkMoves(player, worker, map);
            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,1));
            expected.add(new Coordinates(2,1));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(2,3));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, output);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkMoves_x2y2WithDome_ReturnSixPositions() {
        try {
            worker.setPosition(map.getSquare(2,2));

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(3,1).setLevel(Level.DOME);
            map.getSquare(2,3).setLevel(Level.FIRST);
            map.getSquare(1,2).setLevel(Level.DOME);

            List<Coordinates> output = card.checkMoves(player, worker, map);
            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,1));
            expected.add(new Coordinates(2,1));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(2,3));
            expected.add(new Coordinates(1,3));

            Assert.assertEquals(expected, output);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkMoves_x2y2WorkerAtLevelSecond_ReturnEightPositions() {
        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setLevel(Level.SECOND);

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(3,1).setLevel(Level.THIRD);
            map.getSquare(2,3).setLevel(Level.FIRST);
            map.getSquare(1,2).setLevel(Level.SECOND);

            List<Coordinates> output = card.checkMoves(player, worker, map);
            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,1));
            expected.add(new Coordinates(2,1));
            expected.add(new Coordinates(3,1));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(2,3));
            expected.add(new Coordinates(1,3));
            expected.add(new Coordinates(1,2));

            Assert.assertEquals(expected, output);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkMoves_x2y2WithOccupiedSquare_ReturnSevenPositions() {
        try {
            Worker worker2 = new Worker("Worker2");
            worker2.setPosition(map.getSquare(3,1));

            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setLevel(Level.FIRST);

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(3,1).setLevel(Level.SECOND);
            map.getSquare(2,3).setLevel(Level.FIRST);
            map.getSquare(1,2).setLevel(Level.SECOND);

            List<Coordinates> output = card.checkMoves(player, worker, map);
            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(1,1));
            expected.add(new Coordinates(2,1));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(2,3));
            expected.add(new Coordinates(1,3));
            expected.add(new Coordinates(1,2));

            Assert.assertEquals(expected, output);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkMoves_x2y2WithAthena_ReturnSixPositions() {
        try {
            worker.setPosition(map.getSquare(2,2));
            worker.update(Gods.ATHENA);

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(2,3).setLevel(Level.FIRST);

            List<Coordinates> output = card.checkMoves(player, worker, map);
            List<Coordinates> expected = new ArrayList<>();

            expected.add(new Coordinates(2,1));
            expected.add(new Coordinates(3,1));
            expected.add(new Coordinates(3,2));
            expected.add(new Coordinates(3,3));
            expected.add(new Coordinates(1,3));
            expected.add(new Coordinates(1,2));

            Assert.assertEquals(expected, output);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkBuild_x4y0_ReturnThreeValidPositions() {
        try {
            worker.setPosition(map.getSquare(4,0));

            map.getSquare(3,0).setLevel(Level.SECOND);
            map.getSquare(4,1).setLevel(Level.FIRST);

            List<Pair<Coordinates, List<Level>>> output = card.checkBuild(worker, map);
            List<Pair<Coordinates, List<Level>>> excpected = new ArrayList<>();

            excpected.add(new Pair<>(new Coordinates(4,1), new ArrayList<>(Collections.singleton(Level.SECOND))));
            excpected.add(new Pair<>(new Coordinates(3,1), new ArrayList<>(Collections.singleton(Level.FIRST))));
            excpected.add(new Pair<>(new Coordinates(3,0), new ArrayList<>(Collections.singleton(Level.THIRD))));

            Assert.assertEquals(excpected, output);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkBuild_x3y3_ReturnEightValidPositions() {
        try {
            worker.setPosition(map.getSquare(3,3));
            map.getSquare(3,3).setLevel(Level.THIRD);

            map.getSquare(2,2).setLevel(Level.SECOND);
            map.getSquare(4,3).setLevel(Level.THIRD);
            map.getSquare(4,4).setLevel(Level.FIRST);
            map.getSquare(3,4).setLevel(Level.FIRST);
            map.getSquare(2,4).setLevel(Level.THIRD);

            List<Pair<Coordinates, List<Level>>> output = card.checkBuild(worker, map);
            List<Pair<Coordinates, List<Level>>> excpected = new ArrayList<>();

            excpected.add(new Pair<>(new Coordinates(2,2), new ArrayList<>(Collections.singleton(Level.THIRD))));
            excpected.add(new Pair<>(new Coordinates(3,2), new ArrayList<>(Collections.singleton(Level.FIRST))));
            excpected.add(new Pair<>(new Coordinates(4,2), new ArrayList<>(Collections.singleton(Level.FIRST))));
            excpected.add(new Pair<>(new Coordinates(4,3), new ArrayList<>(Collections.singleton(Level.DOME))));
            excpected.add(new Pair<>(new Coordinates(4,4), new ArrayList<>(Collections.singleton(Level.SECOND))));
            excpected.add(new Pair<>(new Coordinates(3,4), new ArrayList<>(Collections.singleton(Level.SECOND))));
            excpected.add(new Pair<>(new Coordinates(2,4), new ArrayList<>(Collections.singleton(Level.DOME))));
            excpected.add(new Pair<>(new Coordinates(2,3), new ArrayList<>(Collections.singleton(Level.FIRST))));

            Assert.assertEquals(excpected, output);
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void move_x2y2WithAthena_TheWorkerIsMoved() {
        try {
            worker.setPosition(map.getSquare(2,2));
            worker.update(Gods.ATHENA);

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(2,3).setLevel(Level.FIRST);

            card.move(player, worker, map.getSquare(2,1), map);

            Assert.assertEquals(map.getSquare(2,1), worker.getPosition());
            Assert.assertFalse(worker.getInWinningCondition());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void move_x2y2GoesUp_TheWorkerIsMoved() {
        try {
            worker.setPosition(map.getSquare(2,2));

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(2,3).setLevel(Level.FIRST);

            card.move(player, worker, map.getSquare(2,3), map);

            Assert.assertEquals(map.getSquare(2,3), worker.getPosition());
            Assert.assertFalse(worker.getInWinningCondition());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void move_x2y2GoesUpWinning_TheWorkerIsMovedAndWin() {
        try {
            worker.setPosition(map.getSquare(2,2));
            map.getSquare(2,2).setLevel(Level.SECOND);

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(2,3).setLevel(Level.THIRD);

            card.move(player, worker, map.getSquare(2,3), map);

            Assert.assertEquals(map.getSquare(2,3), worker.getPosition());
            Assert.assertTrue(worker.getInWinningCondition());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void move_x2y2InvalidMoveToHigh_TheWorkerIsNotMoved() {
        try {
            worker.setPosition(map.getSquare(2,2));

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(1,2).setLevel(Level.SECOND);
            map.getSquare(2,3).setLevel(Level.FIRST);

            card.move(player, worker, map.getSquare(1,2), map);

            Assert.assertEquals(map.getSquare(2,2), worker.getPosition());
            Assert.assertFalse(worker.getInWinningCondition());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void move_x2y2InvalidMoveToFar_TheWorkerIsMoved() {
        try {
            worker.setPosition(map.getSquare(2,2));

            map.getSquare(1,1).setLevel(Level.FIRST);
            map.getSquare(2,3).setLevel(Level.FIRST);

            card.move(player, worker, map.getSquare(2,4), map);

            Assert.assertEquals(map.getSquare(2,2), worker.getPosition());
            Assert.assertFalse(worker.getInWinningCondition());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void build_x3y3BuildOnSecondLevel_TheLevelIsBuilt() {
        try {
            worker.setPosition(map.getSquare(3,3));
            map.getSquare(3,3).setLevel(Level.THIRD);

            map.getSquare(2,2).setLevel(Level.SECOND);
            map.getSquare(4,3).setLevel(Level.THIRD);
            map.getSquare(4,4).setLevel(Level.FIRST);
            map.getSquare(3,4).setLevel(Level.FIRST);
            map.getSquare(2,4).setLevel(Level.THIRD);

            card.build(worker, map.getSquare(2,2), Level.THIRD, map);

            Assert.assertEquals(Level.THIRD, map.getSquare(2,2).getLevel());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void build_x4y2BuildOnThirdLevel_TheLevelIsBuilt() {
        try {
            worker.setPosition(map.getSquare(4,2));

            map.getSquare(2,2).setLevel(Level.SECOND);
            map.getSquare(4,3).setLevel(Level.THIRD);
            map.getSquare(4,4).setLevel(Level.FIRST);
            map.getSquare(3,4).setLevel(Level.FIRST);
            map.getSquare(2,4).setLevel(Level.THIRD);

            card.build(worker, map.getSquare(4,3), Level.DOME, map);

            Assert.assertEquals(Level.DOME, map.getSquare(4,3).getLevel());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void build_x4y2BuildThirdOnFirstLevel_TheLevelIsNotBuilt() {
        try {
            worker.setPosition(map.getSquare(4,2));

            map.getSquare(2,2).setLevel(Level.SECOND);
            map.getSquare(4,3).setLevel(Level.THIRD);
            map.getSquare(4,4).setLevel(Level.FIRST);
            map.getSquare(3,4).setLevel(Level.FIRST);
            map.getSquare(2,4).setLevel(Level.THIRD);

            card.build(worker, map.getSquare(4,4), Level.THIRD, map);

            Assert.assertEquals(Level.FIRST, map.getSquare(4,4).getLevel());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

    @Test
    public void build_x4y2BuildOutsideTheRange_TheLevelIsNotBuilt() {
        try {
            worker.setPosition(map.getSquare(4,2));

            map.getSquare(2,2).setLevel(Level.SECOND);
            map.getSquare(4,3).setLevel(Level.THIRD);
            map.getSquare(4,4).setLevel(Level.FIRST);
            map.getSquare(3,4).setLevel(Level.FIRST);
            map.getSquare(2,4).setLevel(Level.THIRD);

            card.build(worker, map.getSquare(1,1), Level.FIRST, map);

            Assert.assertEquals(Level.GROUND, map.getSquare(1,1).getLevel());
        }catch (OutOfMapException e){
            e.printStackTrace();
        }
    }

}