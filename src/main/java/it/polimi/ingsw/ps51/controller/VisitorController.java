package it.polimi.ingsw.ps51.controller;

import it.polimi.ingsw.ps51.events.events_for_server.*;

public class VisitorController implements VisitorServer {

    Game game;

    public VisitorController(Game game) {
        this.game = game;
    }

    @Override
    public void visitNickname(Nickname event) {

    }

    @Override
    public void visitMoveChoice(MoveChoice event) {

    }

    @Override
    public void visitNumberOfPlayer(NumberOfPlayers event) {

    }

    @Override
    public void visitBuild(Build event) {

    }

    @Override
    public void visitWorkerChoice(WorkerChoice event) {

    }

    @Override
    public void visitDecisionTaken(DecisionTaken event) {

    }
}
