package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_client.Ping;
import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.network.server.MainServer;
import it.polimi.ingsw.ps51.network.server.socket.SocketConnection;

public class VisitorPong implements VisitorForPong {

    SocketConnection socketConnection;

    /**
     * Constructor
     * @param socketConnection the socket connection which has this as attribute
     */
    public VisitorPong (SocketConnection socketConnection) {
        this.socketConnection = socketConnection;
    }

    @Override
    public void visitBuild(Build event) {
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitDecisionTaken(DecisionTaken event) {
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitGodChoice(GodChoice event) {
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitGodsDeck(GodsDeck event) {
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitMoveChoice(MoveChoice event) {
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitWorkerChoice(WorkerChoice event) {
        socketConnection.getGameRoom().notify(event);
    }

    @Override
    public void visitWorkerPosition(WorkerPosition event) {
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
        socketConnection.setOnServerNumberOfPlayer(event.getPlayerNumber());
    }

    @Override
    public void visitColorChoice(ColorChoice event) {
        socketConnection.getGameRoom().notify(event);
    }

}
