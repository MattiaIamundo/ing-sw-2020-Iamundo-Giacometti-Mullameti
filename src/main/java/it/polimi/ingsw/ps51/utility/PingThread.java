package it.polimi.ingsw.ps51.utility;

import it.polimi.ingsw.ps51.events.events_for_client.Ping;
import it.polimi.ingsw.ps51.network.server.socket.SocketConnection;

/**
 * This class represents a thread which send after an amount of time
 * one {@link Ping} event to the client
 */
public class PingThread implements Runnable{

    SocketConnection sc;
    int halfTimeout;

    /**
     * Constructor
     * @param socketConnection the socket connection which has this as attribute
     * @param halfTimeout the amount of time to sleep before to send the ping event
     */
    public PingThread(SocketConnection socketConnection, int halfTimeout) {
        this.sc = socketConnection;
        this.halfTimeout = halfTimeout;
    }

    /**
     * Sleep an amount of time and then send a ping event
     */
    @Override
    public void run() {

        try {
            Thread.sleep(halfTimeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sc.sendEvent(new Ping());
    }
}
