package it.polimi.ingsw.ps51.model;

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

    public void setActualPlayer(Player newActualPlayer){
        actualPlayer = newActualPlayer;
    }
}
