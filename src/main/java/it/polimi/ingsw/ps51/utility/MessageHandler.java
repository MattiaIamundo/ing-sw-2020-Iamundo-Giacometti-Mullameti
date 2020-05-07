package it.polimi.ingsw.ps51.utility;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.VisitorClient;
import it.polimi.ingsw.ps51.view.Supporter;
import it.polimi.ingsw.ps51.view.VisitorView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class control if there is a new event from the server to signal it to the cli
 */
public class MessageHandler {

    private EventForClient event;
    Supporter supporter;
    VisitorClient visitorClient;
    ExecutorService ex = Executors.newSingleThreadExecutor();

    /**
     * Constructor
     * @param s the supporter which has this as attribute
     */
    public MessageHandler(Supporter s) {
        supporter = s;
        visitorClient = new VisitorView(s);
    }

    /**
     * Getter of ex
     * @return The reference of the executor service used
     */
    public ExecutorService getEx() {
        return this.ex;
    }

    /**
     * Here is tasked a new thread which controls if a new event is arrived from the server
     * @return a future string which contains a string to indicate the type of the new event
     */
    public Future<String> handleTheFuture() {
        return ex.submit( () -> {

            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!supporter.getEvents().isEmpty()) {
                    event = supporter.getEvents().take();
                    return this.event.acceptVisitor(visitorClient);
                }
            }
        });
    }
}
