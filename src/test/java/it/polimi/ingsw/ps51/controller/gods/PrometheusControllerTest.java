package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseMove;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.MakeDecision;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.Prometheus;
import it.polimi.ingsw.ps51.utility.GameObserver;
import it.polimi.ingsw.ps51.utility.Observer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PrometheusControllerTest {

    Map map;
    Card card;
    Player player;
    Worker worker;
    PrometheusController controller;
    PhantomGame game;
    MessageReceiver receiver;


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

    private class MessageReceiver implements Observer<EventForClient> {

        private EventForClient event;

        @Override
        public void update(EventForClient message) {
            event = message;
        }
    }

    @Before
    public void setUp() throws Exception {
        map = new Map();
        player = new Player("Player");
        worker = new Worker("Player");
        player.setWorkers(Arrays.asList(worker));
        card = new Prometheus();
        receiver = new MessageReceiver();
        game = new PhantomGame();
        controller = new PrometheusController(card, map, player);
        controller.addGame(game);
        controller.addObserver(receiver);

        try {
            worker.setPosition(map.getSquare(0,0));
            map.getSquare(0,1).setLevel(Level.FIRST);
            map.getSquare(1,0).setLevel(Level.FIRST);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        card = null;
        player = null;
        worker = null;
        receiver = null;
        game = null;
        controller = null;
    }

    @Test
    public void searchForMoves_WithoutUsingPowerGods_ThreePossibleSquareWhereToMove() {
        List<Coordinates> expected = new ArrayList<>();
        controller.selectedWorker = worker;
        controller.decisionManager(false);

        expected.add(new Coordinates(1,0));
        expected.add(new Coordinates(1,1));
        expected.add(new Coordinates(0,1));

        assertNotNull(receiver.event);
        assertTrue(receiver.event instanceof ChooseMove);
        assertEquals(expected, ((ChooseMove) receiver.event).getValidChoices());
    }

    @Test
    public void searchForMoves_UsingPowerGods_OnePossibleSquareWhereToMove() {
        List<Coordinates> expected = new ArrayList<>();
        controller.selectedWorker = worker;
        controller.decisionManager(true);
        controller.searchForMoves();

        expected.add(new Coordinates(1,1));

        assertNotNull(receiver.event);
        assertTrue(receiver.event instanceof ChooseMove);
        assertEquals(expected, ((ChooseMove) receiver.event).getValidChoices());
    }

    @Test
    public void preBuild_WorkerInWinningCondition_TurnEndedGameAdvised() {
        worker.setInWinningCondition(true);
        controller.selectedWorker = worker;
        controller.preBuild(new Coordinates(1,0), Level.FIRST);

        assertNotNull(game.event);
        assertEquals(ControllerToGame.WINNER, game.event);
    }

    @Test
    public void preBuild_BuildInX1Y0_GoOnWithMoveAction() {
        List<Coordinates> expected = new ArrayList<>();
        controller.selectedWorker = worker;
        controller.decisionManager(true);
        controller.preBuild(new Coordinates(1,0), Level.FIRST);

        expected.add(new Coordinates(1,1));

        assertNotNull(receiver.event);
        assertTrue(receiver.event instanceof ChooseMove);
        assertEquals(expected, ((ChooseMove) receiver.event).getValidChoices());
    }

    @Test
    public void manageWorkerChoice_IsPossibleToUseGodPower_PlayerAskedToDecideIfUseGodPower() {
        controller.manageWorkerChoice(worker);

        assertEquals(worker, controller.selectedWorker);
        assertNotNull(receiver.event);
        assertTrue(receiver.event instanceof MakeDecision);
    }

    @Test
    public void manageWorkerChoice_NotPossibleToUseGodPower_TurnGoAheadAsUsual() {
        List<Coordinates> expected = new ArrayList<>();
        try {
            map.getSquare(1,1).setLevel(Level.FIRST);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        controller.manageWorkerChoice(worker);

        expected.add(new Coordinates(1,0));
        expected.add(new Coordinates(1,1));
        expected.add(new Coordinates(0,1));

        assertEquals(worker, controller.selectedWorker);
        assertNotNull(receiver.event);
        assertTrue(receiver.event instanceof ChooseMove);
        assertEquals(expected, ((ChooseMove) receiver.event).getValidChoices());
    }

    @Test
    public void manageBuildChoice_UseGodPower_GoOnWithMoveAction() {
        controller.selectedWorker = worker;
        controller.decisionManager(true);
        controller.manageBuildChoice(new Coordinates(1,1), Level.FIRST);

        assertNotNull(receiver.event);
        assertTrue(receiver.event instanceof ChooseMove);
    }

    @Test
    public void manageBuildChoice_DoNotUseGodPower_EndTurn() {
        controller.selectedWorker = worker;
        controller.decisionManager(false);
        controller.manageBuildChoice(new Coordinates(1,1), Level.FIRST);

        assertNotNull(game.event);
        assertEquals(ControllerToGame.END_TURN, game.event);
    }
}