package it.polimi.ingsw.ps51.utility;

public class SquareChangeNotifier {

    private SquareObserver observer;

    public void addObserver(SquareObserver observer){
        this.observer = observer;
    }

    public void notifyChange(){
        observer.mapUpdated();
    }
}
