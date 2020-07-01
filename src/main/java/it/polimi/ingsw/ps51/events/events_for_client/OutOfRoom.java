package it.polimi.ingsw.ps51.events.events_for_client;

public class OutOfRoom implements EventForClient{


    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitOutOfRoom(this);
    }

    @Override
    public String getReceiver() {
        return "END";
    }

}
