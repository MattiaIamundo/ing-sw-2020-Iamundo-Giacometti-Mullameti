package it.polimi.ingsw.ps51.network.client;

import it.polimi.ingsw.ps51.events.events_for_client.Disconnection;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.Observer;
import it.polimi.ingsw.ps51.view.Supporter;

/**
 * This class represents the link between the type of connection {@link ClientInterface}
 * and the the type of view chose by the user {@link Supporter}
 * @author Luca Giacometti
 */
public class Handler extends Observable<EventForClient> implements Runnable, Observer<EventForServer>{

    private boolean isFinished;
    private ClientInterface connection;

    /**
     * Constructor
     * @param connection the type of connection
     */
    public Handler(ClientInterface connection) {
        this.connection = connection;
        this.isFinished = false;
    }

    /**
     * Here the Handler handles the communication with the server
     * it is always waiting an event with the method receiveEvent
     * so the thread is blocked in it until the arrive of an event.
     * Therefore, it is propagated to the {@link Supporter}
     */
    @Override
    public void run() {

        while (!isFinished) {
            EventForClient event = connection.receiveEvent();
            if (event != null) {
                notify(event);
                if(event.getReceiver().equals("END") || event.getReceiver().equals("DISCONNECTION"))
                    isFinished = true;
            }
            else {
                isFinished = true;
                Disconnection d = new Disconnection();
                notify(d);
            }
        }

        connection.closeConnection();

    }


    public void update(EventForServer message) {
        boolean ok = connection.sendEvent(message);
        if (!ok) {
            isFinished = true;
            notify(new Disconnection());
            connection.closeConnection();
        }
    }

}
