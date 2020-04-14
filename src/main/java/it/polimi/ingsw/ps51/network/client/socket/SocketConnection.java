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
 * This class represents the connection with the socket
 * here there are:
 * the socket,
 * the streams to communicate with the server
 * @author Luca Giacometti
 */
public class SocketConnection implements ClientInterface {

    Socket connection;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    /**
     * Constructor
     * @param socket the socket connection
     */
    public SocketConnection(Socket socket) throws IOException {
        this.connection = socket;
        this.oos = new ObjectOutputStream(this.connection.getOutputStream());
        this.ois = new ObjectInputStream(this.connection.getInputStream());
    }

    @Override
    public boolean sendEvent(EventForServer event) {
        try {
            this.oos.writeObject(event);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public EventForClient receiveEvent() {
        try {
            return (EventForClient) this.ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Disconnection();
    }

    @Override
    public void closeConnection() {
        try {
            this.oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
