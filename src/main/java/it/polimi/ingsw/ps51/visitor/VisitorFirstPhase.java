package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_server.Nickname;
import it.polimi.ingsw.ps51.events.events_for_server.NumberOfPlayers;
import it.polimi.ingsw.ps51.events.events_for_server.Pong;

/**
 * This interface represents the visitor which handles the identification phase
 */
public interface VisitorFirstPhase {

    /**
     * To handle the {@link Nickname} event
     * @param nickname the nickname event received from the client
     */
    void visitNickname(Nickname nickname);

    /**
     * To handle the {@link NumberOfPlayers} event
     * @param numberOfPlayers the number of player event received from the client
     */
    void visitNumberOfPlayer(NumberOfPlayers numberOfPlayers);

    /**
     * To handle the {@link Pong} event
     * @param event the pong event received from the client
     */
    void visitPong(Pong event);
}
