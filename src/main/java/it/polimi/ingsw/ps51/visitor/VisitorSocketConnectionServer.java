package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_client.NumberOfPlayer;
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

        if ( socketConnection.checkName(nickname.getNickname()) ) {
            socketConnection.setNickname(nickname.getNickname());
            boolean first = socketConnection.first();

            if (first)
                socketConnection.sendEvent(new NumberOfPlayer());
            else
                socketConnection.setOk(true);
        }
        else
            socketConnection.sendEvent(new it.polimi.ingsw.ps51.events.events_for_client.Nickname());
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
