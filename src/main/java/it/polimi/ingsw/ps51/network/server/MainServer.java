package it.polimi.ingsw.ps51.network.server;

import it.polimi.ingsw.ps51.controller.Game;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.Playground;

import java.util.*;

/**
 * This class represents the central server which handles:
 * the creation of the room
 * the setting of the controller game
 * the setting of the model
 * And here there are all the nicknames of all the players
 * and all the references between the nicknames and the connections used by the clients
 * @author Luca Giacometti
 */
public class MainServer implements Runnable{

    private Integer numberOfPlayer;
    private final Object objectToSynchronized = new Object();
    List<String> allNicknamesOfPlayers;
    List<String> actualNicknameInSearchOfRoom;
    Map<String,ServerInterface> mapOfNicknameAndServerInterface;

    /**
     * Constructor
     */
    public MainServer() {
        this.numberOfPlayer = 2;
        this.allNicknamesOfPlayers = new ArrayList<>();
        this.actualNicknameInSearchOfRoom = new ArrayList<>();
        this.mapOfNicknameAndServerInterface = new HashMap<>();
    }

    /**
     * Getter of the object to be synchronized before every operation
     * by the other classes
     * @return the final object to be synchronized
     */
    public synchronized Object getObjectToSynchronized() {
        return objectToSynchronized;
    }

    /**
     * Getter of the list of the nicknames which are waiting to start a game
     * @return the list of nicknames waiting the beginning of the game
     */
    public synchronized List<String> getActualNicknameInSearchOfRoom() {
        return this.actualNicknameInSearchOfRoom;
    }

    /**
     * Getter of number of player
     * @return the actual number of this game
     */
    public synchronized Integer getNumberOfPlayer() {
        return numberOfPlayer;
    }

    /**
     * Getter of the all nicknames of players
     * @return the list which contains all the nicknames of players which are playing a game
     */
    public synchronized List<String> getAllNicknamesOfPlayers() {
        return allNicknamesOfPlayers;
    }

    /**
     * Setter of number of player
     * @param number the number of the player for the game which is processing
     */
    public void setNumberOfPlayer(Integer number) {
        this.numberOfPlayer = number;
    }

    /**
     * This method obtain a synchronize access to the list of nicknames
     * which are waiting to start a game, and to the number of player
     * to be processed before to start the game
     * @return true if the number of nicknames are the right number
     *          false if there are not enough nicknames
     */
    public synchronized boolean computeTheSizeOfList() {
        synchronized ( getObjectToSynchronized() ) {
            return getActualNicknameInSearchOfRoom().size() < getNumberOfPlayer();
        }
    }

    /**
     * This method adds the nickname choose by a client into the lists of nicknames
     * and create the link between the nickname and his connection
     * @param s the nickname of the client
     * @param serverInterface the connection which the client is using
     */
    public void addNickname(String s, ServerInterface serverInterface) {
        this.getActualNicknameInSearchOfRoom().add(s);
        this.getAllNicknamesOfPlayers().add(s);
        this.mapOfNicknameAndServerInterface.put(s,serverInterface);
    }

    /**
     * This method check if the nickname choose by the user is
     * already used or not inside the list of all nickname present
     * @param nickToCheck the nickname to be checked
     * @return true if the nickname is not already present
     *          false if it is already present
     */
    public synchronized boolean checkName(String nickToCheck) {
        for (String s : getAllNicknamesOfPlayers()) {
            if (s.equals(nickToCheck)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks in the list of nickname which are in search of
     * a game if the nickname passed as parameter is the first one or not
     * @param nickname the nickname of the user
     * @return true if it is the first of the list
     *          false if it is not
     */
    public synchronized boolean iMFirst(String nickname) {
        return getActualNicknameInSearchOfRoom().size() == 1 &&
                getActualNicknameInSearchOfRoom().get(0).equals(nickname);
    }

    /**
     * This method removes from all the list which contains it
     * @param nickname the nickname to remove from the server
     */
    public synchronized void removeNickname(String nickname) {
        allNicknamesOfPlayers.remove(nickname);
        actualNicknameInSearchOfRoom.remove(nickname);
        ServerInterface si = mapOfNicknameAndServerInterface.get(nickname);
        si.closeConnection();
        mapOfNicknameAndServerInterface.remove(nickname, si);
    }

    /**
     * When this thread is running, it waits the right number of players.
     * When the number of nicknames is right, it starts to set a new Room
     * coping the list of nicknames of the players and the link between the
     * nicknames and them connections.
     * After that, it initializes all the model and the controller
     * and it sets up the first observers.
     */
    @Override
    public void run() {

        while (computeTheSizeOfList()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized ( getObjectToSynchronized() ) {

            List<String> list = new ArrayList<>(this.actualNicknameInSearchOfRoom);
            Map<String,ServerInterface> stringServerInterfaceMap = new HashMap<>();

            for (String s : list) {
                stringServerInterfaceMap.put(s, this.mapOfNicknameAndServerInterface.get(s));
            }

            Room newRoom = new Room(list, stringServerInterfaceMap);

            List<Player> players = new ArrayList<>();

            for (String s : list) {
                this.mapOfNicknameAndServerInterface.get(s).pairWithRoom(newRoom);
                players.add(new Player(s));
            }

            Playground playground = new Playground(players);
            Game game = new Game(playground);

            game.addObserver(newRoom);
            newRoom.setGame(game);

            Thread t = new Thread(newRoom);
            t.start();

            this.actualNicknameInSearchOfRoom.clear();
            this.numberOfPlayer = 2;
        }
    }
}
