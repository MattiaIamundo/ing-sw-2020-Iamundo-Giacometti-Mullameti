package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AtlasTest {

    private Worker workerPlayer;
    private Map mapOfGame;
    private Atlas atlas;

    @Before
    public void setUp() {
        this.atlas = new Atlas();
        this.mapOfGame = new Map();
        this.workerPlayer = new Worker("Luca");
    }

    @After
    public void tearDown() {
        this.workerPlayer = null;
        this.mapOfGame = null;
        this.atlas = null;
    }

    @Test
    public void checkBuildTest_workerAndMapWithOneAdjacentSquareOccupiedAndOneAdjacentDome_shouldReturnSixCommonPairPlusFiveDomesPair() {

        boolean isOk;

        try {
            this.workerPlayer.setPosition(mapOfGame.getSquare(1,2));
            this.mapOfGame.getSquare(1,2).setPresentWorker(this.workerPlayer);
            Worker worker2 = new Worker("Merita");
            worker2.setPosition(mapOfGame.getSquare(2,2));
            this.mapOfGame.getSquare(2,2).setPresentWorker(worker2);

            this.mapOfGame.getSquare(0,1).setLevel(Level.SECOND);
            this.mapOfGame.getSquare(0,2).setLevel(Level.FIRST);
            this.mapOfGame.getSquare(1,1).setLevel(Level.THIRD);
            this.mapOfGame.getSquare(2,1).setLevel(Level.DOME);

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }

        List<Pair<Coordinates, List<Level>>> validBuildsAtlas = this.atlas.checkBuild(this.workerPlayer, this.mapOfGame);
        List<Pair<Coordinates, List<Level>>> pairs = new ArrayList<>();

        pairs.add(new Pair<>(new Coordinates(0,1), new ArrayList<>(Arrays.asList(Level.THIRD, Level.DOME))));
        pairs.add(new Pair<>(new Coordinates(0,2), new ArrayList<>(Arrays.asList(Level.SECOND, Level.DOME))));
        pairs.add(new Pair<>(new Coordinates(0,3), new ArrayList<>(Arrays.asList(Level.FIRST, Level.DOME))));
        pairs.add(new Pair<>(new Coordinates(1,1), new ArrayList<>(Collections.singleton(Level.DOME))));
        pairs.add(new Pair<>(new Coordinates(1,3), new ArrayList<>(Arrays.asList(Level.FIRST, Level.DOME))));
        pairs.add(new Pair<>(new Coordinates(2,3), new ArrayList<>(Arrays.asList(Level.FIRST, Level.DOME))));

        isOk = pairs.containsAll(validBuildsAtlas);
        Assert.assertTrue(isOk);
    }

}