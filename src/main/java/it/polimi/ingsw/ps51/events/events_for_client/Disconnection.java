package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that inform all the remaining players that at least one player has disconnected from the match
 */
public class Disconnection implements EventForClient{


    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitDisconnection(this);
    }

    @Override
    public String getReceiver() {
        return "DISCONNECTION";
    }
}
