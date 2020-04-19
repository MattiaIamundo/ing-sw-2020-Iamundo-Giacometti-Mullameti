package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.Map;

/**
 * Event that carries the updated map, that may be caused by a moved worker or due to a new level that as been built
 */
public class MapUpdate implements EventForClient{

    Map map;

    public MapUpdate(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitMapUpdate(this);
    }

    @Override
    public String getReceiver() {
        return "ALL";
    }
}
