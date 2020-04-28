package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that ask to the receiver player to make a binary decision on the argument carried in the {@code toDecide} string
 */
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
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitMakeDecision(this);
    }
}
