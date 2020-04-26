package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.visitor.VisitorForPong;

/**
 * Event that carries the decision taken by the player
 */
public class DecisionTaken implements EventForServer {

    private boolean decided;

    public DecisionTaken(boolean decided) {
        this.decided = decided;
    }

    public boolean isDecided() {
        return decided;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitDecisionTaken(this);
    }

    @Override
    public void acceptVisitor(VisitorForPong visitor) {
        visitor.visitDecisionTaken(this);
    }
}
