package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.*;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Artemis;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.utility.GameObserver;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.Observer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ArtemisControllerTest {

    Player player;
    Worker worker1;
    Worker worker2;
    Map map;
    Stub stub;
    Card card;
    ArtemisController controller;
    PhantomGame game;

    private class PhantomGame extends Observable<EventForClient> implements GameObserver {

        private ControllerToGame event;

        @Override
        public void update(ControllerToGame message) {
            event = message;
        }

        @Override
        public void update(EventForServer message) {

        }
    }


    private class Stub extends Observable<EventForServer> implements Observer<EventForClient>{

        private EventForClient event;
        private List<EventForClient> buffer = new ArrayList<>();

        protected void clearBuffer(){
            buffer = new ArrayList<>();
        }

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
            worker1 = new Worker("Player", map.getSquare(2,2));
            worker2 = new Worker("Player", map.getSquare(4,4));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }

        player.setWorkers(Arrays.asList(worker1, worker2));
        card = new Artemis();
        controller = new ArtemisController(card, map, player);
        stub = new Stub();
        game = new PhantomGame();
        controller.addGame(game);
        controller.addObserver(stub);
        playground.addObserver(stub);
        controller.selectedWorker = worker1;
    }

    @After
    public void tearDown() throws Exception {
        player = null;
        worker1 = null;
        worker2 = null;
        map = null;
        card = null;
        controller = null;
        game = null;
        stub = null;
    }

    @Test
    public void searchForMovesTest_FirstMove_AllPossibleMovements() {
        List<Coordinates> expected = card.checkMoves(player, worker1, map);
        controller.selectedWorker = worker1;
        controller.searchForMoves();

        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseMove);
        assertEquals(expected, ((ChooseMove) stub.event).getValidChoices());
    }

    @Test
    public void searchForMovesTest_SecondMove_CannotMoveOnPreviousPosition() {
        controller.selectedWorker = worker1;
        controller.performMove(new Coordinates(2,3));
        controller.decisionManager(true);
        List<Coordinates> expected = card.checkMoves(player, worker1, map);
        expected.remove(new Coordinates(2,2));

        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseMove);
        assertEquals(expected, ((ChooseMove) stub.event).getValidChoices());
    }

    @Test
    public void performMoveTest_MoveToWinning_Win(){
        try {
            map.getSquare(2,2).setLevel(Level.SECOND);
            map.getSquare(2,3).setLevel(Level.THIRD);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        controller.performMove(new Coordinates(2,3));

        assertEquals(game.event, ControllerToGame.WINNER);
    }

    @Test
    public void performMoveTest_InvalidCoordinates_RequestToRedoAction(){
        controller.performMove(new Coordinates(5,3));

        assertTrue(stub.buffer.get(0) instanceof UnsuccessfulOperation);
        assertTrue(stub.buffer.get(1) instanceof ChooseMove);
    }

    @Test
    public void performMoveTest_FirstMove_ReceiveDecision() {
        controller.selectedWorker = worker1;
        controller.performMove(new Coordinates(1,2));

        assertEquals(new Coordinates(1,2), worker1.getPosition().getCoordinates());
        assertTrue(stub.event instanceof MakeDecision);
    }

    @Test
    public void performMoveTest_SecondMove_ReceiveChooseBuild() {
        controller.selectedWorker = worker1;
        controller.decisionManager(true);
        controller.performMove(new Coordinates(1,2));

        assertEquals(new Coordinates(1,2), worker1.getPosition().getCoordinates());
        assertTrue(stub.event instanceof ChooseBuild);
    }

    @Test
    public void decisionManagerTest_True_RequestForSecondMove() {
        controller.performMove(new Coordinates(2,3));
        controller.decisionManager(true);

        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseMove);
    }

    @Test
    public void decisionManagerTest_False_RequestForBuild() {
        controller.decisionManager(false);

        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseBuild);
    }
}