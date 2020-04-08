package it.polimi.ingsw.ps51.model;

import java.io.Serializable;

/**
 * Represent a single square of the game map
 * @author Mattia Iamundo
 */
public class Square implements Serializable, Cloneable {

    private Level level;
    private Coordinates coordinates;
    private boolean isPresentWorker;

    
    public Square (Coordinates coordinates){
        level = Level.GROUND;
        this.coordinates = coordinates;
        isPresentWorker = false;
    }

    public Level getLevel(){
        return level;
    }

    public void setLevel(Level newLevel){
        level = newLevel;
    }

    public boolean isPresentWorker() {
        return isPresentWorker;
    }

    public void setPresentWorker(boolean presentWorker) {
        isPresentWorker = presentWorker;
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
        return ((square.isPresentWorker == this.isPresentWorker) && square.coordinates.equals(this.coordinates)
                && square.level.equals(this.level));
    }

}
