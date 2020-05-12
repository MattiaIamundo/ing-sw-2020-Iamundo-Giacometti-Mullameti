package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.MakeDecision;
import it.polimi.ingsw.ps51.events.events_for_client.UnsuccessfulOperation;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;

public class PoseidonController extends NormalGodsController implements GodControllerWithDecision {

    private boolean usePower = false;
    private int buildCount = 3;

    public PoseidonController(Card card, Map map, Player player) {
        super(card, map, player);
    }

    @Override
    public void build(Coordinates buildOn, Level level) {
        try {
            if (usePower){
                Square square = map.getSquare(buildOn.getX(), buildOn.getY());
                card.build(selectedWorker, square, level, map);
                buildCount--;
                if (buildCount > 0){
                    notify(new MakeDecision(player.getNickname(), "Do you want to build once more?, "+ buildCount +" builds remains"));
                }else {
                    usePower = false;
                    buildCount = 3;
                    notifyToGame(ControllerToGame.END_TURN);
                }
            }else {
                Square square = map.getSquare(buildOn.getX(), buildOn.getY());
                card.build(selectedWorker, square, level, map);
                if (isWinner()){
                    notifyToGame(ControllerToGame.WINNER);
                }else {
                    if (getOtherWorker().getPosition().getLevel().equals(Level.GROUND)){
                        notify(new MakeDecision(player.getNickname(), "Do you want to use your God's power and build, up to three times with your other worker?"));
                    }else {
                        notifyToGame(ControllerToGame.END_TURN);
                    }
                }
            }
        } catch (OutOfMapException e) {
            notify(new UnsuccessfulOperation(player.getNickname()));
            searchForBuild();
        }
    }

    @Override
    public void decisionManager(boolean takenDecision) {
        if (!usePower && takenDecision){
            usePower = true;
            selectedWorker = getOtherWorker();
            searchForBuild();
        }else if (usePower && takenDecision){
            searchForBuild();
        }else {
            usePower = false;
            buildCount = 3;
            notifyToGame(ControllerToGame.END_TURN);
        }
    }

    private Worker getOtherWorker(){
        for (Worker worker : player.getWorkers()){
            if (!worker.equals(selectedWorker)){
                return worker;
            }
        }
        return null;
    }
}
