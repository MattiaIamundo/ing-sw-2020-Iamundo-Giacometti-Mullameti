package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.*;
import org.javatuples.Pair;

import java.util.List;

/**
 * This interface express the mandatory methods for each God card whom must implements this interface directly
 * or in an indirect way through the abstract class that them extends
 */
public interface Card {

    /**
     * check where the selected worker can be moved
     * @param player the player who is playing his game turn
     * @param worker the selected worker
     * @param map the game map
     * @return a List of the Coordinates where the selected worker can be moved
     */
    List<Coordinates> checkMoves(Player player, Worker worker, Map map);

    /**
     * moves the selected worker in the desired position
     * @param player the player who is playing his game turn
     * @param worker the selected worker
     * @param position the chosen position, must be one of those contained in the List returned by checkMoves
     * @param map the game map
     */
    void move(Player player, Worker worker, Square position, Map map);

    /**
     * checks where the selected worker can build and at which height
     * @param worker the selected worker
     * @param map the game map
     * @return a List of Pair, each Pair contains a Coordinates, where the worker can build, and a Level that indicates
     *         which level the worker can build
     */
    List<Pair<Coordinates, List<Level>>> checkBuild(Worker worker, Map map);

    /**
     * build a level on the desired square
     * @param worker the selected worker
     * @param position the position where the worker chose to build
     * @param level the level that the worker chose to build
     * @param map the game map
     */
    void build(Worker worker, Square position, Level level, Map map);


}
