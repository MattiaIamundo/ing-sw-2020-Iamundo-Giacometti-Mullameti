package it.polimi.ingsw.ps51.network.server;

import it.polimi.ingsw.ps51.controller.Game;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.Disconnection;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.RoomObserver;

import java.util.*;

/**
 * This class represents the container which has:
 * the controller and the model of one game,
 * and the references to the {@link ServerInterface}
 * which represent the clients
 * @author Luca Giacometti
 */
public class Room extends Observable<EventForServer> implements Runnable, RoomObserver {

    private boolean isFinish;
    private Game game;
    List<String> nicknames;
    Map<String,ServerInterface> mapOfNicknameAndServerInterface;

    /**
     * Constructor
     * @param nick the list of the nicknames of the player in this room
     * @param map the association between the nickname and the server interface which the client is using
     */
    public Room(List<String> nick, Map<String,ServerInterface> map) {
        this.isFinish = false;
        this.nicknames = new ArrayList<>();
        this.nicknames.addAll(nick);
        this.mapOfNicknameAndServerInterface = new HashMap<>();
        this.mapOfNicknameAndServerInterface.putAll(map);

    }

    /**
     * This methods admits to put the game controller inside this room
     * and to set it as an observer of the room
     * @param game the controller of the game
     */
    public void setGame(Game game) {
        this.game = game;
        addObserver(game);
    }

    /**
     * With this method the notifications are send to the client/s
     * accessing to the map which contains the references.
     * And if the game is finished, it is captured here
     * and the room turns down himself
     * @param message the event which has to be send to client/s
     */
    @Override
    public void update(EventForClient message) {

        if (message.getReceiver().equals("ALL") || message.getReceiver().equals("END")) {
            for (String s: this.nicknames) {
                mapOfNicknameAndServerInterface.get(s).sendEvent(message);
            }

            if (message.getReceiver().equals("END")) {
                this.isFinish = true;
            }
        }
        else
            mapOfNicknameAndServerInterface.get(message.getReceiver()).sendEvent(message);

    }

    /**
     * Here the game is started and the room is continuing sleeping
     * until the end of the game or a disconnection of a player.
     * After it all the {@link ServerInterface} are closed
     */
    @Override
    public void run() {
        game.startGame();

        while (!isFinish) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("The thread Room is interrupted but is continuing to run");
            }
        }

        for( String s : this.nicknames) {
            this.mapOfNicknameAndServerInterface.get(s).closeConnection();
        }
        System.out.println("The game room is shutting down...");
    }

    /**
     * This update creates a Disconnection event for each client still alive
     * and sets the condition to stop this thread
     * @param disconnection the event which contains the nickname of the player which is
     *                      disconnected by the game
     */
    @Override
    public void update(Disconnection disconnection) {
        for(String s : this.nicknames) {
            if (!s.equals(disconnection.getPlayerDisconnected()))
                mapOfNicknameAndServerInterface.get(s).sendEvent(new it.polimi.ingsw.ps51.events.events_for_client.Disconnection());
        }
        this.isFinish = true;
    }
}
