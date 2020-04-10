package it.polimi.ingsw.ps51.events.events_for_client;

public interface VisitorClient {

    void visitChooseBuild(ChooseBuild event);

    void visitChooseMove(ChooseMove event);

    void visitChooseWorker(ChooseWorker event);

    void visitDisconnection(Disconnection event);

    void visitEndEvent(EndEvent event);

    void visitLose(Lose event);

    void visitNickname(Nickname event);

    void visitNumberOfPlayer(NumberOfPlayer event);

    void visitWin(Win event);
}
