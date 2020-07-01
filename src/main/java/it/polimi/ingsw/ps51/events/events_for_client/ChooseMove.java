package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.Coordinates;

import java.util.List;

/**
 * Event that list the possible coordinates where the selected worker can be moved
 */
public class ChooseMove extends SpecificUserEvent implements EventForClient {

    private List<Coordinates> validChoices;

    public ChooseMove(List<Coordinates> validChoices, String receiver) {
        super(receiver);
        this.validChoices = validChoices;
    }

    public List<Coordinates> getValidChoices() {
        return validChoices;
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitChooseMove(this);
    }
}
