package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.visitor.VisitorForPong;

import java.util.List;

/**
 * Event that carries the God's cards chosen by the challenger
 */
public class GodsDeck implements EventForServer{

    private List<Gods> deck;

    public GodsDeck(List<Gods> deck) {
        this.deck = deck;
    }

    public List<Gods> getDeck() {
        return deck;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitGodsDeck(this);
    }

    @Override
    public void acceptVisitor(VisitorForPong visitor) {
        visitor.visitGodsDeck(this);
    }
}
