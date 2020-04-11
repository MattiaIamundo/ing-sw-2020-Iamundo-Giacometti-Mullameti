package it.polimi.ingsw.ps51.network.client;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.view.Supporter;

/**
 * This interface is the representation of the connection between {@link Client} and server
 * @author Luca Giacometti
 */
public interface ClientInterface {

    /**
     * The event for the server are send towards this method
     * @param eventForServer the event to send to the server
     * @return true if the event was send
     *          false if not
     */
    boolean sendEvent(EventForServer eventForServer);

    /**
     * The events for the client are received due to this method.
     * It has to block the thread {@link Handler} to receive an
     * event which is coming form the server
     * @return an event for the {@link Supporter}
     */
    EventForClient receiveEvent();

    /**
     * The call to this method generate the closure to the connection
     */
    void closeConnection();
}
