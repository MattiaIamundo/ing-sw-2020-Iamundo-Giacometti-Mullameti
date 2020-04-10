package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.events_for_client.ChooseBuild;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseMove;
import it.polimi.ingsw.ps51.events.events_for_client.ChooseWorker;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.Observer;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class GodController extends Observable<EventForClient> implements Observer<EventForServer> {
    Card card;
    Map map;
    Player player;
    Worker selectedWorker;

    public GodController(Card card, Map map, Player player) {
        this.card = card;
        this.map = map;
        this.player = player;
    }

    public void start(){
        List<Worker> validWorkers = new ArrayList<>();

        for (Worker worker : player.getWorkers()){
            if (!card.checkMoves(player, worker, map).isEmpty()){
                validWorkers.add(worker);
            }
        }

        notify(new ChooseWorker(validWorkers, player.getNickname()));
    }

    protected void searchForMoves(Worker worker){
        List<Coordinates> validMoves;

        for (Worker worker1 : player.getWorkers()){
            if (worker1.equals(worker)){
                selectedWorker = worker1;
            }
        }
        validMoves = card.checkMoves(player, selectedWorker, map);
        notify(new ChooseMove(validMoves, player.getNickname()));
    }

    protected void effectuateMove(Coordinates moveTo){
        try {
            Square square = map.getSquare(moveTo.getX(), moveTo.getY());
            card.move(player, selectedWorker, square, map);
            searchForBuild();
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }

    protected void searchForBuild(){
        List<Pair<Coordinates, List<Level>>> vaidBuilds;

        vaidBuilds = card.checkBuild(selectedWorker, map);
        notify(new ChooseBuild(vaidBuilds, player.getNickname()));
    }

    protected void build(Coordinates coordinates, Level level){
        try {
            Square square = map.getSquare(coordinates.getX(), coordinates.getY());
            card.build(selectedWorker, square, level, map);
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
    }


}
