package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.model.gods.Gods;

/**
 * Event that carries the God chosen by the player
 */
public class GodChoice implements EventForServer{
    private Gods god;

    public GodChoice(Gods god) {
        this.god = god;
    }

    public Gods getGod() {
        return god;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitGodChoice(this);
    }
}
