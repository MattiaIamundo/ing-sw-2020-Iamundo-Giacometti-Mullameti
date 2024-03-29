package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.visitor.VisitorFirstPhase;
import it.polimi.ingsw.ps51.visitor.VisitorForPong;
import it.polimi.ingsw.ps51.visitor.VisitorSocketConnectionServer;

/**
 * Event that carries the chosen nickname by a user
 */
public class Nickname implements EventForFirstPhase{

    String nickname;

    public Nickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {

    }

    @Override
    public void acceptVisitor(VisitorForPong visitor) {

    }

    @Override
    public void acceptVisitor(VisitorFirstPhase visitor) {
        visitor.visitNickname(this);
    }
}
