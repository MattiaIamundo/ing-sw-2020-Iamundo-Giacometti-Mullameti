package it.polimi.ingsw.ps51.events.events_for_client;

public class OutOfRoom implements EventForClient{

    public OutOfRoom() {

    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {

    }

    @Override
    public String getReceiver() {
        return "END";
    }

}
