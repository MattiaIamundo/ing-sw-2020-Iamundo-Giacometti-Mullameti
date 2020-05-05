package it.polimi.ingsw.ps51.controller.gods;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.gods.Card;
import it.polimi.ingsw.ps51.model.gods.Gods;

public class GodsControllerFactory {

    public static GodController getController(Gods god, Player player, Map map, Card card){
        switch (god){
            case DEMETER:
                return new DemeterController(card, map, player);
            case PROMETHEUS:
                return new PrometheusController(card, map, player);
            case ARTEMIS:
                return new ArtemisController(card, map, player);
            default:
                return new NormalGodsController(card, map, player);
        }
    }
}
