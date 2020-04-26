package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.visitor.VisitorForPong;

/**
 * Event that carries the choice of the user about where to move the previously selected worker
 */
public class MoveChoice implements EventForServer{

    private Coordinates moveTo;

    public MoveChoice(Coordinates moveTo) {
        this.moveTo = moveTo;
    }

    public Coordinates getMoveTo() {
        return moveTo;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitMoveChoice(this);
    }

    @Override
    public void acceptVisitor(VisitorForPong visitor) {
        visitor.visitMoveChoice(this);
    }
}
