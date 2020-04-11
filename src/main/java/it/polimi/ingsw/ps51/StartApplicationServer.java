package it.polimi.ingsw.ps51;

import it.polimi.ingsw.ps51.network.server.MainServer;

/**
 * This class is the first class to be launched from the server,
 * it admits the server to start the class {@link MainServer} and the class
 * @author Luca Giacometti
 */
public class StartApplicationServer {

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        //SocketServer ss = new SocketServer(mainServer);

        Thread t1 = new Thread(mainServer);
        //Thread t2 = new Thread(ss);

        //t2.start();
        //t1.start();
    }
}
