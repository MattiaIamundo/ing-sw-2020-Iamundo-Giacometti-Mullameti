package it.polimi.ingsw.ps51.events.events_for_client;

import java.io.Serializable;

/**
 * Generic event that can be received by the client
 */
public interface EventForClient extends Serializable {

    void acceptVisitor(VisitorClient visitor);

    String getReceiver();
}
