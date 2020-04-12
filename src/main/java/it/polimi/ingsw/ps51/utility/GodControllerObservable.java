package it.polimi.ingsw.ps51.utility;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;

public class GodControllerObservable extends Observable<EventForClient>{
    private GameObserver game;

    public void addGame(GameObserver game){
        this.game = game;
    }

    public void removeGame(){
        game = null;
    }

    public void notifyToGame(ControllerToGame message){
        game.update(message);
    }
}
