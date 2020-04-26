package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_server.Nickname;
import it.polimi.ingsw.ps51.events.events_for_server.NumberOfPlayers;
import it.polimi.ingsw.ps51.events.events_for_server.Pong;
import it.polimi.ingsw.ps51.network.server.socket.SocketConnection;

public class VisitorSocketConnectionServer implements VisitorFirstPhase{

    SocketConnection socketConnection;

    public VisitorSocketConnectionServer(SocketConnection sc) {
        this.socketConnection = sc;
    }

    @Override
    public void visitNickname(Nickname nickname) {
        socketConnection.setNickname(nickname.getNickname());
        if ( socketConnection.checkName() )
            socketConnection.first();
    }

    @Override
    public void visitNumberOfPlayer(NumberOfPlayers numberOfPlayers) {
        socketConnection.setOnServerNumberOfPlayer(numberOfPlayers.getPlayerNumber());
    }

    @Override
    public void visitPong(Pong event) {
        socketConnection.startPingThread();
    }
}
