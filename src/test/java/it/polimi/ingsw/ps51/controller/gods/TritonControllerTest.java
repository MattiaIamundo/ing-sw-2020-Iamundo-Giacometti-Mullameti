package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.*;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Triton;
import it.polimi.ingsw.ps51.utility.GameObserver;
import it.polimi.ingsw.ps51.utility.Observer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TritonControllerTest {

    Map map;
    Player player;
    Worker worker;
    TritonController controller;
    PhantomeGame game;
    Stub stub;


    private class PhantomeGame implements GameObserver{
        private ControllerToGame event;

        @Override
        public void update(ControllerToGame message) {
            event = message;
        }

        @Override
        public void update(EventForServer message) {

        }
    }


    private class Stub implements Observer<EventForClient>{
        private EventForClient event;

        private List<EventForClient> buffer = new ArrayList<>();


        @Override
        public void update(EventForClient message) {
            event = message;
            buffer.add(message);
        }
    }

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        Playground playground = new Playground(Collections.singletonList(player));
        map = playground.getBoardMap();

        try {
            worker = new Worker("Player", map.getSquare(1,1));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        player.addWorker(worker);
        controller = new TritonController(new Triton(), map, player);
        controller.selectedWorker = worker;
        game = new PhantomeGame();
        stub = new Stub();
        controller.addGame(game);
        controller.addObserver(stub);
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        player = null;
        worker = null;
        controller = null;
        game = null;
        stub = null;
    }

    @Test
    public void performMoveTest_MoveToInnerSquare_PowerNotUsableTurnEnd() {
        controller.performMove(new Coordinates(1,2));

        assertTrue(stub.event instanceof ChooseBuild);
    }

    @Test
    public void performMoveTest_MoveToPerimeterSquareNotUsePower_AskedIfMoveAgain() {
        controller.performMove(new Coordinates(1,0));

        assertTrue(stub.event instanceof MakeDecision);
        controller.decisionManager(false);
        assertTrue(stub.event instanceof ChooseBuild);
    }

    @Test
    public void performMoveTest_MoveToPerimeterSquareUsePower_AskedIfMoveAgain() {
        controller.performMove(new Coordinates(1,0));

        assertTrue(stub.event instanceof MakeDecision);
        controller.decisionManager(true);
        assertTrue(stub.event instanceof ChooseMove);
    }

    @Test
    public void preformMoveTest_OutOfMapCoordinates_AskedToRedoAction(){
        controller.manageMoveChoice(new Coordinates(4,5));

        assertEquals(3, stub.buffer.size());
        assertTrue(stub.buffer.get(0) instanceof Ack);
        assertTrue(stub.buffer.get(1) instanceof UnsuccessfulOperation);
        assertTrue(stub.buffer.get(2) instanceof ChooseMove);
    }

    @Test
    public void performMoveTest_MoveToWinning_WinMatch(){
        try {
            map.getSquare(1,1).setLevel(Level.SECOND);
            map.getSquare(1,2).setLevel(Level.THIRD);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        controller.performMove(new Coordinates(1,2));
        assertEquals(ControllerToGame.WINNER, game.event);
    }

    @Test
    public void decisionManagerTest_True_AskedForWhereToMove() {
        controller.decisionManager(true);

        assertTrue(stub.event instanceof ChooseMove);
    }

    @Test
    public void decisionManagerTest_False_AskedForWhereToBuild() {
        controller.decisionManager(false);

        assertTrue(stub.event instanceof ChooseBuild);
    }
}