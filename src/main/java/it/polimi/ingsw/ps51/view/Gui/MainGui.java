package it.polimi.ingsw.ps51.view.Gui;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Worker;
import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.utility.MessageHandler;
import it.polimi.ingsw.ps51.view.Printer;
import it.polimi.ingsw.ps51.view.Supporter;
import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainGui  extends Supporter {

    private Gui gui;
    private boolean ok;
    boolean isFinish;
    MessageHandler mh;


    public MainGui() {
        super();
        gui = new Gui(this);
        ok = false;
        isFinish = false;
        mh = new MessageHandler(this);

    }

    @Override
    public void update(EventForClient message) {
        getEvents().add(message);
    }

    @Override
    public void run() {


        while(!isFinish) {

            Future<String> stringFuture = mh.handleTheFuture();
            ok = false;
            while (!ok) {
                ok = true;
                try {
                    setTypeOfEvent(stringFuture.get(1, TimeUnit.SECONDS));

                    switch (getTypeOfEvent()) {

                        case "NICKNAME":
                            gui.logIn();
                            break;
                        case"NUMBEROFPLAYER" :
                            gui.numberOfPlayers();
                            break;
                        case "GODSDECK":
                            gui.chooseGodsDeck();
                            break;

                        case "GOD" :
                            gui.chooseGodsPlayers();
                            break;
                        case "WORKERPOSITION" :
                            gui.placeWorkers();
                            break;

                        case "WORKER" :
                            gui.chooseWorker();
                            break;
                        case "MOVE":
                            gui.askMove();
                            break;
                        case "BUILD":
                            
                            break;
                        case "MAP":
                            gui.updateMap();
                            break;
                        case "DECISION" :

                            break;
                        case "ACK":

                            break;
                        case "UNSUCCESSFULOPERATION":

                            break;
                        case "GAME_IS_STARTING":

                            break;
                        case "TURN_IS_END":

                            break;
                        case "WIN":

                            break;
                        case "LOSE":

                            break;
                        case "ROOM":

                            isFinish = true;
                            break;
                        case "DISCONNECT":

                            isFinish = true;
                            break;
                        case "END":

                            isFinish = true;
                            break;
                        default:
                            ok = false;
                            break;

                    }
                } catch (InterruptedException | TimeoutException | OutOfMapException | ExecutionException | IOException e) {
                    // e.printStackTrace();
                    ok=false;
                }
            }
        }
        System.exit(0);
    }
}
