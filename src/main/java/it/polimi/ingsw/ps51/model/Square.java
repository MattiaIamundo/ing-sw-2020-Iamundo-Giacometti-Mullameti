package it.polimi.ingsw.ps51.model;

import java.io.Serializable;

/**
 * Represent a single square of the game map
 * @author Mattia Iamundo
 */
public class Square implements Serializable, Cloneable {

    private Level level ;
    private Worker presentWorker;
    private Coordinates coordinates ;


    
    public Square (Coordinates coordinates){
        level = Level.GROUND;
        presentWorker = null;
        this.coordinates = coordinates;
    }

    public Level getLevel(){
        return level;
    }
    public void setLevel(Level newLevel){
        level = newLevel;
    }

    public Worker getPresentWorker(){
        return presentWorker;
    }

    /***
     * This method check if a worker is present on his square
     * @return true if a worker is on this square, false otherwise
     */
    public boolean isPresentWorker(){
        return presentWorker != null;
    }

    public void setPresentWorker(Worker worker){
        presentWorker = worker;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    /**
     * verify if the square is free or not
     * @return true if on the square there isn't a worker nor a dome
     */
    public boolean isFreeSquare(){
        return !level.equals(Level.DOME) && !isPresentWorker();
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }

        if (o == null || o.getClass() != this.getClass()){
            return false;
        }

        Square square = (Square) o;
        return (square.presentWorker == this.presentWorker && square.coordinates.equals(this.coordinates)
                && square.level.equals(this.level));
    }

}
