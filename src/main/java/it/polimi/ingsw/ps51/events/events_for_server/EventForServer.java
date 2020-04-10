package it.polimi.ingsw.ps51.events.events_for_server;

/**
 * Generic event that can be received by the server
 */
public interface EventForServer {

    void acceptVisitor(VisitorServer visitor);
}
