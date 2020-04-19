package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that ask to the player where place the worker, the {@code workerNum} identifies the worker that must be
 * collocated on the map, it's useful in case the player try to position the worker on an invalid square, in this
 * case the request is reiterated with the same {@code workerNum}
 */
public class ChooseWorkerPosition extends SpecificUserEvent implements EventForClient{

    //Identifies the number of the worker to be collocated
    private int workerNum;

    public ChooseWorkerPosition(String receiver, int workerNum) {
        super(receiver);
        this.workerNum = workerNum;
    }

    public int getWorkerNum() {
        return workerNum;
    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {

    }
}
