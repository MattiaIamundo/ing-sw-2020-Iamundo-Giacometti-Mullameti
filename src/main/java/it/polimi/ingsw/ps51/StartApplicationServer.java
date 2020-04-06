package it.polimi.ingsw.ps51;

import it.polimi.ingsw.ps51.network.socket.MainServer;

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
