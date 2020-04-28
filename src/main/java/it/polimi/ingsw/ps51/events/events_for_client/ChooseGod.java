package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.gods.Gods;

import java.util.List;

/**
 * Event that carries the list of God's cards that cam be chosen by the player
 */
public class ChooseGod extends SpecificUserEvent implements EventForClient{

    private List<Gods> availableGods;

    public ChooseGod(String receiver, List<Gods> availableGods) {
        super(receiver);
        this.availableGods = availableGods;
    }

    public List<Gods> getAvailableGods() {
        return availableGods;
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitChooseGod(this);
    }
}
