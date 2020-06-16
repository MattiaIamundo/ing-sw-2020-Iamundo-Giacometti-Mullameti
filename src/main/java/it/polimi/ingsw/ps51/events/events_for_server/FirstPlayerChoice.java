package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.visitor.VisitorForPong;

public class FirstPlayerChoice implements EventForServer{
    private String firstPlayer;

    public FirstPlayerChoice(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitFirstPlayerChoice(this);
    }

    @Override
    public void acceptVisitor(VisitorForPong visitor) {
        visitor.visitFirstPlayerChoice(this);
    }
}
