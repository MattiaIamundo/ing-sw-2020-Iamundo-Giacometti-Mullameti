package it.polimi.ingsw.ps51.events.events_for_server;

/**
 * Event that carries the chosen nickname by a user
 */
public class Nickname implements EventForServer{

    private String nickname;

    public Nickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitNickname(this);
    }
}
