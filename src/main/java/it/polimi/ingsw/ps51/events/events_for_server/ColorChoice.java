package it.polimi.ingsw.ps51.events.events_for_server;

import it.polimi.ingsw.ps51.view.Color;
import it.polimi.ingsw.ps51.visitor.VisitorForPong;

/**
 * This event represents the color chosen by the client
 */
public class ColorChoice implements EventForServer{

    Color colorChoice;

    /**
     * Constructor
     * @param colorChoice the color chosen
     */
    public ColorChoice(Color colorChoice) {
        this.colorChoice = colorChoice;
    }

    /**
     * Getter of colorChoice
     * @return the reference to the color chosen
     */
    public Color getColorChoice() {
        return this.colorChoice;
    }

    @Override
    public void acceptVisitor(VisitorServer visitor) {
        visitor.visitColorChoice(this);
    }

    @Override
    public void acceptVisitor(VisitorForPong visitor) {
        visitor.visitColorChoice(this);
    }
}
