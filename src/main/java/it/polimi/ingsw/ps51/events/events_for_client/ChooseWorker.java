package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.Worker;

import java.util.List;

/**
 * Event that ask to the player to chose one of his worker to play the turn
 */
public class ChooseWorker extends SpecificUserEvent implements EventForClient{

    private List<Worker> validChoices;

    public ChooseWorker(List<Worker> validChoices, String receiver) {
        super(receiver);
        this.validChoices = validChoices;
    }

    public List<Worker> getValidChoices() {
        return validChoices;
    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitChooseWorker(this);
    }
}
