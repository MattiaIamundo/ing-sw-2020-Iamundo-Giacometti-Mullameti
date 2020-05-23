package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.view.Color;

/**
 * This interface represents the visitor which handles the game phase
 */
public interface VisitorForPong {

    /**
     * To pass to the room the {@link Build} event
     * @param event the build event received from the client
     */
    void visitBuild(Build event);

    /**
     * To pass to the room the {@link DecisionTaken} event
     * @param event the decision taken event received from the client
     */
    void visitDecisionTaken(DecisionTaken event);

    /**
     * To pass to the room the {@link GodChoice} event
     * @param event the god choice event received from the client
     */
    void visitGodChoice(GodChoice event);

    /**
     * To pass to the room the {@link GodsDeck} event
     * @param event the gods deck event received from the client
     */
    void visitGodsDeck(GodsDeck event);

    /**
     * To pass to the room the {@link MoveChoice} event
     * @param event the move choice event received from the client
     */
    void visitMoveChoice(MoveChoice event);

    /**
     * To pass to the room the {@link WorkerChoice} event
     * @param event the worker choice event received from the client
     */
    void visitWorkerChoice(WorkerChoice event);

    /**
     * To pass to the room the {@link WorkerPosition} event
     * @param event the worker position event received from the client
     */
    void visitWorkerPosition(WorkerPosition event);

    /**
     * To handle the {@link Pong} event
     * @param event the pong event received from the client
     */
    void visitPong(Pong event);

    /**
     * To handle the {@link NumberOfPlayers} event
     * @param event the number of player event received from the client
     */
    void visitNumberOfPlayers(NumberOfPlayers event);

    /**
     * To handle the {@link ColorChoice} event
     * @param event the color choice event received from the client
     */
    void visitColorChoice(ColorChoice event);
}
