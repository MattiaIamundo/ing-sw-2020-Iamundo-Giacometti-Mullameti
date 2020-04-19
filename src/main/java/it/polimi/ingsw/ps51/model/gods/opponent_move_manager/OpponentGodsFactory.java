package it.polimi.ingsw.ps51.model.gods.opponent_move_manager;

import it.polimi.ingsw.ps51.model.gods.Gods;

/**
 * Factory class that creates the correct manager to apply the "Opponent's turn" effect
 */
public class OpponentGodsFactory {
    public OpponentGodsFactory() {}

    /**
     * @param god the desired god's manager
     * @return the manager associated to the inserted god
     */
    public OpponentTurnGodsManager getGod(Gods god){
        switch (god){
            case ATHENA:
                return new OpponentAthena();
        }
        return null;
    }
}
