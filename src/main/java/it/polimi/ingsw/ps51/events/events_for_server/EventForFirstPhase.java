package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.visitor.VisitorFirstPhase;

public interface EventForFirstPhase extends EventForServer{

    void acceptVisitor(VisitorFirstPhase visitor);
}
