package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_server.Nickname;
import it.polimi.ingsw.ps51.events.events_for_server.NumberOfPlayers;
import it.polimi.ingsw.ps51.events.events_for_server.Pong;

public interface VisitorFirstPhase {

    void visitNickname(Nickname nickname);

    void visitNumberOfPlayer(NumberOfPlayers numberOfPlayers);

    void visitPong(Pong event);
}
