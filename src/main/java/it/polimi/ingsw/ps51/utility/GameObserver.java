package it.polimi.ingsw.ps51.utility;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.Event;

public interface GameObserver extends Observer<Event>{

    void update(ControllerToGame message);
}
