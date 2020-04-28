package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that ask to the player who is creating the match of how many player it must be composed
 */
public class NumberOfPlayer implements EventForClient{

    public NumberOfPlayer() {

    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
         return visitor.visitNumberOfPlayer(this);
    }

    @Override
    public String getReceiver() {
        return "NUMBER";
    }
}
