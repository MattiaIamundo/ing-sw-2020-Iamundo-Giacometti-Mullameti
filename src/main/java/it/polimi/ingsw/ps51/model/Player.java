package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.model.Gods.Card;
import java.util.List;

/**
 * @author Merita Mullameti
 * This class is dedicated to the player
 */
public class Player {

    private String nickname;
    private Card god ;
    private List<Worker> workers;

    /**
     * The constructor of the class
     * @param nickname represents the nickname of the player
     */
    public Player (String nickname){
        this.nickname=nickname;
    }

    /**
     * This method gets the card the player owns
     * @return the card the player owns
     */
    public Card getGod(){
        return this.god;
    }

    /**
     * This method sets the card the player owns
     * @param card represents the card the player owns
     */
    public void setGod(Card card){
        this.god=card;
    }

    /**
     * This method gets the workers the player owns
     * @return the workers this player owns
     */
    public List<Worker> getWorkers(){
        return this.workers;
    }

    /**
     * This method sets the workers the player owns
     * @param workers represents the workers this player owns
     */
    public void setWorkers(List<Worker> workers){
        this.workers=workers;
    }
}



