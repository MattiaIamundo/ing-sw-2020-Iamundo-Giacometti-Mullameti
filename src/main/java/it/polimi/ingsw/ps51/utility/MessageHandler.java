package it.polimi.ingsw.ps51.utility;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.VisitorClient;
import it.polimi.ingsw.ps51.view.Supporter;
import it.polimi.ingsw.ps51.view.VisitorView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MessageHandler {

    private EventForClient event;
    Supporter supporter;
    VisitorClient visitorClient;
    ExecutorService ex = Executors.newSingleThreadExecutor();

    public MessageHandler(Supporter s) {
        supporter = s;
        visitorClient = new VisitorView(s);
    }

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
