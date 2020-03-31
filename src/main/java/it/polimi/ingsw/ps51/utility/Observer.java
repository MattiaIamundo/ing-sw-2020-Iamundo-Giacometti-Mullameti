package it.polimi.ingsw.ps51.utility;

/**
 * This class implements the Observer for the Observer/Observable Pattern
 * @param <T> the type of the parameter
 * @author Luca Giacometti
 */
public interface Observer <T> {

    /**
     * Represents the update to do about the object T
     * @param message the object which have to be updated
     */
    void update(T message);
}
