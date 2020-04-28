package it.polimi.ingsw.ps51.view;

import it.polimi.ingsw.ps51.events.events_for_client.*;


public class VisitorView implements VisitorClient {

    private Supporter s;

    public VisitorView(Supporter supporter) {
        s = supporter;
    }

    @Override
    public String visitChooseBuild(ChooseBuild event) {
        s.setValidChoicesBuild(event.getValidChoices());
        return "BUILD";
    }

    @Override
    public String visitChooseGod(ChooseGod event) {
        s.setAvailableGods(event.getAvailableGods());
        return "GOD";
    }

    @Override
    public String visitChooseGodsDeck(ChooseGodsDeck event) {
        s.setGodsNum(event.getGodsNum());
        return "GODSDECK";
    }

    @Override
    public String visitChooseMove(ChooseMove event) {
        s.setValidChoicesMoves(event.getValidChoices());
        return "MOVE";
    }

    @Override
    public String visitChooseWorker(ChooseWorker event) {

        s.setValidChoicesWorkers(event.getValidChoices());
        return "WORKER";
    }

    @Override
    public String visitChooseWorkerPosition(ChooseWorkerPosition event) {

        s.setWorkerNum(event.getWorkerNum());
        return "WORKERPOSITION";

    }

    @Override
    public String visitDisconnection(Disconnection event) {

        return "DISCONNECTION";
    }

    @Override
    public String visitEndEvent(EndEvent event) {
        return "END";
    }

    @Override
    public String visitLose(Lose event) {
        return "LOSE";
    }

    @Override
    public String visitMakeDecision(MakeDecision event) {
        return "DECISION";
    }

    @Override
    public String visitMapUpdate(MapUpdate event) {
        s.setMap(event.getMap());
        s.setWorkers(event.getWorkers());
        return "MAP";
    }

    @Override
    public String visitNickname(Nickname event) {
        return "NICKNAME";
    }

    @Override
    public String visitNumberOfPlayer(NumberOfPlayer event) {
        return "NUMBEROFPLAYER";
    }

    @Override
    public String visitWin(Win event) {
        return "WIN";
    }

    @Override
    public String visitOutOfRoom(OutOfRoom event) {
        return "ROOM";
    }
}
