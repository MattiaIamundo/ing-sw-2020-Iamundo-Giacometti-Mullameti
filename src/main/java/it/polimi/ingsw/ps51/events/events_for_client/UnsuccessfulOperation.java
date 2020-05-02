package it.polimi.ingsw.ps51.events.events_for_client;

public class UnsuccessfulOperation extends SpecificUserEvent implements EventForClient{

    /**
     * @param receiver the nickname of the player toward whom the event must be sent
     */
    public UnsuccessfulOperation(String receiver) {
        super(receiver);
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitUnsuccessfulOperation(this);
    }

}
