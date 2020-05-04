package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.gods.Gods;
import org.javatuples.Pair;

import java.util.List;

public class GameIsStarting implements EventForClient{

    private final List<Pair<String, Gods>> chosenGods;

    public GameIsStarting(List<Pair<String, Gods>> chosenGods) {
        this.chosenGods = chosenGods;
    }

    public List<Pair<String, Gods>> getChosenGods() {
        return chosenGods;
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitGameIsStarting(this);
    }

    @Override
    public String getReceiver() {
        return "ALL";
    }
}
