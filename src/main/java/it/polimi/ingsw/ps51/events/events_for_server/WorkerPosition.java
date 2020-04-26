package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.visitor.VisitorForPong;

/**
 * Event that carries the coordinates where to collocates the worker for which the player received a
 * {@code ChooseWorkerPosition} event
 */
public class WorkerPosition implements EventForServer{

    private Coordinates coordinates;

    public WorkerPosition(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitWorkerPosition(this);
    }

    @Override
    public void acceptVisitor(VisitorForPong visitor) {
        visitor.visitWorkerPosition(this);
    }
}
