package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.model.gods.opponent_move_manager.Gods;

public class Athena extends CommonAction {


    @Override
    protected void notify(Gods message) {
        super.notify(Gods.ATHENA);
    }
}
