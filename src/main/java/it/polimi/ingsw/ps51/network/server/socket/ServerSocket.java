package it.polimi.ingsw.ps51.network.server.socket;

import it.polimi.ingsw.ps51.network.server.MainServer;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * This class represents the Server which creates the {@link SocketConnection}
 * @author Luca Giacometti
 */
public class ServerSocket implements Runnable{

    MainServer mainServer;
    java.net.ServerSocket ss;
    Logger logger = Logger.getLogger(this.getClass().getName());

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
            logger.info("[SERVERSOCKET]: I'm already closed...");
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
            logger.info("[SERVERSOCKET]: I'm on!");
            try {
                while (this.mainServer.computeTheSizeOfList()) {
                    SocketConnection si = new SocketConnection(ss.accept(), this.mainServer);
                    logger.info("[SERVERSOCKET]: I'm processing a new player...");
                    Thread t = new Thread(si);
                    t.start();
                }
            } catch (IOException e) {
                logger.info("[SERVERSOCKET]: I'm shutting down...");
        }
    }
}
