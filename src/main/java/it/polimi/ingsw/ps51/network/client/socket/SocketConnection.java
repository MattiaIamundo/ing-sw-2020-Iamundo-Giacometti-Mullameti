package it.polimi.ingsw.ps51.network.client.socket;

import it.polimi.ingsw.ps51.events.events_for_client.Disconnection;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.network.client.ClientInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class represents the {@link ClientInterface} with the socket
 * here there are:
 * the socket,
 * the streams to communicate with the server,
 * and the timeout to indicate if there are connection problems
 * @author Luca Giacometti
 */
public class SocketConnection implements ClientInterface {

    Socket connection;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Integer timeout;

    /**
     * Constructor
     * @param socket the socket connection
     * @param timeout the timeout of socket
     * @throws IOException throws only if the creation of the streams
     *          is getting some problems
     */
    public SocketConnection(Socket socket, Integer timeout) throws IOException {
        this.connection = socket;
        this.timeout = timeout;
        this.oos = new ObjectOutputStream(this.connection.getOutputStream());
        this.ois = new ObjectInputStream(this.connection.getInputStream());
    }

    @Override
    public boolean sendEvent(EventForServer event) {
        try {
            this.oos.writeObject(event);
            return true;
        } catch (IOException e) {
            System.out.println("The server is down...");
        }
        return false;
    }

    @Override
    public EventForClient receiveEvent() {
        try {
            this.connection.setSoTimeout(timeout);
            EventForClient event = (EventForClient) this.ois.readObject();
            this.connection.setSoTimeout(0);
            return event;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The object input stream is closed by server...");
        }
        return new Disconnection();
    }

    @Override
    public void closeConnection() {
        try {
            this.oos.close();
        } catch (IOException e) {
            System.out.println("The object output stream is already closed by server...");
        }
        try {
            this.ois.close();
        } catch (IOException e) {
            System.out.println("The object input stream is already closed...");
        }
        try {
            this.connection.close();
        } catch (IOException e) {
            System.out.println("The socket connection is already closed...");
        }
    }
}
