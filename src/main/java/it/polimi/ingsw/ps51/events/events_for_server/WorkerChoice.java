package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.model.Worker;
import it.polimi.ingsw.ps51.visitor.VisitorForPong;

/**
 * Event that carries the selected worker that will be moved and used to build a new level of a tower
 */
public class WorkerChoice implements EventForServer{
    private final Worker chosenWorker;

    public WorkerChoice(Worker chosenWorker) {
        this.chosenWorker = chosenWorker;
    }

    public Worker getChosenWorker() {
        return chosenWorker;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitWorkerChoice(this);
    }

    @Override
    public void acceptVisitor(VisitorForPong visitor) {
        visitor.visitWorkerChoice(this);
    }
}
