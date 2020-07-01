package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * The event signal to the specified player that it's turn is ended
 */
public class TurnIsEnd extends SpecificUserEvent implements EventForClient{

    /**
     * @param receiver the nickname of the player toward whom the event must be sent
     */
    public TurnIsEnd(String receiver) {
        super(receiver);
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitTurnIsEnd(this);
    }
}
