package it.polimi.ingsw.ps51;

import it.polimi.ingsw.ps51.network.server.MainServer;
import it.polimi.ingsw.ps51.network.server.socket.ServerSocket;

import java.io.IOException;

/**
 * This class is the first class to be launched from the server,
 * it admits the server to start the class {@link MainServer}
 * @author Luca Giacometti
 */
public class StartApplicationServer {

    public static void main(String[] args) {
        try {
            MainServer mainServer = new MainServer();
            ServerSocket ss;
            if (args.length == 1){
                ss = new ServerSocket(mainServer, Integer.parseInt(args[0]));
            }else {
                ss = new ServerSocket(mainServer, 20000);
            }
            mainServer.setSS(ss);
            Thread t1 = new Thread(mainServer);
            t1.start();
        } catch (IOException e) {
            System.out.println("Something during the initialization of all the server network failed...");
        }
    }
}
