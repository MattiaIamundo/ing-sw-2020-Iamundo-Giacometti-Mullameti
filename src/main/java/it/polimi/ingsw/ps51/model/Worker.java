package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.model.gods.opponent_move_manager.Gods;
import it.polimi.ingsw.ps51.utility.Observer;
import it.polimi.ingsw.ps51.utility.WorkerObserver;
import org.javatuples.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Merita Mullameti
 * This class is dedicated to the workers that each player possesses
 */
public class Worker implements Serializable, WorkerObserver, Cloneable {

    private String namePlayer;
    private Square position;
    private boolean inWinningCondition = false;
    private List<Gods> activeGods = new ArrayList<>();
    //TODO clean this array before turn change

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
        if (position != null){
            position.setPresentWorker(false);
        }
        this.position=pos;
        position.setPresentWorker(true);
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
    public boolean getInWinningCondition(){
        return this.inWinningCondition;
    }

    /**
     * This method sets if the worker can build in this square
     * @param condition decides if the worker can build in the square
     */
    public void setInWinningCondition(boolean condition){
        this.inWinningCondition =condition;
    }

    public List<Gods> getActiveGods() {
        return activeGods;
    }


    @Override
    public void updateGods(Gods message) {
        activeGods.add(message);
    }

    @Override
    public void updatePosition(Pair<Square, Square> message) {
        if (position.equals(message.getValue0())){
            setPosition(message.getValue1());
        }
    }
}
