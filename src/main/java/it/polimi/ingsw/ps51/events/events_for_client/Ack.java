package it.polimi.ingsw.ps51.events.events_for_client;

public class Ack extends SpecificUserEvent implements EventForClient{

    private String operationConfirmed;

    public Ack(String receiver, String operationConfirmed) {
        super(receiver);
        this.operationConfirmed = operationConfirmed;
    }

    public String getOperationConfirmed() {
        return operationConfirmed;
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return null;
    }
}
