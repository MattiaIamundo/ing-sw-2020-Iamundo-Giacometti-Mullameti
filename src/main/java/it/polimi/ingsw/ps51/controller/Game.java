package it.polimi.ingsw.ps51.controller;

import it.polimi.ingsw.ps51.event.Event;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.MemoryTurn;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.Playground;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.Observer;
import org.javatuples.Tuple;

import java.util.List;

public class Game extends Observable<Event> implements Observer<Event> {

    private Playground gameRoom;
    private Event answerFromClient;
    private boolean goOnWithGame;
    private Coordinates whereToMove;
    private Coordinates whereToBuild;
    private boolean thereIsAWinner;

    public Game(Playground gameRoom) {
        this.gameRoom = gameRoom;
        answerFromClient = null;
        goOnWithGame = false;
        whereToMove = null;
        whereToBuild = null;
        // i have also to add the observers
    }

    public void startGame() {}

    public void askMove(List<Coordinates> possibleMoves, String nicknamePlayerToAsk) {}

    public void askBuild(List<Tuple> possibleBuild, String nicknamePlayerToAsk) {}

    public Player checkWinner(List<Player> players) {
        return null;
    }

    public void insertNewActionTurn(MemoryTurn memoryTurn, String nicknamePlayer, String typeOfAction, Coordinates cordOfAction) {}

    @Override
    public void update(Event message) {
        //for this event i have to set towards the pattern visitor
        //the right attribute to go on with the game
    }

}
