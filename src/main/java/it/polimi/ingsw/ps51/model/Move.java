package it.polimi.ingsw.ps51.model;

/**
 * @author Luca
 * This class represents the Move action of the game to memorize in the {@link ActionTurn}
 * and it extends {@link Action}
 * it is identify by:
 * the coordinates of the squares of the map which are involved in this move action,
 * the height of the two square involved,
 * a boolean which indicates if the move is forced by the usage of another player's god
 */
public class Move extends Action {

    private Coordinates prevCord;
    private Coordinates nextCord;
    private Level prevHeight;
    private Level nextHeight;
    private boolean isForced;

    /**
     * Constructor of the class
     * @param prevCord the coordinates of the position of the player's worker before the move
     * @param nextCord the coordinates of the position of the player's worker after the move
     * @param prevHeight the level where the player's worker was before the move
     * @param nextHeight the level where the player's worker is after the move
     * @param isForced it represents if this move is done by the player's will or by the power of another player's god
     * @param isUsageGod it represents if the move is done with the power of the player's god
     */
    public Move(Coordinates prevCord, Coordinates nextCord, Level prevHeight, Level nextHeight, boolean isForced, boolean isUsageGod){
        super(isUsageGod);
        this.prevCord = prevCord;
        this.nextCord = nextCord;
        this.prevHeight = prevHeight;
        this.nextHeight = nextHeight;
        this.isForced = isForced;
    }

    /**
     * Getter of prevCord
     * @return the previous coordinates of the player's worker
     */
    public Coordinates getPrevCord() {
        return this.prevCord;
    }

    /**
     * Getter of nextCord
     * @return the coordinate of the player's worker after the move
     */
    public Coordinates getNextCord() {
        return this.nextCord;
    }

    /**
     * Getter of prevHeight
     * @return the previous level where the player's worker was before the move
     */
    public Level getPrevHeight() {
        return this.prevHeight;
    }

    /**
     * Getter of nextHeight
     * @return the level where the player's worker is after the move
     */
    public Level getNextHeight() {
        return this.nextHeight;
    }

    /**
     * Getter of isForced
     * @return a boolean which indicates if this move is done by the player's will or
     * by the power of another player's god
     */
    public boolean getIsForced() {
        return this.isForced;
    }

}
