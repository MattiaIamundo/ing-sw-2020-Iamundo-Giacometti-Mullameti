package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that ask to the player who is creating the match of how many player it must be composed
 */
public class NumberOfPlayer extends SpecificUserEvent implements EventForClient{

    public NumberOfPlayer(String receiver) {
        super(receiver);
    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitNumberOfPlayer(this);
    }
}
