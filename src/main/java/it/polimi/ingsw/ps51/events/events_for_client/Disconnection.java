package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that inform all the remaining players that at least one player has disconnected from the match
 */
public class Disconnection implements EventForClient{

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitDisconnection(this);
    }
}
