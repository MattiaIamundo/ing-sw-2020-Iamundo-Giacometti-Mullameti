package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.network.server.socket.SocketConnection;

public class VisitorPong implements VisitorForPong {

    SocketConnection socketConnection;

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

    @Override
    public void visitPong(Pong event) {
        socketConnection.startPingThread();
    }

    @Override
    public void visitNumberOfPlayers(NumberOfPlayers event) {
        socketConnection.setOnServerNumberOfPlayer(event.getPlayerNumber());
    }


}
