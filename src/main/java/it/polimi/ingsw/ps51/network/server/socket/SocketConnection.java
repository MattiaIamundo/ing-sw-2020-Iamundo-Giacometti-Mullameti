package it.polimi.ingsw.ps51.network.server.socket;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.NumberOfPlayer;
import it.polimi.ingsw.ps51.events.events_for_client.Ping;
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
 * This class represents the {@link ServerInterface} with socket created by the server to communicate with the client
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

    /**
     * Setter of nickname of the player
     * @param nick the nickname of the player
     */
    public void setNickname(String nick) {
        this.nickname = nick;
    }

    /**
     * Access to the {@link MainServer} in a synchronized mode
     * to call the checkName method and if it is a good nickname
     * call the addNickname method to add this client
     * @return true if the nickname is a valid one and it is set
     *         false if it is not
     */
    public boolean checkName(String nickname) {
        boolean ok;
        synchronized (this.mainServer.getObjectToSynchronized()) {
            ok = this.mainServer.checkName(nickname);
            if (ok) {
                this.mainServer.addNickname(nickname, this);
            }
        }
        return ok;
    }

    /**
     * Access synchronized to the {@link MainServer} to call
     * the iMFirst method to check is this client is the first one
     * to join for game
     */
    public boolean first() {
        boolean first;
        synchronized (this.mainServer.getObjectToSynchronized()) {
            first = this.mainServer.iMFirst(this.nickname);
        }
        return first;
    }

    /**
     * Access synchronized to the {@link MainServer} to set
     * the number of players for the next game
     * @param number the number of player chosen by the client
     */
    public void setOnServerNumberOfPlayer(Integer number) {
        synchronized (this.mainServer.getObjectToSynchronized()) {
            this.mainServer.setNumberOfPlayer(number);
            this.ok = true;
        }

    }

    @Override
    public void sendEvent(EventForClient event){
        try {
            synchronized (this.getOb()) {
                this.oos.writeObject(event);
            }
        } catch (IOException e) {
            if (this.nickname != null)
                System.out.println("Cannot send event to " + this.nickname + "because he/she is disconnected...");
            else
                System.out.println("Cannot send event to the client because he/she is disconnected...");
        }
    }

    @Override
    public void pairWithRoom(Room room) {
        this.gameRoom = room;
    }

    /**
     * Getter of nickname
     * @return the reference of nickname attribute
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Getter of ob
     * @return the object to synchronize to send an event
     */
    public Object getOb() {
        return this.ob;
    }

    /**
     * Start a new thread {@link PingThread} to send
     * to the client a {@link Ping} event after
     * the timeout divided by two
     */
    public void startPingThread() {
        Thread t = new Thread(this.pingThread);
        t.start();
    }

    /**
     * Getter of gameRoom
     * @return the reference to the gameRoom attribute
     */
    public Room getGameRoom() {
        return this.gameRoom;
    }

    /**
     * Setter of ok attribute
     * @param status the status of ok
     */
    public void setOk(boolean status) {
        this.ok = status;
    }

    /**
     * Checks if is present a room in the {@link MainServer}
     * @return true if there is already a room
     *         false otherwise
     */
    public boolean checkIfIsAlreadyPresentARoom() {
        boolean present;
        synchronized (this.mainServer.getObjectToSynchronized()) {
            present = mainServer.checkIfThereIsAlreadyARoom();
        }
        return present;
    }

    /**
     * Setter of isFinish attribute
     * @param status the status of the game
     */
    public void setFinish(boolean status) {
        this.isFinish = status;
    }

    @Override
    public void closeConnection() {
        this.isFinish = true;
        try {
            this.oos.close();
        } catch (IOException e) {
            if (this.nickname != null)
                System.out.println("The output stream of" + this.nickname + "is already closed by the client disconnection...");
            else
                System.out.println("The output stream of a client is already closed by the himself disconnection...");
        }
        try {
            this.ois.close();
        } catch (IOException e) {
            if (this.nickname != null)
                System.out.println("The input stream of" + this.nickname + "is already closed by the client disconnection...");
            else
                System.out.println("The input stream of a client is already closed by the himself disconnection...");
        }
        try {
            this.connection.close();
        } catch (IOException e) {
            if (this.nickname != null)
                System.out.println("The socket of" + this.nickname + "is already closed by the client disconnection...");
            else
                System.out.println("The socket of a client is already closed by the himself disconnection...");
        }
    }

    /**
     * When this class is instantiated, it starts the first phase:
     * it send and waiting the request of nickname and the answer from the user,
     * checks if it is a good one (if it is unique), and if it is, it adds it to the
     * server list of nicknames creating the link between this nickname and this class.
     * If the client is the first one, requests the number of player of the game and then
     * it sets up that number.
     * If the first one to be accepted in the game is disconnected but the game is not already started,
     * the {@link NumberOfPlayer} event is send to the next player.
     * After that, it starts the normal phase of the game.
     * In every phase, it sends continually a {@link Ping} event
     * to verify if the client is on or not
     */
    @Override
    public void run() {

        try {
            connection.setSoTimeout(timeOut);
            startPingThread();
            sendEvent(new it.polimi.ingsw.ps51.events.events_for_client.Nickname());
            while (!ok) {
                EventForFirstPhase event = (EventForFirstPhase) this.ois.readObject();
                event.acceptVisitor(this.visitor);
            }

        } catch (IOException | ClassNotFoundException e) {

            synchronized (this.mainServer.getObjectToSynchronized()) {
                if (this.nickname != null) {
                    this.mainServer.reAskNumberIfIWasTheFirstOneAndOtherAreConnected(this.nickname);
                    this.mainServer.removeNickname(this.nickname);
                }
                isFinish = true;
            }
        }

        try {

            while(!isFinish) {
                EventForServer event = (EventForServer) this.ois.readObject();
                event.acceptVisitor(visitorPong);
            }
        } catch (IOException | ClassNotFoundException e) {

            if (gameRoom != null) {
                this.gameRoom.update(new it.polimi.ingsw.ps51.events.events_for_server.Disconnection(this.nickname));
            }
            else {
                synchronized (this.mainServer.getObjectToSynchronized()) {
                    this.mainServer.reAskNumberIfIWasTheFirstOneAndOtherAreConnected(this.nickname);
                    this.mainServer.removeNickname(this.nickname);
                }
            }
        }

    }
}
