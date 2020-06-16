package it.polimi.ingsw.ps51.events.events_for_client;

import java.util.List;

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
