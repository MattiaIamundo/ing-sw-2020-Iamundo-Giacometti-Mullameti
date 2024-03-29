package it.polimi.ingsw.ps51.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * this class implements the Observable
 * @param <T> the type of the parameter
 * @author Luca Giacometti
 */
public class Observable <T> {
    //this is the list of the observers
    private ArrayList <Observer<T>> observers = new ArrayList<>();

    /**
     * To add an observer to the list
     * @param newObserver is the new observer to insert in the list
     */
    public void addObserver(Observer newObserver){
        //stopping the variable players
        synchronized (observers) {

            observers.add(newObserver);
        }

    }

    /**
     * To remove an observer form the list
     * @param removeObserver is the observer to remove
     */
    public void removeObserver(Observer removeObserver){
        //stopping the variable players
        synchronized (observers) {

            observers.remove(removeObserver);
        }

    }

    public List<Observer<T>> copyObservers(){
        return observers;
    }

    /**
     * To notify the observers about the object T
     * @param message the variable which generates the update
     */
    public void notify(T message) {
        //stopping the variable players
        synchronized (observers) {

            for(Observer<T> observer : observers){
                observer.update(message);
            }

        }

    }

}