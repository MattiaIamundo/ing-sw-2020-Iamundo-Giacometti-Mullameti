package it.polimi.ingsw.ps51.model;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_client.MapUpdate;
import it.polimi.ingsw.ps51.model.gods.CommonAction;
import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.utility.Observable;
import it.polimi.ingsw.ps51.utility.SquareObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This is the main class of the model and represents the actual sate of the game
 */
public class Playground extends Observable<EventForClient> implements SquareObserver {

    private Map boardMap;
    private List<Player> players;
    private MemoryTurn log ;
    private Integer actualTurn;
    private Player actualPlayer;
    private final static Logger logger = Logger.getLogger(Playground.class.getName());

    public Playground(List<Player> players){
        boardMap = new Map();
        boardMap.addObservers(this);
        this.players = new ArrayList<>(players);
        log = new MemoryTurn();
        actualTurn = 0;
    }

    public List<Player> getPlayers(){
        return new ArrayList<>(players);
    }

    /**
     * Retrieve a player from his nickname
     * @param player the nickname of the searched player
     * @return the player whose nickname was given
     */
    public Player getPlayer(String player){
        for(Player player1 : players){
            if (player1.getNickname().equals(player)){
                return player1;
            }
        }
        return null;
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

    /**
     * Retrieve who is the player that must be play his turn after the actual player, the list of players is cycled and
     * at its end restart from the first element
     * @return the next player
     */
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
        setActualTurn();
        return actualPlayer;
    }

    /**
     * Remove a player from the playground, this could happen in a 3-player game, where a player lose but the other two
     * continue to play the game
     * @param player the player that lost the match
     */
    public synchronized void removePlayer(Player player){
        for (Worker worker : player.getWorkers()){
            worker.getPosition().setPresentWorker(false);
        }

        for (Player player1 : players){
            if (!player1.equals(player)){
                for (Worker worker : player.getWorkers()){
                    ((CommonAction) player1.getGod()).removeObserver(worker);
                }
            }
        }

        players.remove(player);
    }

    /**
     * The method is called by a square that noticed a change in it's status and consequently send a {@link MapUpdate}
     * message to all the players to inform them about a change in the map's configuration
     */
    @Override
    public void mapUpdated() {
        List<Worker> workers = new ArrayList<>();

        try {
            for (Player player : players){
                for (Worker worker : player.getWorkers()){
                    workers.add((Worker) worker.clone());
                }
            }
            notify(new MapUpdate(((Map) boardMap.clone()), workers));
        } catch (CloneNotSupportedException e) {
            logger.severe("Impossible to clone worker");
        }
    }
}
