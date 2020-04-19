package it.polimi.ingsw.ps51.visitor;

import it.polimi.ingsw.ps51.events.events_for_server.Disconnection;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.network.server.Room;

public class VisitorRoom implements VisitorDisconnection{

    Room room;

    public VisitorRoom(Room room) {
        this.room = room;
    }

    @Override
    public void visitDisconnection(Disconnection disconnection) {

    }

    @Override
    public void visitOtherEvents(EventForServer eventForServer) {

    }
}
