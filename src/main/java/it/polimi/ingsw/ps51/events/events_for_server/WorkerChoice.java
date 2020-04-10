package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.model.Worker;

/**
 * Event that carries the selected worker that will be moved and used to build a new level of a tower
 */
public class WorkerChoice implements EventForServer{
    private Worker chosedWorker;

    public WorkerChoice(Worker chosedWorker) {
        this.chosedWorker = chosedWorker;
    }

    public Worker getChosedWorker() {
        return chosedWorker;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitWorkerChoice(this);
    }
}
