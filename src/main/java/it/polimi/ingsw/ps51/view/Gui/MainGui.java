package it.polimi.ingsw.ps51.view.Gui;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.ColorChoice;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.WorkerColor;
import it.polimi.ingsw.ps51.utility.MessageHandler;
import it.polimi.ingsw.ps51.view.Supporter;

import java.io.IOException;

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
                        case "COLOR":
                            System.out.println("color");
                            gui.chooseColor();
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
                            gui.askBuild();
                            break;
                        case "MAP":
                            gui.updateMap();
                            break;
                        case "DECISION" :
                            gui.makeDecision();
                            break;
                        case "ACK":
                            gui.ack();
                            break;
                        case "UNSUCCESSFULOPERATION":
                            gui.unsuccessfulOperation();
                            break;
                        case "GAME_IS_STARTING":
                            gui.gameIsStarting();
                            break;
                        case "TURN_IS_END":
                            gui.turnIsEnd();
                            break;
                        case "WIN":
                            gui.winGame();
                            break;
                        case "LOSE":
                            gui.loseGame();
                            break;
                        case "ROOM":
                            gui.outOfRoom();
                            isFinish = true;
                            break;
                        case "DISCONNECT":
                            gui.disconnectGame();
                            isFinish = true;
                            break;
                        case "END":
                            gui.endGame();
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
