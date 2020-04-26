package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.visitor.VisitorForPong;

/**
 * Generic event that can be received by the server
 */
public interface EventForServer {

    void acceptVisitor(VisitorServer visitor);

    void acceptVisitor(VisitorForPong visitor);
}
