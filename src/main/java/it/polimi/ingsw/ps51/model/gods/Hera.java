package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Player;
import it.polimi.ingsw.ps51.model.Square;
import it.polimi.ingsw.ps51.model.Worker;

public class Hera extends CommonAction{

    @Override
    public synchronized void move(Player player, Worker worker, Square position, Map map) {
        super.move(player, worker, position, map);
        notify(Gods.HERA);
    }
}
