package it.polimi.ingsw.ps51.events.events_for_client;

public interface VisitorClient {

    String visitChooseBuild(ChooseBuild event);

    String visitChooseGod(ChooseGod event);

    String visitChooseGodsDeck(ChooseGodsDeck event);

    String visitChooseMove(ChooseMove event);

    String visitChooseWorker(ChooseWorker event);

    String visitChooseWorkerPosition(ChooseWorkerPosition event);

    String visitDisconnection(Disconnection event);

    String visitEndEvent(EndEvent event);

    String visitLose(Lose event);

    String visitMakeDecision(MakeDecision event);

    String visitMapUpdate(MapUpdate event);

    String visitNickname(Nickname event);

    String visitNumberOfPlayer(NumberOfPlayer event);

    String visitWin(Win event);

    String visitOutOfRoom (OutOfRoom event);

    String visitUnsuccessfulOperation(UnsuccessfulOperation event);

    String visitAck(Ack event);

    String visitGameIsStarting(GameIsStarting event);

    String visitTurnIsEnd(TurnIsEnd event);
}
