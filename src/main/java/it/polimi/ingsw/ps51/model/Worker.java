package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.Gods;
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

    /**
     * The constructor of the class
     * @deprecated This constructor doesn't support the immediate collocation of the worker on the map
     * Use {@link #Worker(String, Square)}
     * @param namePlayer represents the nickname of the Player
     */
    @Deprecated(since = "17/04/2020", forRemoval = true)
    public  Worker (String namePlayer){
        this.namePlayer=namePlayer;
    }

    /**
     * Creates a new worker and positions it on the map on the given square
     * @param namePlayer represents the nickname of the Player
     * @param position the square where to collocates the worker
     */
    public Worker (String namePlayer, Square position){
        this.namePlayer = namePlayer;
        this.position = position;
        position.setPresentWorker(true);
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
    public boolean isInWinningCondition(){
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

    public void removeGod(Card card){
        activeGods.remove(Gods.getGodFromCard(card));
    }

    @Override
    public void updatePosition(Pair<Square, Square> message) {
        if (position.equals(message.getValue0())){
            setPosition(message.getValue1());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }

        Worker worker = (Worker) obj;
        return ((this.namePlayer.equals(worker.namePlayer)) && (this.inWinningCondition == worker.inWinningCondition) &&
                (this.position.equals(worker.position)) && (this.activeGods.equals(worker.activeGods)));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Worker worker = (Worker) super.clone();
        worker.position = (Square) this.position.clone();
        worker.activeGods = new ArrayList<>(this.activeGods);
        return worker;
    }
}
