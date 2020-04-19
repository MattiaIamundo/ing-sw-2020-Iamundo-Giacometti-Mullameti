package it.polimi.ingsw.ps51.events.events_for_server;

public class Disconnection {

    String playerDisconnected;

    public Disconnection(String playerDisconnected) {
        this.playerDisconnected = playerDisconnected;
    }

    public String getPlayerDisconnected() {
        return playerDisconnected;
    }
}
