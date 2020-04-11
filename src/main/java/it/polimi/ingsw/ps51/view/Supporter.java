package it.polimi.ingsw.ps51.view;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.Observer;

abstract public class Supporter extends Observable<EventForServer> implements Runnable, Observer<EventForClient> {
}
