package it.polimi.ingsw.ps51.controller;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.*;
import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.*;
import it.polimi.ingsw.ps51.utility.GodControllerObservable;
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

public class GameTest {

    Stub stub;
    Game game;
    Playground playground;
    PhantomController controller;
    Player player1;
    Player player2;
    Player player3;

    private class Stub extends Observable<EventForServer> implements Observer<EventForClient>{
        private EventForClient event;

        private List<EventForClient> buffer = new ArrayList<>();

        void clearBuffer(){
            buffer = new ArrayList<>();
        }

        @Override
        public void update(EventForClient message) {
            event = message;
            buffer.add(message);
        }
    }

    private class PhantomController extends GodControllerObservable{

    }

    @Before
    public void setUp() throws Exception {
        stub = new Stub();
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        player3 = new Player("Player3");
        playground = new Playground(Arrays.asList(player1, player2, player3));
        game = new Game(playground);
        game.addObserver(stub);
        stub.addObserver(game);
        controller = new PhantomController();
        controller.addGame(game);
    }

    @After
    public void tearDown() throws Exception {
        stub = null;
        game = null;
        controller = null;
        playground = null;
        player1 = null;
        player2 = null;
        player3 = null;
    }

    @Test
    public void phaseOneTest(){
        game.startGame();

        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseGodsDeck);
        assertEquals(3, ((ChooseGodsDeck) stub.event).getGodsNum());
    }

    @Test
    public void phaseTwo(){
        stub.notify(new GodsDeck(Arrays.asList(Gods.APOLLO, Gods.ARTEMIS, Gods.MINOTAUR)));

        assertTrue(stub.event instanceof ChooseGod);
        assertEquals(Arrays.asList(Gods.APOLLO, Gods.ARTEMIS, Gods.MINOTAUR), ((ChooseGod) stub.event).getAvailableGods());

        stub.notify(new GodChoice(Gods.APOLLO));
        assertNotNull(player1.getGod());
        assertTrue(player1.getGod() instanceof Apollo);
        assertTrue(stub.event instanceof ChooseGod);
        assertEquals(Arrays.asList(Gods.ARTEMIS, Gods.MINOTAUR), ((ChooseGod) stub.event).getAvailableGods());

        stub.notify(new GodChoice(Gods.MINOTAUR));
        assertNotNull(player2.getGod());
        assertTrue(player2.getGod() instanceof Minotaur);
        assertTrue(stub.event instanceof ChooseGod);
        assertEquals(Collections.singletonList(Gods.ARTEMIS), ((ChooseGod) stub.event).getAvailableGods());

        stub.notify(new GodChoice(Gods.ARTEMIS));
        assertNotNull(player3.getGod());
        assertTrue(player3.getGod() instanceof Artemis);
        stub.event = null;
        while (stub.event == null){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(stub.event instanceof ChooseWorkerPosition);
    }

    @Test
    public void phaseThree(){
        phaseTwo();

        assertTrue(stub.event instanceof ChooseWorkerPosition);
        assertEquals(1, ((ChooseWorkerPosition) stub.event).getWorkerNum());
        assertEquals(player1.getNickname(), ((ChooseWorkerPosition) stub.event).getReceiver());
        stub.notify(new WorkerPosition(new Coordinates(2,2)));
        waitForEventBeApplied();
        assertEquals(new Coordinates(2,2), player1.getWorkers().get(0).getPosition().getCoordinates());

        assertTrue(stub.event instanceof ChooseWorkerPosition);
        assertEquals(2, ((ChooseWorkerPosition) stub.event).getWorkerNum());
        assertEquals(player1.getNickname(), ((ChooseWorkerPosition) stub.event).getReceiver());
        stub.notify(new WorkerPosition(new Coordinates(1,2)));
        waitForEventBeApplied();
        assertEquals(new Coordinates(1,2), player1.getWorkers().get(1).getPosition().getCoordinates());

        assertTrue(stub.event instanceof ChooseWorkerPosition);
        assertEquals(1, ((ChooseWorkerPosition) stub.event).getWorkerNum());
        assertEquals(player2.getNickname(), ((ChooseWorkerPosition) stub.event).getReceiver());
        stub.notify(new WorkerPosition(new Coordinates(3,2)));
        waitForEventBeApplied();
        assertEquals(new Coordinates(3,2), player2.getWorkers().get(0).getPosition().getCoordinates());

        assertTrue(stub.event instanceof ChooseWorkerPosition);
        assertEquals(2, ((ChooseWorkerPosition) stub.event).getWorkerNum());
        assertEquals(player2.getNickname(), ((ChooseWorkerPosition) stub.event).getReceiver());
        stub.notify(new WorkerPosition(new Coordinates(4,3)));
        waitForEventBeApplied();
        assertEquals(new Coordinates(4,3), player2.getWorkers().get(1).getPosition().getCoordinates());

        assertTrue(stub.event instanceof ChooseWorkerPosition);
        assertEquals(1, ((ChooseWorkerPosition) stub.event).getWorkerNum());
        assertEquals(player3.getNickname(), ((ChooseWorkerPosition) stub.event).getReceiver());
        stub.notify(new WorkerPosition(new Coordinates(3,3)));
        waitForEventBeApplied();
        assertEquals(new Coordinates(3,3), player3.getWorkers().get(0).getPosition().getCoordinates());

        assertTrue(stub.event instanceof ChooseWorkerPosition);
        assertEquals(2, ((ChooseWorkerPosition) stub.event).getWorkerNum());
        assertEquals(player3.getNickname(), ((ChooseWorkerPosition) stub.event).getReceiver());
        stub.notify(new WorkerPosition(new Coordinates(2,2)));
        waitForEventBeApplied();
        assertEquals(1, player3.getWorkers().size());

        assertTrue(stub.event instanceof ChooseWorkerPosition);
        assertEquals(2, ((ChooseWorkerPosition) stub.event).getWorkerNum());
        assertEquals(player3.getNickname(), ((ChooseWorkerPosition) stub.event).getReceiver());
        stub.notify(new WorkerPosition(new Coordinates(0,0)));
        waitForEventBeApplied();
        assertEquals(new Coordinates(0,0), player3.getWorkers().get(1).getPosition().getCoordinates());
    }

    @Test
    public void updateTest_LoserEvent3Player_PlayerRemovedGameContinues(){
        phaseThree();
        stub.clearBuffer();
        controller.notifyToGame(ControllerToGame.LOSER);

        assertEquals(2, stub.buffer.size());
        assertTrue(stub.buffer.get(0) instanceof Lose);
        assertTrue(stub.buffer.get(1) instanceof ChooseWorker);
        assertEquals("Player1", stub.buffer.get(0).getReceiver());
        assertEquals("Player2", stub.buffer.get(1).getReceiver());
        assertEquals(2, playground.getPlayers().size());
    }

    @Test
    public void updateTest_LoserEvent2Player_GameEnds(){
        phaseThree();
        playground.removePlayer(player2);
        stub.clearBuffer();
        controller.notifyToGame(ControllerToGame.LOSER);

        assertEquals(2, stub.buffer.size());
        assertTrue(stub.buffer.get(0) instanceof Lose);
        assertTrue(stub.buffer.get(1) instanceof Win);
        assertEquals("Player1", stub.buffer.get(0).getReceiver());
        assertEquals("Player3", stub.buffer.get(1).getReceiver());
    }

    @Test
    public void updateTest_EndTurnEvent_GoToNextTurn(){
        phaseThree();
        controller.notifyToGame(ControllerToGame.END_TURN);

        assertEquals(player2, playground.getActualPlayer());
        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseWorker);
    }

    @Test
    public void updateTest_WinnerEvent_GameEnd(){
        phaseThree();
        stub.clearBuffer();
        controller.notifyToGame(ControllerToGame.WINNER);

        assertEquals(3, stub.buffer.size());
        assertTrue(stub.buffer.get(0) instanceof Win);
        assertEquals("Player1", stub.buffer.get(0).getReceiver());
        assertTrue(stub.buffer.get(1) instanceof Lose);
        assertEquals("Player2", stub.buffer.get(1).getReceiver());
        assertTrue(stub.buffer.get(2) instanceof Lose);
        assertEquals("Player3", stub.buffer.get(2).getReceiver());
    }

    private void waitForEventBeApplied(){
        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}