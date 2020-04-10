package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import org.javatuples.Pair;

/**
 * Event that carries the choice of where and which level to build
 */
public class Build implements EventForServer{

    private Pair<Coordinates, Level> buildOn;

    public Build(Pair<Coordinates, Level> buildOn) {
        this.buildOn = buildOn;
    }

    public Pair<Coordinates, Level> getBuildOn() {
        return buildOn;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitBuild(this);
    }
}
