package it.polimi.ingsw.ps51.utility;

import it.polimi.ingsw.ps51.events.events_for_client.Ping;
import it.polimi.ingsw.ps51.network.server.socket.SocketConnection;

public class PingThread implements Runnable{

    SocketConnection sc;
    int halfTimeout;

    public PingThread(SocketConnection socketConnection, int halfTimeout) {
        this.sc = socketConnection;
        this.halfTimeout = halfTimeout;
    }

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
