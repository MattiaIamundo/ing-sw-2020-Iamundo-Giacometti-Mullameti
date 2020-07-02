package it.polimi.ingsw.ps51.view.Gui;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.EventForServer;
import it.polimi.ingsw.ps51.events.events_for_server.FirstPlayerChoice;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.utility.MessageHandler;
import it.polimi.ingsw.ps51.view.Supporter;

import javax.swing.*;
import java.io.IOException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainGui extends Supporter {

    Gui gui;
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

        SwingUtilities.invokeLater(() -> gui.launch());
        while(!isFinish) {

            Future<String> stringFuture = mh.handleTheFuture();
            ok = false;
            while (!ok) {
                ok = true;
                try {
                    setTypeOfEvent(stringFuture.get(1, TimeUnit.SECONDS));

                    switch (getTypeOfEvent()) {

                        case "NICKNAME":
                            SwingUtilities.invokeLater(() -> gui.logIn());
                            break;
                        case"NUMBEROFPLAYER" :
                            SwingUtilities.invokeLater(() -> gui.numberOfPlayers());
                            break;
                        case "GODSDECK":
                            SwingUtilities.invokeLater(() -> gui.chooseGodsDeck());
                            break;
                        case "GOD" :
                            SwingUtilities.invokeLater(() -> gui.chooseGodsPlayers());
                            break;
                        case "FIRSTPLAYER":
                            SwingUtilities.invokeLater(() -> gui.chooseFirstPlayer());

                            break;
                        case "COLOR":
                            SwingUtilities.invokeLater(() -> gui.chooseColor());
                            break;
                        case "WORKERPOSITION":
                            SwingUtilities.invokeLater(() -> gui.placeWorkers());
                            break;
                        case "WORKER":
                            SwingUtilities.invokeLater(() -> gui.chooseWorker());
                            break;
                        case "MOVE":
                            SwingUtilities.invokeLater(() -> gui.askMove());
                            break;
                        case "BUILD":
                            SwingUtilities.invokeLater(() -> gui.askBuild());
                            break;
                        case "MAP":
                            SwingUtilities.invokeLater(() -> {
                                try {
                                    gui.updateMap();
                                } catch (OutOfMapException e) {
                                    e.printStackTrace();
                                }
                            });
                            break;
                        case "DECISION" :
                            SwingUtilities.invokeLater(() -> gui.makeDecision());
                            break;
                        case "ACK":
                            SwingUtilities.invokeLater(() -> gui.ack());
                            break;
                        case "UNSUCCESSFULOPERATION":
                            SwingUtilities.invokeLater(() -> gui.unsuccessfulOperation());
                            break;
                        case "GAME_IS_STARTING":
                            SwingUtilities.invokeLater(() -> gui.gameIsStarting());
                            break;
                        case "TURN_IS_END":
                            SwingUtilities.invokeLater(() -> gui.turnIsEnd());
                            break;
                        case "WIN":
                            SwingUtilities.invokeLater(() -> {
                                try {
                                    gui.winGame();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            break;
                        case "LOSE":
                            SwingUtilities.invokeLater(() -> gui.loseGame());
                            break;
                        case "ROOM":
                            SwingUtilities.invokeLater(() -> gui.outOfRoom());
                            isFinish = true;
                            break;
                        case "DISCONNECTION":
                            SwingUtilities.invokeLater(() -> gui.disconnectGame());
                            isFinish = true;
                            break;
                        case "END":
                            SwingUtilities.invokeLater(() -> gui.endGame());
                            isFinish = true;
                            break;
                        default:
                            ok = false;
                            break;

                    }
                } catch (InterruptedException | TimeoutException | ExecutionException e) {
                    ok = false;
                }
            }
        }
        this.mh.getEx().shutdown();
    }
}
