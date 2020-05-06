package it.polimi.ingsw.ps51.network.server.socket;

import it.polimi.ingsw.ps51.network.server.MainServer;

import java.io.IOException;

/**
 * This class represents the Server which creates the {@link SocketConnection}
 * @author Luca Giacometti
 */
public class ServerSocket implements Runnable{

    MainServer mainServer;
    java.net.ServerSocket ss;

    /**
     * Constructor
     * @param mainServer the central server
     * @throws IOException it is thrown if the {@link java.net.ServerSocket} has got some problem with the port number
     */
    public ServerSocket(MainServer mainServer, Integer port) throws IOException {
        this.mainServer = mainServer;
        this.ss = new java.net.ServerSocket(port);
    }

    /**
     * Here the {@link java.net.ServerSocket} is closed
     */
    public void stopSS() {
        try {
            this.ss.close();
        } catch (IOException e) {
            System.out.println("The ServerSocket is already closed...");
        }
    }

    /**
     * Here the {@link java.net.ServerSocket} creates the {@link java.net.Socket} to be inserted
     * in the {@link SocketConnection}
     * until the number of player for the creation of a room is reached up
     * then this thread is terminated
     */
    @Override
    public void run() {

            try {
                while (this.mainServer.computeTheSizeOfList()) {
                    SocketConnection si = new SocketConnection(ss.accept(), this.mainServer);
                    Thread t = new Thread(si);
                    t.start();
                }
            } catch (IOException e) {
                System.out.println("The server socket is down...");
        }
    }
}
