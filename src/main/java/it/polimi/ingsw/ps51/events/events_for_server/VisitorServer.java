package it.polimi.ingsw.ps51.events.events_for_server;

/**
 * Interface used to implements the visitor pattern for the events received by the server
 */
public interface VisitorServer {

    void visitBuild(Build event);

    void visitDecisionTaken(DecisionTaken event);

    void visitFirstPlayerChoice(FirstPlayerChoice event);

    void visitGodChoice(GodChoice event);

    void visitGodsDeck(GodsDeck event);

    void visitMoveChoice(MoveChoice event);

    void visitWorkerChoice(WorkerChoice event);

    void visitWorkerPosition(WorkerPosition event);

    void visitColorChoice(ColorChoice event);
}
