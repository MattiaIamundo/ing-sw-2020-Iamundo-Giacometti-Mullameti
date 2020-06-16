package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_client.Ping;
import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.network.server.MainServer;
import it.polimi.ingsw.ps51.network.server.socket.SocketConnection;

import java.util.logging.Logger;

public class VisitorPong implements VisitorForPong {

    SocketConnection socketConnection;
    Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Constructor
     * @param socketConnection the socket connection which has this as attribute
     */
    public VisitorPong (SocketConnection socketConnection) {
        this.socketConnection = socketConnection;
    }

    @Override
    public void visitBuild(Build event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a BUILD event is received!");
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitDecisionTaken(DecisionTaken event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a DECISION TAKEN event is received!");
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitFirstPlayerChoice(FirstPlayerChoice event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a FIRST PLAYER CHOICE event is received!");
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitGodChoice(GodChoice event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a GOD CHOICE event is received!");
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitGodsDeck(GodsDeck event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a GODS DECK event is received!");
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitMoveChoice(MoveChoice event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a MOVE CHOICE event is received!");
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitWorkerChoice(WorkerChoice event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a WORKER CHOICE event is received!");
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitWorkerPosition(WorkerPosition event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a WORKER POSITION event is received!");
        socketConnection.getGameRoom().notify(event);
    }

    /**
     * Call a socket connection method to create a thread to send a {@link Ping} event
     * @param event the pong event received from the client
     */
    @Override
    public void visitPong(Pong event) {
        socketConnection.startPingThread();
    }

    /**
     * Call a socket connection method to set on the {@link MainServer} the number chosen by first client
     * @param event the number of player event received from the client
     */
    @Override
    public void visitNumberOfPlayers(NumberOfPlayers event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a NUMBER OF PLAYERS event is received!");
        socketConnection.setOnServerNumberOfPlayer(event.getPlayerNumber());
    }

    @Override
    public void visitColorChoice(ColorChoice event) {
        logger.info("[VISITOR PONG of " + socketConnection.getNickname() + "]: a COLOR CHOICE event is received!");
        socketConnection.getGameRoom().notify(event);
    }

}
