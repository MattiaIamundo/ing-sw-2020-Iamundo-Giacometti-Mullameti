package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event directed to the challenger to ask him to select as many God's card as the number of players
 */
public class ChooseGodsDeck extends SpecificUserEvent implements EventForClient{

    public ChooseGodsDeck(String receiver) {
        super(receiver);
    }

    @Override
    public void acceptVisitor(VisitorClient visitor) {
        visitor.visitChooseGodsDeck(this);
    }
}
