package it.polimi.ingsw.ps51.events.events_for_client;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;

import java.util.List;

/**
 * Event that carries the updated map, that may be caused by a moved worker or due to a new level that as been built
 */
public class MapUpdate implements EventForClient{

    Map map;
    List<Worker> workers;

    public MapUpdate(Map map, List<Worker> workers) {
        this.map = map;
        this.workers = workers;
    }

    public Map getMap() {
        return map;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    @Override
    public String acceptVisitor(VisitorClient visitor) {
        return visitor.visitMapUpdate(this);
    }

    @Override
    public String getReceiver() {
        return "ALL";
    }
}
