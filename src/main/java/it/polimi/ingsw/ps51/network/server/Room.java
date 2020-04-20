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
 * the controller and the model of the game,
 * and the references to the server interfaces
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
     * With this method the notification are send to the client/s
     * accessing to the map which contains the links.
     * And if the game is finished, it is captured here
     * @param message the event which have to be updated
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
     * until the end of the game or a disconnection of a player
     */
    @Override
    public void run() {
        game.startGame();

        while (!isFinish) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for( String s : this.nicknames) {
            this.mapOfNicknameAndServerInterface.get(s).closeConnection();
        }
    }

    /**
     * This update create a Disconnection event for each client
     * and set the condition to finish the game
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
