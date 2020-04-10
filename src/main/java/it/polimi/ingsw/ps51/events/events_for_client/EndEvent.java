package it.polimi.ingsw.ps51.events.events_for_client;

public class EndEvent implements EventForClient{

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitEndEvent(this);
    }
}
