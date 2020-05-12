package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.*;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.Demeter;
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

public class DemeterControllerTest {
    private Map map;
    private PhantomGame game;
    private Card card;
    private Player player;
    private Worker worker1;
    private Worker worker2;
    private Stub stub;
    private DemeterController controller;

    private class Stub implements Observer<EventForClient> {

        private EventForClient event;

        private List<EventForClient> buffer = new ArrayList<>();

        @Override
        public void update(EventForClient message) {
            event = message;
            buffer.add(message);
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
        card = new Demeter();
        stub = new Stub();
        controller = new DemeterController(card, map, player);
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
    public void buildTest_OnlyOnce_TurnEndAfterOnebuild() {
        controller.manageBuildChoice(new Coordinates(2,1), Level.FIRST);

        Assert.assertEquals(3, stub.buffer.size());
        Assert.assertTrue(stub.buffer.get(0) instanceof Ack);
        Assert.assertNotNull(stub.event);
        Assert.assertTrue(stub.event instanceof MakeDecision);

        controller.decisionManager(false);
        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void buildTest_BuildDouble_TurnEndAfterTwobuild() {
        controller.manageBuildChoice(new Coordinates(2,1), Level.FIRST);

        Assert.assertEquals(3, stub.buffer.size());
        Assert.assertTrue(stub.buffer.get(0) instanceof Ack);
        Assert.assertNotNull(stub.event);
        Assert.assertTrue(stub.event instanceof MakeDecision);

        controller.decisionManager(true);
        List<Pair<Coordinates, List<Level>>> excepctedSecondBuild = card.checkBuild(worker1, map);
        excepctedSecondBuild.remove(new Pair<>(new Coordinates(2,1), Arrays.asList(Level.SECOND)));


        Assert.assertNotNull(stub.event);
        Assert.assertTrue(stub.event instanceof ChooseBuild);
        Assert.assertEquals(excepctedSecondBuild, ((ChooseBuild) stub.event).getValidChoices());

        stub.buffer = new ArrayList<>();
        controller.manageBuildChoice(new Coordinates(1,2), Level.FIRST);

        Assert.assertEquals(2, stub.buffer.size());
        Assert.assertTrue(stub.buffer.get(0) instanceof Ack);
        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.END_TURN, game.event);
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
    public void buildTest_SecondBuildNotPossible_TurnEndGameAdvised() {
        try {
            map.getSquare(1,1).setLevel(Level.DOME);
            map.getSquare(3,1).setLevel(Level.DOME);
            map.getSquare(3,2).setLevel(Level.DOME);
            map.getSquare(3,3).setLevel(Level.DOME);
            map.getSquare(2,3).setLevel(Level.DOME);
            map.getSquare(1,3).setLevel(Level.DOME);
            map.getSquare(1,2).setLevel(Level.DOME);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }

        controller.manageBuildChoice(new Coordinates(2,1), Level.FIRST);


        Assert.assertEquals(2, stub.buffer.size());
        Assert.assertTrue(stub.buffer.get(0) instanceof Ack);
        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void buildTest_InvalidCoordinates_ExceptionHandledRequestRedone(){
        controller.build(new Coordinates(5,4), Level.FIRST);

        Assert.assertEquals(2, stub.buffer.size());
        Assert.assertTrue(stub.buffer.get(0) instanceof UnsuccessfulOperation);
        Assert.assertTrue(stub.buffer.get(1) instanceof ChooseBuild);
    }

    @Test
    public void decisionManager_True_ChooseBuildEventSent() {
        controller.decisionManager(true);

        Assert.assertNotNull(stub.event);
        Assert.assertTrue(stub.event instanceof ChooseBuild);
    }

    @Test
    public void decisionManager_False_EndTurnGameAdvised() {
        controller.decisionManager(false);

        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.END_TURN, game.event);
    }
}