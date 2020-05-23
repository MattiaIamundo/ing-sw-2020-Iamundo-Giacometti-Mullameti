package it.polimi.ingsw.ps51.view;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.Observer;
import org.javatuples.Pair;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

abstract public class Supporter extends Observable<EventForServer> implements Runnable, Observer<EventForClient> {


    private String typeOfEvent;
    ArrayBlockingQueue<EventForClient> events;
    private int godsNum;
    private List<Gods> availableGods;
    private int workerNum;
    private List<Worker> validChoicesWorkers;
    private List<Coordinates> validChoicesMoves;
    private List<Pair<Coordinates, List<Level>>> validChoicesBuild;
    private Map map;
    private List<Worker> workers;
    private String decision;
    private String ack;
    private List<Pair<String, Gods>> chosenGods;
    private List<Color> availableColors;

    public Supporter() {
        typeOfEvent = "DEFAULT";
        events = new ArrayBlockingQueue<>(20);
        godsNum = 0;
        availableGods = new ArrayList<>();
        workerNum = 0;
        validChoicesWorkers = new ArrayList<>();
        validChoicesMoves = new ArrayList<>();
        validChoicesBuild = new ArrayList<>();
        map = new Map();
        workers = new ArrayList<>();
        decision = "";
        ack = "";
        chosenGods = new ArrayList<>();
        availableColors = new ArrayList<>();
    }

    public void setTypeOfEvent(String typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

    public String getTypeOfEvent() {
        return typeOfEvent;
    }

    public synchronized ArrayBlockingQueue<EventForClient> getEvents() {
        return this.events;
    }

    public int getGodsNum (){return godsNum;}
    public void setGodsNum(int godsNum){ this.godsNum = godsNum;}


    public List<Gods> getAvailableGods(){return availableGods;}
    public void setAvailableGods(List<Gods> availableGods){this.availableGods = availableGods;}

    public int getWorkerNum(){return workerNum;}
    public void setWorkerNum(int workerNum){this.workerNum = workerNum;}

    public List<Worker> getValidChoicesWorkers(){return validChoicesWorkers;}
    public void setValidChoicesWorkers(List<Worker> validChoicesWorkers){this.validChoicesWorkers = validChoicesWorkers;}

    public List<Coordinates> getValidChoicesMoves(){return validChoicesMoves;}
    public void setValidChoicesMoves(List<Coordinates> validChoicesMoves){this.validChoicesMoves = validChoicesMoves;}

    public List<Pair<Coordinates, List<Level>>> getValidChoicesBuild(){return validChoicesBuild;}
    public void setValidChoicesBuild(List<Pair<Coordinates, List<Level>>> validChoicesBuild){this.validChoicesBuild=validChoicesBuild;}

    public Map getMap(){return map;}
    public void setMap(Map map){this.map = map;}

    public List<Worker> getWorkers(){return workers;}
    public void setWorkers(List<Worker> workers){this.workers = workers;}

    public String getDecision(){return decision;}
    public void setDecision(String decision){this.decision = decision;}

    public String getOperationConfirmed(){return ack;}
    public void setOperationConfirmed(String ack){this.ack=ack;}

    public List<Pair<String, Gods>> getChosenGods() {
        return chosenGods;
    }
    public void setChosenGods(List<Pair<String, Gods>> chosenGods){this.chosenGods=chosenGods;}

    public List<Color> getAvailableColors() {
        return this.availableColors;
    }
    public void setAvailableColors(List<Color> availableColors) {
        this.availableColors = availableColors;
    }
}
