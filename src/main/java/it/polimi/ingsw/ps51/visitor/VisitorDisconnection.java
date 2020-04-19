package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_server.Disconnection;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;

public interface VisitorDisconnection {

    void visitDisconnection(Disconnection disconnection);

    void visitOtherEvents(EventForServer eventForServer);
}
