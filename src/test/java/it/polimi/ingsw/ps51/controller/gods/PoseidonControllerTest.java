package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.*;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.Demeter;
import it.polimi.ingsw.ps51.model.gods.Poseidon;
import it.polimi.ingsw.ps51.model.gods.Prometheus;
import it.polimi.ingsw.ps51.utility.GameObserver;
import it.polimi.ingsw.ps51.utility.Observer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PoseidonControllerTest {

    private Map map;
    private PhantomGame game;
    private Card card;
    private Player player;
    private Worker worker1;
    private Worker worker2;
    private Stub stub;
    private PoseidonController controller;

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
        card = new Poseidon();
        stub = new Stub();
        controller = new PoseidonController(card, map, player);
        controller.addGame(game);
        controller.addObserver(stub);
        controller.selectedWorker = worker1;
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
    public void buildTest_NoUsePower_TurnEnd() {
        controller.build(new Coordinates(2,3), Level.FIRST);
        controller.decisionManager(false);

        assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void buildTest_UsePowerBuildOnce_OneExtraBuildThenTurnEnd() {
        controller.build(new Coordinates(2,3), Level.FIRST);
        assertTrue(stub.event instanceof MakeDecision);
        controller.decisionManager(true);

        assertTrue(stub.event instanceof ChooseBuild);
        controller.build(new Coordinates(3,1), Level.FIRST);
        assertTrue(stub.event instanceof MakeDecision);

        controller.decisionManager(false);
        assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void buildTest_UsePowerBuildTwice_TwoExtraBuildThenTurnEnd() {
        controller.build(new Coordinates(2,3), Level.FIRST);
        stub.clearBuffer();
        controller.decisionManager(true);

        assertTrue(stub.event instanceof ChooseBuild);
        controller.build(new Coordinates(3,1), Level.FIRST);
        assertTrue(stub.event instanceof MakeDecision);
        controller.decisionManager(true);

        assertTrue(stub.event instanceof ChooseBuild);
        controller.build(new Coordinates(2,1), Level.FIRST);
        assertTrue(stub.event instanceof MakeDecision);

        controller.decisionManager(false);
        assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void buildTest_UsePowerBuildThreeTimes_ThreeExtraBuildThenTurnEndAutomatically() {
        controller.build(new Coordinates(2,3), Level.FIRST);
        stub.clearBuffer();
        controller.decisionManager(true);

        assertTrue(stub.event instanceof ChooseBuild);
        controller.build(new Coordinates(3,1), Level.FIRST);
        assertTrue(stub.event instanceof MakeDecision);
        controller.decisionManager(true);

        assertTrue(stub.event instanceof ChooseBuild);
        controller.build(new Coordinates(2,1), Level.FIRST);
        assertTrue(stub.event instanceof MakeDecision);

        controller.decisionManager(true);

        assertTrue(stub.event instanceof ChooseBuild);
        controller.build(new Coordinates(2,1), Level.SECOND);
        assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void buildTest_InvalidCoordinates_ExceptionHandledRequestRedone(){
        controller.build(new Coordinates(5,4), Level.FIRST);

        Assert.assertEquals(2, stub.buffer.size());
        Assert.assertTrue(stub.buffer.get(0) instanceof UnsuccessfulOperation);
        Assert.assertTrue(stub.buffer.get(1) instanceof ChooseBuild);
    }

    @Test
    public void buildTest_GoOnWinningCondition_TurnEndGameAdvised() {
        worker1.setInWinningCondition(true);
        controller.manageBuildChoice(new Coordinates(2,1), Level.FIRST);

        Assert.assertEquals(2, stub.buffer.size());
        Assert.assertTrue(stub.buffer.get(0) instanceof Ack);
        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.WINNER, game.event);
    }

    @Test
    public void buildTest_ImpossibleToUsePower_TurnEnd(){
        try {
            map.getSquare(3,0).setLevel(Level.SECOND);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }

        controller.build(new Coordinates(2,3), Level.FIRST);
        assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void decisionManagerTest_DecidedToUsePower_AskedWhereToBuild() {
        controller.decisionManager(true);

        assertTrue(stub.event instanceof ChooseBuild);
    }

    @Test
    public void decisionManagerTest_DecidedNotUsePower_TurnEnd() {
        controller.decisionManager(false);

        assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void decisionManagerTest_DecidedDoAnotherBuild_AskedWhereToBuild() {
        controller.decisionManager(true);
        controller.decisionManager(true);

        assertTrue(stub.event instanceof ChooseBuild);
    }
}