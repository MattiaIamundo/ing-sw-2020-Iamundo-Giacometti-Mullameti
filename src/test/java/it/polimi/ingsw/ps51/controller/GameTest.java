package it.polimi.ingsw.ps51.controller;

import it.polimi.ingsw.ps51.events.events_for_client.ChooseGod;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseGodsDeck;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseWorkerPosition;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.events.events_for_server.GodChoice;
import it.polimi.ingsw.ps51.events.events_for_server.GodsDeck;
import it.polimi.ingsw.ps51.events.events_for_server.WorkerPosition;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.Playground;
import it.polimi.ingsw.ps51.model.gods.*;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.Observer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class GameTest {

    private Stub stub;
    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;

    private class Stub extends Observable<EventForServer> implements Observer<EventForClient>{
        private EventForClient event;

        @Override
        public void update(EventForClient message) {
            event = message;
        }
    }

    @Before
    public void setUp() throws Exception {
        stub = new Stub();
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        player3 = new Player("Player3");
        game = new Game(new Playground(Arrays.asList(player1, player2, player3)));
        game.addObserver(stub);
        stub.addObserver(game);
    }

    @After
    public void tearDown() throws Exception {
        stub = null;
        game = null;
        player1 = null;
        player2 = null;
        player3 = null;
    }

    @Test
    public void phaseOneTest(){
        game.startGame();

        assertNotNull(stub.event);
        assertTrue(stub.event instanceof ChooseGodsDeck);
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

    private void waitForEventBeApplied(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}