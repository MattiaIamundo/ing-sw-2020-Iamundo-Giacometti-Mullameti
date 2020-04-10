package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that inform the player that he won the match
 */
public class Win extends SpecificUserEvent implements EventForClient{

    public Win(String receiver) {
        super(receiver);
    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitWin(this);
    }
}
