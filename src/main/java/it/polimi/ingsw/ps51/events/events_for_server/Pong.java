package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.visitor.VisitorFirstPhase;
import it.polimi.ingsw.ps51.visitor.VisitorForPong;

public class Pong implements EventForFirstPhase {

    public void acceptVisitor(VisitorForPong visitor) {
        visitor.visitPong(this);
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {

    }

    @Override
    public void acceptVisitor(VisitorFirstPhase visitor) {
        visitor.visitPong(this);
    }
}
