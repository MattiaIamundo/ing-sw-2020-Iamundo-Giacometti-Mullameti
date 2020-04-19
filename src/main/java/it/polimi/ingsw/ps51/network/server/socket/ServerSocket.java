package it.polimi.ingsw.ps51.network.server.socket;

import it.polimi.ingsw.ps51.network.server.MainServer;

import java.io.IOException;

/**
 * This class represents the Server which create the socket connection
 * @author Luca Giacometti
 */
public class ServerSocket implements Runnable{

    MainServer mainServer;
    java.net.ServerSocket ss;

    /**
     * Constructor
     * @param mainServer the central server
     * @throws IOException it is thrown if the server socket has got some problem with the port number
     */
    public ServerSocket(MainServer mainServer) throws IOException {
        this.mainServer = mainServer;
        this.ss = new java.net.ServerSocket(20000);
    }

    /**
     * Here the server socket creates the socket connection with the clients
     * until the number of player for the creation of a room is reached up
     */
    @Override
    public void run() {

        while (this.mainServer.computeTheSizeOfList()) {
            try {
                SocketConnection si = new SocketConnection(ss.accept(),this.mainServer);
                Thread t = new Thread(si);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("The accept request didn't produced a new socket connection...");
                System.out.println("or the streams produced some problems...");
            }
        }
    }
}
