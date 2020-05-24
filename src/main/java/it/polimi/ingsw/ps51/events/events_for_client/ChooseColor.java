package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.WorkerColor;

import java.util.ArrayList;
import java.util.List;

/**
 * This event contains the color which the client can choose to display his workers coloring those
 * @author Luca Giacometti
 */
public class ChooseColor extends SpecificUserEvent implements EventForClient{

    private List<WorkerColor> availableColor;

    /**
     * Constructor
     * @param receiver the nickname of the client
     * @param availableColor the actual color available
     */
    public ChooseColor(String receiver, List<WorkerColor> availableColor) {
        super(receiver);
        this.availableColor = new ArrayList<>();
        this.availableColor = availableColor;
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitChooseColor(this);
    }

    /**
     * Getter of availableColor
     * @return the reference to the list of available color
     */
    public List<WorkerColor> getAvailableColor() {
        return this.availableColor;
    }

}
