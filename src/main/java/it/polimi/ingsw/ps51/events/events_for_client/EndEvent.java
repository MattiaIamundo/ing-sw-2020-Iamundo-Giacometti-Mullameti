package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * The event signal that the match reach it's end and then all the client must terminate their execution
 */
public class EndEvent implements EventForClient{

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitEndEvent(this);
    }

    @Override
    public String getReceiver() {
        return "END";
    }
}
