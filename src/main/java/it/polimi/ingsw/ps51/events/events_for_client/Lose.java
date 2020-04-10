package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that inform the player that he lost the match
 */
public class Lose extends SpecificUserEvent implements EventForClient{

    public Lose(String receiver) {
        super(receiver);
    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitLose(this);
    }
}
