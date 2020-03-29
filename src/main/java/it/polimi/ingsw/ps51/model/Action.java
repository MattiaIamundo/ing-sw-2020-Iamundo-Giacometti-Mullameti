package it.polimi.ingsw.ps51.model;

/**
 * @author Luca
 * This class represents an abstraction of the possible actions of this game,
 * it is abstract because it exists only to group the common methods and attribute
 * of the real actions {@link Move} and {@link Build} which extend this class
 */
abstract class Action {

    private boolean isUsingGod;

    /**
     * Constructor of the class
     * @param isUsageGod it represents if the move is done with the power of the player's god
     */
    public Action(boolean isUsageGod) {
        this.isUsingGod = isUsageGod;
    }

    /**
     * Getter of isUsingGod
     * @return a boolean which indicates if this action was performed
     *         with the usage of the player's god
     */
    public boolean getIsUsingGod(){
        return this.isUsingGod;
    }

    /**
     * Setter of isUsingGod
     * @param state it indicates if the action was performed with the
     *              usage of the player's god
     */
    public void setIsUsingGod(boolean state){
        this.isUsingGod = state;
    }

}
