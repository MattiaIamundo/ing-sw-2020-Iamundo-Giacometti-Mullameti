package it.polimi.ingsw.ps51.utility;

import it.polimi.ingsw.ps51.events.ControllerToGame;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;

public interface GameObserver extends Observer<EventForServer>{

    void update(ControllerToGame message);
}
