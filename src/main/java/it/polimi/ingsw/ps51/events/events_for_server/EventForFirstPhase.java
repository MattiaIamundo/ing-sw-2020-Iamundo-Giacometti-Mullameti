package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.visitor.VisitorSocketConnectionServer;

public interface EventForFirstPhase extends EventForServer{

    void acceptVisitor(VisitorSocketConnectionServer visitor);
}
