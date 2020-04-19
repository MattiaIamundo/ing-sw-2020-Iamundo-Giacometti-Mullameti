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

    /**
     * The method is called as a consequence of receiving a {@code WorkerChoice} event that specifies which of the player's
     * workers must be selected to perform the turn
     * @param worker the selected worker
     */
    void manageWorkerChoice(Worker worker);

    /**
     * The method is called as a consequence of receiving a {@code MoveChoice} event that specifies where to move the
     * selected worker
     * @param moveTo the coordinates where the worker must be moved
     */
    void manageMoveChoice(Coordinates moveTo);

    /**
     * The method is called as a consequence of receiving a {@code Build} event that specifies where and at which level
     * must be built a new tower's level.
     * @param buildOn the coordinates where the new level must be built
     * @param level the level that must be built
     */
    void manageBuildChoice(Coordinates buildOn, Level level);
}
