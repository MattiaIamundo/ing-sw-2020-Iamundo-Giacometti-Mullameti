package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_server.*;

public interface VisitorForPong {

    void visitBuild(Build event);

    void visitDecisionTaken(DecisionTaken event);

    void visitGodChoice(GodChoice event);

    void visitGodsDeck(GodsDeck event);

    void visitMoveChoice(MoveChoice event);

    void visitWorkerChoice(WorkerChoice event);

    void visitWorkerPosition(WorkerPosition event);

    void visitPong(Pong event);

    void visitNumberOfPlayers(NumberOfPlayers event);
}
