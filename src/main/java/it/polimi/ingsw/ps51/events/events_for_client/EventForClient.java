package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Generic event that can be received by the client
 */
public interface EventForClient {

    void acceptVisitor(VisitorClient visitor);

    String getReceiver();
}
