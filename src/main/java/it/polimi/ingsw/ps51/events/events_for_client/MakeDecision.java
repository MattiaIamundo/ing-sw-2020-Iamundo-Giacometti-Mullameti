package it.polimi.ingsw.ps51.events.events_for_client;

public class MakeDecision extends SpecificUserEvent implements EventForClient {

    private String toDecide;

    public MakeDecision(String receiver, String toDecide) {
        super(receiver);
        this.toDecide = toDecide;
    }

    public String getToDecide() {
        return toDecide;
    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitMakeDecision(this);
    }
}
