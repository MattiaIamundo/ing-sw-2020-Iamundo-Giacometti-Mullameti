package it.polimi.ingsw.ps51.network.client;

import it.polimi.ingsw.ps51.StartApplicationClient;
import it.polimi.ingsw.ps51.network.client.socket.SocketConnection;
import it.polimi.ingsw.ps51.network.server.socket.ServerSocket;
import it.polimi.ingsw.ps51.view.Cli;
import it.polimi.ingsw.ps51.view.Supporter;

import java.io.IOException;
import java.net.Socket;

/**
 * This class represents the client starter launched by {@link StartApplicationClient}
 * @author Luca Giacometti
 */
public class Client implements Runnable {

    private Supporter s;
    String url;
    Integer port;
    Integer timeoutSocket;

    /**
     * Constructor
     * @param i it is a 0 if the user wants a {@link Cli}
     * @param url it is the String which represent the url of the server
     * @param port it is the port on which the {@link ServerSocket} is on
     * @param timeoutSocket this integer represents the countdown for the socket
     */
    public Client(Integer i, String url, Integer port, Integer timeoutSocket) {
        this.url = url;
        this.port = port;
        this.timeoutSocket = timeoutSocket;

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
     * If the server is down the exception is catch and this fact is printed
     */
    @Override
    public void run() {

        try {
            Socket socket = new Socket(url, port);
            ClientInterface ci = new SocketConnection(socket, timeoutSocket);
            Handler h = new Handler(ci);
            h.addObserver(s);
            s.addObserver(h);
            Thread thread = new Thread(h);
            Thread t = new Thread(s);
            t.start();
            thread.start();
        } catch (IOException e) {
            System.out.println("The server is down at the moment...");
        }

    }
}
