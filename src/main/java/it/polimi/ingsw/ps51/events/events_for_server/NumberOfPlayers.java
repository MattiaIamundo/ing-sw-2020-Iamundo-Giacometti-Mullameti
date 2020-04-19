package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.visitor.VisitorSocketConnectionServer;

/**
 * Event that carries the number of player of the game, this is chose by the user who creates the match
 */
public class NumberOfPlayers implements EventForFirstPhase{

    Integer playerNumber;

    public NumberOfPlayers(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {

    }

    @Override
    public void acceptVisitor(VisitorSocketConnectionServer visitor) {
        visitor.visitNumberOfPlayer(this);
    }
}
