package it.polimi.ingsw.ps51.network.server.socket;

import it.polimi.ingsw.ps51.events.events_for_client.Disconnection;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.NumberOfPlayer;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.events.events_for_server.Nickname;
import it.polimi.ingsw.ps51.events.events_for_server.NumberOfPlayers;
import it.polimi.ingsw.ps51.network.server.MainServer;
import it.polimi.ingsw.ps51.network.server.Room;
import it.polimi.ingsw.ps51.network.server.ServerInterface;

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
    private boolean firstPhase;
    private Socket connection;
    private String nickname;
    private Room gameRoom;
    private ObjectOutputStream oos;
    private MainServer mainServer;
    private ObjectInputStream ois;

    /**
     * Constructor
     * @param connection the socket connection given by the {@link ServerSocket}
     * @param mainServer the link to the {@link MainServer}
     * @throws IOException it is launched if there are problems with the streams
     */
    public SocketConnection(Socket connection, MainServer mainServer) throws IOException {
        this.isFinish = false;
        this.firstPhase = true;
        this.connection = connection;
        this.mainServer = mainServer;

        this.oos = new ObjectOutputStream(this.connection.getOutputStream());
        this.ois = new ObjectInputStream(this.connection.getInputStream());
    }

    @Override
    public void sendEvent(EventForClient event) {
        try {
            this.oos.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
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

            while (firstPhase) {

                boolean ok = false;
                while (!ok) {

                    sendEvent(new it.polimi.ingsw.ps51.events.events_for_client.Nickname());

                    try {
                        Nickname nickname = (Nickname) this.ois.readObject();
                        ok = true;
                        synchronized (this.mainServer.getObjectToSynchronized()) {
                            for (String s : this.mainServer.getAllNicknamesOfPlayers()) {
                                if (s.equals(nickname.getNickname())) {
                                    ok = false;
                                    break;
                                }
                            }

                            if (ok) {
                                firstPhase = false;
                                this.nickname = nickname.getNickname();
                                this.mainServer.addNickname(nickname.getNickname(), this);
                            }
                        }

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (ok) {
                        boolean eventSent = false;
                        synchronized (this.mainServer.getObjectToSynchronized()) {
                            if (this.mainServer.getActualNicknameInSearchOfRoom().size() == 1 &&
                                    this.mainServer.getActualNicknameInSearchOfRoom().get(0).equals(this.nickname)) {
                                sendEvent(new NumberOfPlayer("client"));
                                eventSent = true;
                            }
                        }

                        if (eventSent) {
                            try {
                                NumberOfPlayers numberOfPlayers = (NumberOfPlayers) this.ois.readObject();

                                synchronized (this.mainServer.getObjectToSynchronized()) {

                                    this.mainServer.setNumberOfPlayer(numberOfPlayers.getPlayerNumber());
                                }

                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } //END WHILE !OK
            }//END WHILE FIRST PHASE
        } catch (NullPointerException e) {
            e.printStackTrace();
            sendEvent(new Disconnection());
            isFinish = true;
        }

        while(!isFinish) {

            try {
                EventForServer event = (EventForServer) this.ois.readObject();
                this.gameRoom.notify(event);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
