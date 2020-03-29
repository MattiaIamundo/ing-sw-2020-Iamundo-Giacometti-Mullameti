package it.polimi.ingsw.ps51.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca
 * This class represents the memory logger which contains all the actions of the game
 * to be memorized in the playground {@link Playground}
 * it is identified by the list of actions {@link ActionTurn} of the game
 */
public class MemoryTurn {

    private List<ActionTurn> listOfAction;

    /**
     * Constructor of the class
     * it allocates the List as a new ArrayList
     */
    public MemoryTurn(){
        this.listOfAction = new ArrayList<>();
    }

    /**
     * Getter of listOfAction
     * @return the list which contains all actions of the game ordered from the first one to the last one
     */
    public List<ActionTurn> getListOfAction(){
        return this.listOfAction;
    }

}
