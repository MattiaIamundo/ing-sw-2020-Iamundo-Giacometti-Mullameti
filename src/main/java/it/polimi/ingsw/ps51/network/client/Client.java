package it.polimi.ingsw.ps51.network.client;

import it.polimi.ingsw.ps51.network.client.socket.SocketConnection;
import it.polimi.ingsw.ps51.view.Cli;
import it.polimi.ingsw.ps51.view.Supporter;

import java.io.IOException;
import java.net.Socket;

/**
 * This class represents the client starter
 * @author Luca Giacometti
 */
public class Client implements Runnable {

    private Supporter s;
    String url;
    Integer port;

    /**
     * Constructor
     * Here the Supporter is created
     * @param i 0 if the user wants a Cli
     */
    public Client(Integer i, String url, Integer port) {
        this.url = url;
        this.port = port;

        if (i == 0)
            s = new Cli();
 /*       else if (i == 1)
            s = new Gui();
        else
            s = new Bot();
  */
    }

    /**
     * Here there is the creation of three object:
     * {@link ClientInterface} in this case only SocketConnection is possible,
     * {@link Handler} h,
     * {@link Supporter} s,
     * and the observers are set up.
     * At the end the {@link Handler} and {@link Supporter} are launched.
     */
    @Override
    public void run() {

        try {
            Socket socket = new Socket(url, port);
            ClientInterface ci = new SocketConnection(socket);
            Handler h = new Handler(ci);
            h.addObserver(s);
            s.addObserver(h);
            Thread thread = new Thread(h);
            Thread t = new Thread(s);
            t.start();
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something during the initialization was not good...");
        }

    }
}