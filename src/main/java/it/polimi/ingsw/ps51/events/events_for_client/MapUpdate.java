package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.Map;

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
}
