package it.polimi.ingsw.ps51.events.events_for_server;

/**
 * Interface used to implements the visitor pattern for the events received by the server
 */
public interface VisitorServer {

    void visitNickname(Nickname event);

    void visitMoveChoice(MoveChoice event);

    void visitNumberOfPlayer(NumberOfPlayers event);

    void visitBuild(Build event);

    void visitWorkerChoice(WorkerChoice event);

    void visitDecisionTaken(DecisionTaken event);
}
