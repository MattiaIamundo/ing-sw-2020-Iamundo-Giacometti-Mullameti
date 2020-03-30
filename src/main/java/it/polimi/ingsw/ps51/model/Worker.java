package it.polimi.ingsw.ps51.model;

/**
 * @author Merita Mullameti
 * This class is dedicated to the workers that each player possesses
 */
public class Worker{

    private String namePlayer;
    private Square position ;
    private boolean canDoLevelUp;


    /**
     * The constructor of the class
     * @param namePlayer represents the nickname of the Player
     */
    public  Worker (String namePlayer){
        this.namePlayer=namePlayer;
    }

    /**
     * This method gets the nickname of the player
     * @return the name of the player
     */
    public String getNamePlayer(){
        return this.namePlayer;
    }

    /**
     * This method sets the position of the worker
     * @param pos represents the square where the worker is
     */
    public void setPosition (Square pos){
        this.position=pos;
    }

    /**
     * This method gets the position of the worker
     * @return the square where the worker is
     */
    public Square getPosition(){
        return this.position;
    }

    /**
     * This method gets if the worker can build in this square
     * @return if the worker can build in this square
     */
    public boolean getCanDoLevelUp(){
        return this.canDoLevelUp;
    }

    /**
     * This method sets if the worker can build in this square
     * @param permission decides if the worker can build in the square
     */
    public void setCanDoLevelUp(boolean permission){
        this.canDoLevelUp=permission;
    }
}
