package it.polimi.ingsw.ps51.events.events_for_client;

public class Ping implements EventForClient{

    public Ping() {

    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {

    }

    @Override
    public String getReceiver() {
        return "PING";
    }
}
