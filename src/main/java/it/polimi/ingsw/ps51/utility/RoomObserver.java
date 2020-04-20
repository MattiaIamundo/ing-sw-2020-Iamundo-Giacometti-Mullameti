package it.polimi.ingsw.ps51.utility;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.Disconnection;

public interface RoomObserver extends Observer<EventForClient>{

    void update(Disconnection disconnection);
}
