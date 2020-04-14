package it.polimi.ingsw.ps51.network.server;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.view.Supporter;

/**
 * This interface is the representation of the server connection between {@link Room} and {@link Supporter}
 * @author Luca Giacometti
 */
public interface ServerInterface {

    /**
     * The events for the client are send towards this method
     * @param eventForClient a generic event for the client
     */
    void sendEvent(EventForClient eventForClient);

    /**
     * This method admits to the implementation to have
     * a link with the Room where the client, connected towards this,
     * is playing the game
     * @param room the room where the client is playing the game
     */
    void pairWithRoom(Room room);

    /**
     * To close the connection with the client
     */
    void closeConnection();
}
