package it.polimi.ingsw.ps51.exceptions;

import it.polimi.ingsw.ps51.model.Coordinates;

/**
 * This exception is thrown when the {@link Coordinates} inserted are out the boundaries of the map
 */
public class OutOfMapException extends Exception{
    final Integer x;
    final Integer y;

    /**
     * Constructor
     * @param x the x-axis coordinate
     * @param y the y-axis coordinate
     */
    public OutOfMapException(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

}
