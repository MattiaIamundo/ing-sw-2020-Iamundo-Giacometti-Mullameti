package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.model.gods.CommonAction;
import it.polimi.ingsw.ps51.model.gods.Gods;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main class of the model and represents the actual sate of the game
 */
public class Playground {

    private Map boardMap;
    private List<Player> players;
    private MemoryTurn log ;
    private Integer actualTurn;
    private Player actualPlayer;

    public Playground(List<Player> players){
        boardMap = new Map();
        this.players = players;
        log = new MemoryTurn();
        actualTurn = 0;
    }

    public List<Player> getPlayers(){
        return new ArrayList<>(players);
    }

    public Map getBoardMap(){
        return boardMap;
    }

    public MemoryTurn getLog(){
        return log;
    }

    public Integer getActualTurn(){
        return actualTurn;
    }

    /**
     * Increments the counters that associate a unique number to each turn
     */
    public void setActualTurn(){
        actualTurn++;
    }

    public Player getActualPlayer(){
        return actualPlayer;
    }

    public void setActualPlayer(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    public Player getNextPlayer(){
        int index = players.indexOf(actualPlayer);
        index++;
        actualPlayer = players.get((index % players.size()));
        for (Player player : players){
            if (!player.equals(actualPlayer)){
                for (Worker worker : player.getWorkers()){
                    worker.removeGod(actualPlayer.getGod());
                }
            }
        }

        return actualPlayer;
    }

    public synchronized void removePlayer(Player player){
        for (Worker worker : player.getWorkers()){
            worker.getPosition().setPresentWorker(false);
        }

        for (Player player1 : players){
            if (!player1.equals(player) && Gods.isOpponentTurnEffect(player1.getGod())){
                for (Worker worker : player.getWorkers()){
                    ((CommonAction) player1.getGod()).removeObserver(worker);
                }
            }
        }

        players.remove(player);
    }
}
