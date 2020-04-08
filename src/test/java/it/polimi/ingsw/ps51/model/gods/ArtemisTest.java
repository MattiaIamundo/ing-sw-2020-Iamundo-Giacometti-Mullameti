package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArtemisTest {

    private Artemis artemis;
    private Worker worker;
    private Player player;
    private Map map;

    @Before
    public void setUp() {
        this.artemis = new Artemis();
        this.worker = new Worker("Merita");
        this.player = new Player("Merita");
        this.map = new Map();
    }

    @After
    public void tearDown() {
        this.artemis = null;
        this.worker = null;
        this.player = null;
        this.map = null;
    }

    @Test
    public void checkMovesTest() {

        boolean containsAll = false;

        try{

            this.worker.setPosition(map.getSquare(1,1));
            this.map.getSquare(1,1).setLevel(Level.GROUND);

            this.map.getSquare(0,0).setLevel(Level.GROUND);
            this.map.getSquare(0,1).setLevel(Level.FIRST);
            this.map.getSquare(0,2).setLevel(Level.THIRD);
            this.map.getSquare(0,3).setLevel(Level.DOME);
            this.map.getSquare(1,0).setLevel(Level.FIRST);
            this.map.getSquare(1,2).setLevel(Level.FIRST);
            this.map.getSquare(1,3).setLevel(Level.THIRD);
            this.map.getSquare(2,0).setLevel(Level.DOME);
            this.map.getSquare(2,1).setLevel(Level.GROUND);
            this.map.getSquare(2,2).setLevel(Level.SECOND);
            this.map.getSquare(2,3).setLevel(Level.FIRST);
            this.map.getSquare(3,0).setLevel(Level.DOME);
            this.map.getSquare(3,1).setLevel(Level.GROUND);
            this.map.getSquare(3,2).setLevel(Level.FIRST);
            this.map.getSquare(3,3).setLevel(Level.THIRD);



            Worker worker2 = new Worker("Merita");
            worker2.setPosition(map.getSquare(2,2));
            this.map.getSquare(2,2).setLevel(Level.SECOND);

            Worker worker3 = new Worker("Luca");
            worker3.setPosition(map.getSquare(0,1));
            this.map.getSquare(0,1).setLevel(Level.SECOND);


        } catch (OutOfMapException e) {
            e.printStackTrace();
        }

        List<Coordinates> validSquares = this.artemis.checkMoves(this.player, this.worker, this.map);
        List<Coordinates> squares = new ArrayList<>();

        squares.add(new Coordinates(0,0));
        squares.add(new Coordinates(1,0));
        squares.add(new Coordinates(1,2));
        squares.add(new Coordinates(2,1));
        squares.add(new Coordinates(2,3));
        squares.add(new Coordinates(3,1));
        squares.add(new Coordinates(3,2));

        containsAll = squares.containsAll(validSquares);
        Assert.assertTrue(containsAll);


    }
}