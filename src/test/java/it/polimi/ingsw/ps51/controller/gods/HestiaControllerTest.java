package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.*;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.Demeter;
import it.polimi.ingsw.ps51.model.gods.Hestia;
import it.polimi.ingsw.ps51.utility.GameObserver;
import it.polimi.ingsw.ps51.utility.Observer;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class HestiaControllerTest {

    private Map map;
    private PhantomGame game;
    private Card card;
    private Player player;
    private Worker worker1;
    private Worker worker2;
    private Stub stub;
    private HestiaController controller;

    private class Stub implements Observer<EventForClient> {

        private EventForClient event;

        private List<EventForClient> buffer = new ArrayList<>();

        @Override
        public void update(EventForClient message) {
            event = message;
            buffer.add(message);
        }

        protected void clearBuffer(){
            buffer = new ArrayList<>();
        }
    }

    private class PhantomGame implements GameObserver {

        private ControllerToGame event;

        @Override
        public void update(ControllerToGame message) {
            event = message;
        }

        @Override
        public void update(EventForServer message) {

        }
    }

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        Playground playground = new Playground(Collections.singletonList(player));
        map = playground.getBoardMap();
        try {
            worker1 = new Worker("Player", map.getSquare(2,2));
            worker2 = new Worker("Player", map.getSquare(3,0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.setWorkers(Arrays.asList(worker1, worker2));
        game = new PhantomGame();
        card = new Hestia();
        stub = new Stub();
        controller = new HestiaController(card, map, player);
        controller.addGame(game);
        controller.addObserver(stub);
        controller.selectedWorker = worker2;
        playground.addObserver(stub);
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        game = null;
        card = null;
        player = null;
        worker1 = null;
        worker2 = null;
        stub = null;
        controller = null;
    }

    @Test
    public void buildTest_FirstBuild_StandardBuildDone() {
        controller.build(new Coordinates(4,0), Level.FIRST);

        try {
            assertEquals(Level.FIRST, map.getSquare(4,0).getLevel());
            assertNotNull(stub.event);
            assertTrue(stub.event instanceof MakeDecision);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buildTest_BuildSecond(){
        controller.build(new Coordinates(4,0), Level.FIRST);
        controller.decisionManager(true);
        controller.build(new Coordinates(3,1), Level.FIRST);

        try {
            assertEquals(Level.FIRST, map.getSquare(3,1).getLevel());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buildTest_GoOnWinningCondition_TurnEndGameAdvised() {
        worker1.setInWinningCondition(true);
        controller.manageBuildChoice(new Coordinates(2,1), Level.FIRST);

        assertEquals(2, stub.buffer.size());
        assertTrue(stub.buffer.get(0) instanceof Ack);
        assertNotNull(game.event);
        assertEquals(ControllerToGame.WINNER, game.event);
    }

    @Test
    public void buildTest_ImpossibleDoSecondBuild_TurnEnd(){
        try {
            map.getSquare(2,1).setLevel(Level.DOME);
            map.getSquare(3,1).setLevel(Level.DOME);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }

        controller.build(new Coordinates(4,0), Level.FIRST);

        assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void buildTest_InvalidCoordinates_ExceptionHandledRequestRedone(){
        controller.build(new Coordinates(5,4), Level.FIRST);

        assertEquals(2, stub.buffer.size());
        assertTrue(stub.buffer.get(0) instanceof UnsuccessfulOperation);
        assertTrue(stub.buffer.get(1) instanceof ChooseBuild);
    }

    @Test
    public void decisionManagerTest_DecidedToBuild_SentBuildChoice() {
        List<Pair<Coordinates, List<Level>>> expected = new ArrayList<>();
        controller.build(new Coordinates(3,1), Level.FIRST);
        stub.clearBuffer();
        expected.add(new Pair<>(new Coordinates(3,1), Collections.singletonList(Level.SECOND)));
        expected.add(new Pair<>(new Coordinates(2,1), Collections.singletonList(Level.FIRST)));

        controller.decisionManager(true);
        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseBuild);
        assertEquals(expected, ((ChooseBuild) stub.event).getValidChoices());
    }

    @Test
    public void decisionManagerTest_DecidedFalse_TurnsEnd(){
        controller.decisionManager(false);

        assertNotNull(game.event);
        assertEquals(ControllerToGame.END_TURN, game.event);
    }
}