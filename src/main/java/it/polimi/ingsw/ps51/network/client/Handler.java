package it.polimi.ingsw.ps51.network.client;

import it.polimi.ingsw.ps51.events.events_for_client.Disconnection;
import it.polimi.ingsw.ps51.events.events_for_client.EndEvent;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.Ping;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.events.events_for_server.Pong;
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
    ClientInterface connection;
    private final Object ob = new Object();

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
     * it is always waiting an event with the method receiveEvent of the {@link ClientInterface}
     * so the thread is blocked in it until an event is arrived.
     * After it, if the event is not equal to null it is propagated to the {@link Supporter},
     * and if it is a terminated event {@link EndEvent} or {@link Disconnection}
     * the client is disconnected.
     * If the event is a {@link Ping}, it isn't necessary
     * propagate it, in fact the handler responds automatically to the server with a {@link Pong}
     */
    @Override
    public void run() {

        while (!isFinished) {
            EventForClient event = connection.receiveEvent();
            if (event != null) {

                if (event.getReceiver().equals("PING")) {
                    boolean ok;
                    synchronized (this.ob) {
                        ok = connection.sendEvent(new Pong());
                        if (!ok) {
                            isFinished = true;
                            notify(new Disconnection());
                        }
                    }
                }
                else {
                    notify(event);
                    if(event.getReceiver().equals("END") || event.getReceiver().equals("DISCONNECTION")) {
                        isFinished = true;
                    }

                }

            }
            else {
                isFinished = true;
                Disconnection d = new Disconnection();
                notify(d);
            }
        }

        connection.closeConnection();

    }

    /**
     * Here is the call to the sendEvent to propagate the event
     * generated by the user to the server.
     * If the propagation was gone good, there is no problem
     * else the client has to be disconnected
     * @param message the event which have to be send to the server
     */
    public void update(EventForServer message) {
        synchronized (this.ob) {
            boolean ok = connection.sendEvent(message);
            if (!ok) {
                isFinished = true;
                notify(new Disconnection());
                connection.closeConnection();
            }
        }
    }

}
