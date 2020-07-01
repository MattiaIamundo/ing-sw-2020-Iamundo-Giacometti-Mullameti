package it.polimi.ingsw.ps51.events.events_for_client;

import java.util.List;

/**
 * The event carries the list of all the players to permit to the challenger to choose who will be the first player to move
 */
public class ChooseFirstPlayer extends SpecificUserEvent implements EventForClient{
    private List<String> players;

    public ChooseFirstPlayer(String receiver, List<String> players) {
        super(receiver);
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitChooseFirstPlayer(this);
    }
}
