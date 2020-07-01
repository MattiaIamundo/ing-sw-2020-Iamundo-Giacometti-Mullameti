package it.polimi.ingsw.ps51.controller;

import it.polimi.ingsw.ps51.controller.gods.GodControllerWithDecision;
import it.polimi.ingsw.ps51.events.events_for_server.*;

public class VisitorController implements VisitorServer {

    Game game;

    public VisitorController(Game game) {
        this.game = game;
    }

    @Override
    public void visitMoveChoice(MoveChoice event) {
        game.getActualController().manageMoveChoice(event.getMoveTo());
    }

    @Override
    public void visitBuild(Build event) {
        game.getActualController().manageBuildChoice(event.getBuildOn().getValue0(), event.getBuildOn().getValue1());
    }

    @Override
    public void visitWorkerChoice(WorkerChoice event) {
        game.getActualController().manageWorkerChoice(event.getChosenWorker());
    }

    @Override
    public void visitWorkerPosition(WorkerPosition event) {
        game.phaseFour.setPosition(event.getCoordinates());
    }

    @Override
    public void visitColorChoice(ColorChoice event) {
        game.colorAssignment(event.getColorChoice());
    }

    @Override
    public void visitDecisionTaken(DecisionTaken event) {
        ((GodControllerWithDecision) game.getActualController()).decisionManager(event.isDecided());
    }

    @Override
    public void visitFirstPlayerChoice(FirstPlayerChoice event) {
        game.phaseThree(event.getFirstPlayer());
    }

    @Override
    public void visitGodChoice(GodChoice event) {
        game.assignController(event.getGod());
    }

    @Override
    public void visitGodsDeck(GodsDeck event) {
        game.startGamePhaseTwo(event.getDeck());
    }
}
