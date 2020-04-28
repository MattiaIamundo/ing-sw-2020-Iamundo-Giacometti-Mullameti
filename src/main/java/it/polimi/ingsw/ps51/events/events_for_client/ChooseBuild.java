package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import org.javatuples.Pair;

import java.util.List;

/**
 * Event that carries the list of the of the valid combinations of coordinates and level
 * where a new level of a tower can be built
 */
public class ChooseBuild extends SpecificUserEvent implements EventForClient{

    private List<Pair<Coordinates, List<Level>>> validChoices;

    public ChooseBuild(List<Pair<Coordinates, List<Level>>> validChoices, String receiver) {
        super(receiver);
        this.validChoices = validChoices;
    }

    public List<Pair<Coordinates, List<Level>>> getValidChoices() {
        return validChoices;
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitChooseBuild(this);
    }
}
