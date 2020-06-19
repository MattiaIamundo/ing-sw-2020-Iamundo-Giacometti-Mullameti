package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.model.gods.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Merita Mullameti
 * This class is dedicated to the player
 */
public class Player {

    private final String nickname;
    private Card god = null;
    private List<Worker> workers;

    /**
     * The constructor of the class
     * @param nickname represents the nickname of the player
     */
    public Player (String nickname){
        this.nickname=nickname;
        workers = new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
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
        this.workers.addAll(workers);
    }

    /**
     * Add a worker to the player's worker list
     * @param worker the worker that must added to the player's workers list
     */
    public void addWorker(Worker worker){
        workers.add(worker);
    }

}



