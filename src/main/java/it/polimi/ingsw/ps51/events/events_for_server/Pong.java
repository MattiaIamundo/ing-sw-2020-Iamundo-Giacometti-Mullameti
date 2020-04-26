package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.visitor.VisitorForPong;

public class Pong implements EventForServer {

    public void acceptVisitor(VisitorForPong visitor) {
        visitor.visitPong(this);
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {

    }
}
