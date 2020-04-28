package it.polimi.ingsw.ps51.controller;

import it.polimi.ingsw.ps51.controller.gods.DemeterController;
import it.polimi.ingsw.ps51.controller.gods.GodController;
import it.polimi.ingsw.ps51.controller.gods.NormalGodsController;
import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseBuild;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseMove;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.Build;
import it.polimi.ingsw.ps51.events.events_for_server.DecisionTaken;
import it.polimi.ingsw.ps51.events.events_for_server.MoveChoice;
import it.polimi.ingsw.ps51.events.events_for_server.WorkerChoice;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Apollo;
import it.polimi.ingsw.ps51.model.gods.Demeter;
import it.polimi.ingsw.ps51.utility.Observer;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class VisitorControllerTest {
    Playground playground;
    Player player;
    Worker worker;
    Worker worker1;
    GodController controller;
    GameForTest game;
    VisitorController visitor;
    Stub stub;


    private class Stub implements Observer<EventForClient>{

        private EventForClient event;

        @Override
        public void update(EventForClient message) {
            event = message;
        }
    }

    private class GameForTest extends Game{

        /**
         * This is the constructor of the class
         *
         * @param gameRoom it's the {@code Playground} that gives to the controller the access to the model
         */
        public GameForTest(Playground gameRoom) {
            super(gameRoom);
        }

        @Override
        public GodController getActualController() {
            return controller;
        }

        @Override
        public void update(ControllerToGame message) {

        }
    }

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        playground = new Playground(Collections.singletonList(player));
        Map map = playground.getBoardMap();
        try {
            worker = new Worker("Player1", map.getSquare(2,2));
            worker1 = new Worker("Player1", map.getSquare(3,1));
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        player.setWorkers(Arrays.asList(worker, worker1));
        game = new GameForTest(playground);
        visitor = new VisitorController(game);
        controller = new DemeterController(new Demeter(), map, player);
        stub = new Stub();
        ((DemeterController) controller).addObserver(stub);
    }

    @After
    public void tearDown() throws Exception {
        player = null;
        worker = null;
        worker1 = null;
        playground = null;
        game = null;
        visitor = null;
    }

    @Test
    public void visitMoveChoiceTest_MoveTox2y3_WorkerMoved() {
        controller.manageWorkerChoice(worker);
        visitor.visitMoveChoice(new MoveChoice(new Coordinates(2,3)));

        try {
            assertEquals(playground.getBoardMap().getSquare(2,3), worker.getPosition());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void visitBuildTest_BuildFirstOn2y3_BuildSuccess() {
        controller.manageWorkerChoice(worker);
        visitor.visitBuild(new Build(new Pair<>(new Coordinates(2,3), Level.FIRST)));

        try {
            assertEquals(Level.FIRST, playground.getBoardMap().getSquare(2,3).getLevel());
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void visitWorkerChoiceTest() {
        visitor.visitWorkerChoice(new WorkerChoice(worker));

        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseMove);
    }


    @Test
    public void visitDecisionTaken() {
        controller.manageWorkerChoice(worker);
        controller.manageBuildChoice(new Coordinates(1,2), Level.FIRST);
        visitor.visitDecisionTaken(new DecisionTaken(true));

        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseBuild);
    }
}