package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Worker;

public interface GodController {

    /**
     * This method is that one which must be called to start the turn, it retrieves the list of the workers that can be
     * moved and creates an event which is sent to the actual player who choose the worker to use,
     * if none of the workers of the actual player can be moved Game will be advised that the actual player
     * lost the match
     */
    void start();

    void manageWorkerChoice(Worker worker);

    void manageMoveChoice(Coordinates moveTo);

    void manageBuildChoice(Coordinates buildOn, Level level);
}
