package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_client.NumberOfPlayer;
import it.polimi.ingsw.ps51.events.events_for_client.OutOfRoom;
import it.polimi.ingsw.ps51.events.events_for_client.Ping;
import it.polimi.ingsw.ps51.events.events_for_server.Nickname;
import it.polimi.ingsw.ps51.events.events_for_server.NumberOfPlayers;
import it.polimi.ingsw.ps51.events.events_for_server.Pong;
import it.polimi.ingsw.ps51.network.server.MainServer;
import it.polimi.ingsw.ps51.network.server.socket.SocketConnection;

import java.util.logging.Logger;

public class VisitorSocketConnectionServer implements VisitorFirstPhase{

    SocketConnection socketConnection;
    Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Constructor
     * @param sc the socket connection which has this visitor as attribute
     */
    public VisitorSocketConnectionServer(SocketConnection sc) {
        this.socketConnection = sc;
    }

    /**
     * If a room is already started, the {@link SocketConnection} is set to terminate
     * and to the client a {@link OutOfRoom} event is send.
     * Else:
     * - If the nickname received if already present, it's requested again
     * - Else it is added and if it is the first, is send a {@link NumberOfPlayer} event
     * @param nickname the nickname event received from the client
     */
    @Override
    public void visitNickname(Nickname nickname) {
        logger.info("[VISITOR SOCKETCONNECTION]: I'm processing the NICKNAME event!");
        if (socketConnection.checkIfIsAlreadyPresentARoom()) {
            //the game is already started so the player cannot be admitted in the room
            logger.info("[VISITOR SOCKETCONNECTION]: A game is already started!");
            socketConnection.setOk(true);
            socketConnection.setFinish(true);
            socketConnection.sendEvent(new OutOfRoom());
            socketConnection.closeConnection();
        }
        else {
            if ( socketConnection.checkName(nickname.getNickname()) ) {
                socketConnection.setNickname(nickname.getNickname());
                boolean first = socketConnection.first();

                if (first) {
                    logger.info("[VISITOR SOCKETCONNECTION of " + nickname.getNickname() + "]: I'm sending the NUMBER OF PLAYER event!");
                    socketConnection.sendEvent(new NumberOfPlayer());
                }
                else
                    socketConnection.setOk(true);
            }
            else {
                logger.info("[VISITOR SOCKETCONNECTION]: There is already the nickname: " + nickname.getNickname() + "!");
                logger.info("[VISITOR SOCKETCONNECTION]: I'm sending again the NICKNAME event!");
                socketConnection.sendEvent(new it.polimi.ingsw.ps51.events.events_for_client.Nickname());
            }
        }
    }

    /**
     * Call a socket connection method to set on the {@link MainServer} the number chosen by first client
     * @param numberOfPlayers the number of player event received from the client
     */
    @Override
    public void visitNumberOfPlayer(NumberOfPlayers numberOfPlayers) {
        logger.info("[VISITOR SOCKETCONNECTION of " + socketConnection.getNickname() + "]: I'm processing the NUMBEROFPLAYERS event!");
        socketConnection.setOnServerNumberOfPlayer(numberOfPlayers.getPlayerNumber());
    }

    /**
     * Call a socket connection method to create a thread to send a {@link Ping} event
     * @param event the pong event received from the client
     */
    @Override
    public void visitPong(Pong event) {
        socketConnection.startPingThread();
    }
}
