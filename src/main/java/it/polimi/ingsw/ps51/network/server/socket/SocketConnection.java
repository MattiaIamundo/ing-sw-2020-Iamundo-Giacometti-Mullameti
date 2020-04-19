package it.polimi.ingsw.ps51.network.server.socket;

import it.polimi.ingsw.ps51.events.events_for_client.Disconnection;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.NumberOfPlayer;
import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.network.server.MainServer;
import it.polimi.ingsw.ps51.network.server.Room;
import it.polimi.ingsw.ps51.network.server.ServerInterface;
import it.polimi.ingsw.ps51.visitor.VisitorSocketConnectionServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class represents the socket connection created by the server to communicate with the client
 * @author Luca Giacometti
 */
public class SocketConnection implements Runnable, ServerInterface {

    private boolean isFinish;
    boolean nick;
    boolean ok;
    Socket connection;
    private String nickname;
    private Room gameRoom;
    ObjectOutputStream oos;
    MainServer mainServer;
    ObjectInputStream ois;
    VisitorSocketConnectionServer visitor;

    /**
     * Constructor
     * @param connection the socket connection given by the {@link ServerSocket}
     * @param mainServer the link to the {@link MainServer}
     * @throws IOException it is launched if there are problems with the streams
     */
    public SocketConnection(Socket connection, MainServer mainServer) throws IOException {
        this.isFinish = false;
        this.nick = true;
        this.ok = false;
        this.nickname = null;
        this.connection = connection;
        this.mainServer = mainServer;
        this.visitor = new VisitorSocketConnectionServer(this);
        this.oos = new ObjectOutputStream(this.connection.getOutputStream());
        this.ois = new ObjectInputStream(this.connection.getInputStream());
    }

    public void setNickname(String nick) {
        this.nickname = nick;
    }

    public boolean checkName() {
        boolean ok;
        synchronized (this.mainServer.getObjectToSynchronized()) {
            ok = this.mainServer.checkName(this.nickname);
            if (ok) {
                this.mainServer.addNickname(this.nickname, this);
            }
        }
        return ok;
    }

    public void first() {
        boolean first;
        synchronized (this.mainServer.getObjectToSynchronized()) {
            first = this.mainServer.iMFirst(this.nickname);
        }
        if (first)
            this.nick = false;
        else
            this.ok = true;
    }

    public void setOnServerNumberOfPlayer(Integer number) {
        synchronized (this.mainServer.getObjectToSynchronized()) {
            this.mainServer.setNumberOfPlayer(number);
        }
        this.ok = true;
    }

    @Override
    public void sendEvent(EventForClient event){
        try {
            this.oos.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
            /*
            if (this.nickname == null);
            else if (!this.ok) {

            }
            else {}

             */
        }
    }

    @Override
    public void pairWithRoom(Room room) {
        this.gameRoom = room;
    }

    @Override
    public void closeConnection() {
        this.isFinish = true;
        try {
            this.oos.close();
        } catch (IOException e) {
            //e.printStackTrace();
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

    /**
     * When this class is instantiated, it starts the first phase:
     * it send and waiting the request of nickname and the answer from the user,
     * checks if it is a good one (if it is unique), and if it is, it adds it to the
     * server list of nicknames creating the link between this nickname and this class.
     * If the client is the first one, requests the number of player of the game and then
     * it sets up that number.
     * After that, it starts the normal phase of the game
     */
    @Override
    public void run() {

        try {

            while (!ok) {

                if (nick)
                    sendEvent(new it.polimi.ingsw.ps51.events.events_for_client.Nickname());
                else
                    sendEvent(new NumberOfPlayer());

                EventForFirstPhase event = (EventForFirstPhase) this.ois.readObject();
                event.acceptVisitor(this.visitor);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            synchronized (this.mainServer.getObjectToSynchronized()) {
                if (this.nickname != null)
                    this.mainServer.removeNickname(this.nickname);
            }
            sendEvent(new Disconnection());
            isFinish = true;
        }

        try {
            while(!isFinish) {
                EventForServer event = (EventForServer) this.ois.readObject();
                this.gameRoom.notify(event);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //this.gameRoom.notify(new it.polimi.ingsw.ps51.events.events_for_server.Disconnection(this.nickname));
        }

    }
}
