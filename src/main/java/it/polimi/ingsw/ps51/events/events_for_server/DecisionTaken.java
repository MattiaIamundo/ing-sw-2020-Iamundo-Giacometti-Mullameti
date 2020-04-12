package it.polimi.ingsw.ps51.events.events_for_server;

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
}
