package it.polimi.ingsw.ps51.model;

/**
 * @author Luca
 * This class represents the Build action of the game to memorize in the {@link ActionTurn}
 * and it extends {@link Action}
 * it is identify by:
 * the position where the player built a structure and
 * the level which indicates the structure the player built {@link Level}
 */
public class Build extends Action {
    
    private Coordinates position;
    private Level newLevel;

    /**
     * Constructor of the class
     * @param position is the position of the Square where the player built
     * @param newLevel is the structure which the player built
     * @param isUsageGod it represents if the move is done with the power of the player's god
     */
    public Build (Coordinates position, Level newLevel, boolean isUsageGod){
        super(isUsageGod);
        this.position = position;
        this.newLevel = newLevel;
    }

    /**
     * Getter of position
     * @return the coordinates which identified the square on the map
     * where the player built a structure
     */
    public Coordinates getPosition(){
        return this.position;
    }

    /**
     * Getter of newLevel
     * @return the structure which the player built
     */
    public Level getNewLevel(){
        return this.newLevel;
    }

}
