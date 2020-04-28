package it.polimi.ingsw.ps51.events.events_for_client;

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
