package it.polimi.ingsw.ps51.network.server.socket;

import it.polimi.ingsw.ps51.events.events_for_client.Disconnection;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.NumberOfPlayer;
import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.network.server.MainServer;
import it.polimi.ingsw.ps51.network.server.Room;
import it.polimi.ingsw.ps51.network.server.ServerInterface;
import it.polimi.ingsw.ps51.utility.PingThread;
import it.polimi.ingsw.ps51.visitor.VisitorFirstPhase;
import it.polimi.ingsw.ps51.visitor.VisitorForPong;
import it.polimi.ingsw.ps51.visitor.VisitorPong;
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
    VisitorFirstPhase visitor;
    private final Object ob = new Object();
    PingThread pingThread;
    int timeOut;
    VisitorForPong visitorPong;

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
        this.timeOut = 20000;
        this.pingThread = new PingThread(this, timeOut/2);
        this.visitorPong = new VisitorPong(this);
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
            synchronized (this.getOb()) {
                this.oos.writeObject(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pairWithRoom(Room room) {
        this.gameRoom = room;
    }

    public String getNickname() {
        return this.nickname;
    }

    public Object getOb() {
        return this.ob;
    }

    public void startPingThread() {
        Thread t = new Thread(this.pingThread);
        t.start();
    }

    public Room getGameRoom() {
        return this.gameRoom;
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
            connection.setSoTimeout(timeOut);
            startPingThread();
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
            connection.setSoTimeout(timeOut);
            while(!isFinish) {
                EventForServer event = (EventForServer) this.ois.readObject();
                event.acceptVisitor(visitorPong);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            this.gameRoom.update(new it.polimi.ingsw.ps51.events.events_for_server.Disconnection(this.nickname));
        }

    }
}
