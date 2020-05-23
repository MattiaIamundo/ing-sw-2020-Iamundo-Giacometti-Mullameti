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
        game.getActualController().manageWorkerChoice(event.getChosedWorker());
    }

    @Override
    public void visitWorkerPosition(WorkerPosition event) {
        game.thirdPhase.setPosition(event.getCoordinates());
    }

    @Override
    public void visitColorChoice(ColorChoice event) {
        //TODO handle the color chosen by the client, putting that into the player class as an attribute
    }

    @Override
    public void visitDecisionTaken(DecisionTaken event) {
        ((GodControllerWithDecision) game.getActualController()).decisionManager(event.isDecided());
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
