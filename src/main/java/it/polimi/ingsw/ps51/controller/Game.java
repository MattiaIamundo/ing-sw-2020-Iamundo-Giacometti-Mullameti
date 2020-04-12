package it.polimi.ingsw.ps51.controller;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.Event;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.MemoryTurn;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.Playground;
import it.polimi.ingsw.ps51.utility.GameObserver;
import it.polimi.ingsw.ps51.utility.Observable;

import java.util.List;

public class Game extends Observable<Event> implements GameObserver {

    private Playground gameRoom;
    private Event answerFromClient;
    private boolean goOnWithGame;
    private boolean thereIsAWinner;

    public Game(Playground gameRoom) {
        this.gameRoom = gameRoom;
        answerFromClient = null;
        goOnWithGame = false;
        // i have also to add the observers
    }

    public void startGame() {}

    public Player checkWinner(List<Player> players) {
        return null;
    }

    public void insertNewActionTurn(MemoryTurn memoryTurn, String nicknamePlayer, String typeOfAction, Coordinates cordOfAction) {}

    public void changeTurn(){
        //TODO the mechanics to check is the actual player win the match and if not change the actual player
    }

    @Override
    public void update(Event message) {
        //for this event i have to set towards the pattern visitor
        //the right attribute to go on with the game
    }

    @Override
    public void update(ControllerToGame message) {
        switch (message){
            case END_TURN:
                break;
            case LOSER:
                break;
            case WINNER:
                break;
        }
    }
}
