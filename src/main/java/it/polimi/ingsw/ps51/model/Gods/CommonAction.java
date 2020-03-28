package it.polimi.ingsw.ps51.model.Gods;

import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Player;
import java.util.List;

abstract class CommonAction implements Card {
    private boolean useageOfGod;

    public void askForTheUsage(){

    }

    public List<Integer> checkMove(Player player , Map map){
        return null;
    }

    public void checkBuild(Player player , Map map){

    }
}
