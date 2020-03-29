package it.polimi.ingsw.ps51.model;

/**
 * @author Luca
 * This class represents the single action of the game to be memorized in {@link MemoryTurn}
 * which which is identified by:
 * the nickname of the player who has done the action,
 * the action {@link Action},
 * and the number of the turn when he did this action
 */
public class ActionTurn {

    private String nicknameOfPlayer;
    private Action action;
    private Integer numberOfTurn;

    /**
     * Constructor of the class
     * it sets:
     * the numberOfTurn to zero and
     * the action and the nickname to null
     */
    public ActionTurn(){
        this.numberOfTurn = 0;
        this.action = null;
        this.nicknameOfPlayer = null;
    }

    /**
     * Setter of nicknameOfPlayer
     * @param nickname is the nickname of the player who has done this move
     */
    public void setNicknameOfPlayer(String nickname){
        this.nicknameOfPlayer = nickname;
    }

    /**
     * Getter of nicknameOfPlayer
     * @return the nickname of the player who has done this move
     */
    public String getNickname(){
        return this.nicknameOfPlayer;
    }

    /**
     * Setter of action
     * @param action this is the action done by the player
     */
    public void setAction(Action action){
        this.action = action;
    }

    /**
     * Getter of the action
     * @return the action done by the player
     */
    public Action getAction(){
        return this.action;
    }

    /**
     * Setter of numberOfTurn
     * @param numberOfTurn it is an Integer which indicates the number of the turn
     */
    public void setNumberOfTurn(Integer numberOfTurn){
        this.numberOfTurn = numberOfTurn;
    }

    /**
     * Getter of numberOfTurn
     * @return an Integer which indicates the number of the turn
     */
    public Integer getNumberOfTurn(){
        return this.numberOfTurn;
    }

}
