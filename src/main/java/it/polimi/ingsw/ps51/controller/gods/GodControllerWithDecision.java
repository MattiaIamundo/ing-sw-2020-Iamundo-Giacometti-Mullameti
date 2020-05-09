package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.events.events_for_server.DecisionTaken;

public interface GodControllerWithDecision extends GodController {

    /**
     * The method is called as a consequence of receiving a {@link DecisionTaken} event
     * @param takenDecision correspond to the truth value of the decision taken by the user
     */
    void decisionManager(boolean takenDecision);
}
