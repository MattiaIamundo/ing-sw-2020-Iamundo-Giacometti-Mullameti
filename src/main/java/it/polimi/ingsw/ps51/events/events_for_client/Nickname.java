package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that inform all the users of a new player indicating it's nickname
 */
public class Nickname implements EventForClient{

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitNickname(this);
    }
}
