package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseBuild;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseMove;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseWorker;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
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

import java.util.Arrays;

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

        @Override
        public void update(EventForClient message) {
            event = message;
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
        map = new Map();
        game = new PhantomGame();
        card = new SimpleGod();
        receiver = new MessageReceiver();
        player = new Player("Player");
        worker1 = new Worker("Player");
        worker2 = new Worker("Player");
        player.setWorkers(Arrays.asList(worker1, worker2));
        controller = new NormalGodsController(card, map, player);
        controller.addObserver(receiver);
        controller.addGame(game);
        try {
            worker1.setPosition(map.getSquare(3,2));
            worker2.setPosition(map.getSquare(2,0));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
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

        Assert.assertNotNull(receiver.event);
        Assert.assertTrue(receiver.event instanceof ChooseMove);
        Assert.assertEquals(card.checkMoves(player, worker1, map), ((ChooseMove) receiver.event).getValidChoices());
    }

    @Test
    public void testEffectuateMove_MoveToX3Y3_WorkerIsMovedSendChooseBuildEvent() {
        controller.selectedWorker = worker1;
        controller.manageMoveChoice(new Coordinates(3,3));

        Assert.assertEquals(new Coordinates(3,3), worker1.getPosition().getCoordinates());
        Assert.assertNotNull(receiver.event);
        Assert.assertTrue(receiver.event instanceof ChooseBuild);
        Assert.assertEquals(card.checkBuild(worker1, map), ((ChooseBuild) receiver.event).getValidChoices());
    }

    @Test
    public void testEffectuateMove_MoveToX3Y3Winning_WorkerIsMovedGameAdvisedOfWinning() {
        try {
            map.getSquare(3,3).setLevel(Level.THIRD);
            map.getSquare(3,2).setLevel(Level.SECOND);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        controller.selectedWorker = worker1;
        controller.manageMoveChoice(new Coordinates(3,3));

        Assert.assertEquals(new Coordinates(3,3), worker1.getPosition().getCoordinates());
        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.WINNER, game.event);
    }

    @Test
    public void testBuild_BuildNotWinning_TurnEnds() {
        controller.selectedWorker = worker1;
        controller.manageBuildChoice(new Coordinates(3,3), Level.FIRST);

        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.END_TURN, game.event);
    }

    @Test
    public void testBuild_BuildWinning_GameAdvisedWinning() {
        controller.selectedWorker = worker1;
        worker1.setInWinningCondition(true);
        controller.manageBuildChoice(new Coordinates(3,3), Level.FIRST);

        Assert.assertNotNull(game.event);
        Assert.assertEquals(ControllerToGame.WINNER, game.event);
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