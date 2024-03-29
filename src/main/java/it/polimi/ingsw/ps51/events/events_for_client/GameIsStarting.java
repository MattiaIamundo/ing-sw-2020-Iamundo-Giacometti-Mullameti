package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.WorkerColor;
import it.polimi.ingsw.ps51.model.gods.Gods;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The event signal to all the client that the setup phases of the game is ended and now the match will start,
 * the event also carry all the information needed to the clients to decorate the game board, for instance the
 * list of the gods chosen by each player and their color
 */
public class GameIsStarting implements EventForClient{

    private final List<Pair<String, Gods>> chosenGods;
    private final Map<String, WorkerColor> colorMap;

    public GameIsStarting(List<Pair<String, Gods>> chosenGods, Map<String, WorkerColor> colorMap) {
        this.chosenGods = chosenGods;
        this.colorMap = colorMap;
    }

    public List<Pair<String, Gods>> getChosenGods() {
        return chosenGods;
    }

    public Map<String, WorkerColor> getColorMap() {
        return colorMap;
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
