package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.*;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.CommonAction;
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

public class NormalGodsControllerTest {

    private Map map;
    private PhantomGame game;
    private Card card;
    private Player player;
    private Worker worker1;
    private Worker worker2;
    private MessageReceiver receiver;
    private NormalGodsController controller;

    private class MessageReceiver implements Observer<EventForClient>{

        private EventForClient event;

        private List<EventForClient> buffer = new ArrayList<>();

        @Override
        public void update(EventForClient message) {
            event = message;
            buffer.add(message);
        }
    }

    private class SimpleGod extends CommonAction{}

    private class PhantomGame implements GameObserver{

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
            worker1 = new Worker("Player", map.getSquare(3,2));
            worker2 = new Worker("Player", map.getSquare(2,0));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        player.setWorkers(Arrays.asList(worker1, worker2));
        game = new PhantomGame();
        card = new SimpleGod();
        receiver = new MessageReceiver();
        controller = new NormalGodsController(card, map, player);
        controller.addObserver(receiver);
        controller.addGame(game);
        playground.addObserver(receiver);
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        card = null;
        game = null;
        player = null;
        worker1 = null;
        worker2 = null;
        receiver = null;
        controller = null;
    }

    @Test
    public void testStart_WorkerCanMove_ChooseWorkerSent() {
        controller.start();

        Assert.assertNotNull(receiver.event);
        Assert.assertTrue(receiver.event instanceof ChooseWorker);
        Assert.assertEquals(2, ((ChooseWorker) receiver.event).getValidChoices().size());
    }

    @Test
    public void testStart_WorkerCanNotMove_GameReceive() {
        try {
            map.getSquare(1,0).setLevel(Level.THIRD);
            map.getSquare(1,1).setLevel(Level.THIRD);
            map.getSquare(2,1).setLevel(Level.THIRD);
            map.getSquare(3,1).setLevel(Level.THIRD);
            map.getSquare(3,0).setLevel(Level.THIRD);
            map.getSquare(4,1).setLevel(Level.THIRD);
            map.getSquare(4,2).setLevel(Level.THIRD);
            map.getSquare(4,3).setLevel(Level.THIRD);
            map.getSquare(3,3).setLevel(Level.THIRD);
            map.getSquare(2,3).setLevel(Level.THIRD);
            map.getSquare(2,2).setLevel(Level.THIRD);

        } catch (OutOfMapException e) {
            e.printStackTrace();
        }


        controller.start();

        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.LOSER, game.event);
    }

    @Test
    public void testSearchForMoves_SelectedWorker1_SetWorker1AsChosenWorker() {
        controller.manageWorkerChoice(worker1);

        Assert.assertEquals(2, receiver.buffer.size());
        Assert.assertTrue(receiver.buffer.get(0) instanceof Ack);
        Assert.assertNotNull(receiver.event);
        Assert.assertTrue(receiver.event instanceof ChooseMove);
        Assert.assertEquals(card.checkMoves(player, worker1, map), ((ChooseMove) receiver.event).getValidChoices());
    }

    @Test
    public void testPerformMove_MoveToX3Y3_WorkerIsMovedSendChooseBuildEvent() {
        controller.selectedWorker = worker1;
        controller.manageMoveChoice(new Coordinates(3,3));

        Assert.assertEquals(new Coordinates(3,3), worker1.getPosition().getCoordinates());
        Assert.assertEquals(3, receiver.buffer.size());
        Assert.assertTrue(receiver.buffer.get(1) instanceof Ack);
        Assert.assertNotNull(receiver.event);
        Assert.assertTrue(receiver.event instanceof ChooseBuild);
        Assert.assertEquals(card.checkBuild(worker1, map), ((ChooseBuild) receiver.event).getValidChoices());
    }

    @Test
    public void testPerformMove_MoveToX3Y3Winning_WorkerIsMovedGameAdvisedOfWinning() {
        try {
            map.getSquare(3,3).setLevel(Level.THIRD);
            map.getSquare(3,2).setLevel(Level.SECOND);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        controller.selectedWorker = worker1;
        controller.manageMoveChoice(new Coordinates(3,3));

        Assert.assertEquals(new Coordinates(3,3), worker1.getPosition().getCoordinates());
        Assert.assertNotNull(receiver.event);
        Assert.assertTrue(receiver.event instanceof Ack);
        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.WINNER, game.event);
    }

    @Test
    public void preformMoveTest_OutOfMapCoordinates_AskedToRedoAction(){
        controller.selectedWorker = worker1;
        controller.manageMoveChoice(new Coordinates(4,5));

        Assert.assertEquals(2, receiver.buffer.size());
        Assert.assertTrue(receiver.buffer.get(0) instanceof UnsuccessfulOperation);
        Assert.assertTrue(receiver.buffer.get(1) instanceof ChooseMove);
    }

    @Test
    public void testBuild_BuildNotWinning_TurnEnds() {
        controller.selectedWorker = worker1;
        controller.manageBuildChoice(new Coordinates(3,3), Level.FIRST);

        Assert.assertEquals(2, receiver.buffer.size());
        Assert.assertTrue(receiver.buffer.get(1) instanceof Ack);
        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void testBuild_BuildWinning_GameAdvisedWinning() {
        controller.selectedWorker = worker1;
        worker1.setInWinningCondition(true);
        controller.manageBuildChoice(new Coordinates(3,3), Level.FIRST);

        Assert.assertNotNull(receiver.event);
        Assert.assertTrue(receiver.event instanceof Ack);
        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.WINNER, game.event);
    }

    @Test
    public void buildTest_OutOfMapCoordinates_AskedToRedoAction(){
        controller.selectedWorker = worker1;
        controller.manageBuildChoice(new Coordinates(6,7), Level.FIRST);

        Assert.assertEquals(2, receiver.buffer.size());
        Assert.assertTrue(receiver.buffer.get(0) instanceof UnsuccessfulOperation);
        Assert.assertTrue(receiver.buffer.get(1) instanceof ChooseBuild);
    }

    @Test
    public void testIsWinner_OneWorkerWinner_ReturnTrue() {
        worker1.setInWinningCondition(true);

        Assert.assertTrue(controller.isWinner());
    }

    @Test
    public void testIsWinner_NoWinningWorker_ReturnFalse() {

        Assert.assertFalse(controller.isWinner());

    }
}