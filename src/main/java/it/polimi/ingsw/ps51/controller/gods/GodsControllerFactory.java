package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.Gods;

public class GodsControllerFactory {

    /**
     * It's a factory method that return a god's controller based on the god given in input, the other parameter
     * are needed to initialize the controller
     * @param god the God of which we want the controller
     * @param player the player that has chose the god
     * @param map the game map
     * @param card the god's card
     * @return the controller associated to the give god
     */
    public static GodController getController(Gods god, Player player, Map map, Card card){
        switch (god){
            case DEMETER:
                return new DemeterController(card, map, player);
            case PROMETHEUS:
                return new PrometheusController(card, map, player);
            case ARTEMIS:
                return new ArtemisController(card, map, player);
            case HESTIA:
                return new HestiaController(card, map, player);
            case POSEIDON:
                return new PoseidonController(card, map, player);
            default:
                return new NormalGodsController(card, map, player);
        }
    }
}
