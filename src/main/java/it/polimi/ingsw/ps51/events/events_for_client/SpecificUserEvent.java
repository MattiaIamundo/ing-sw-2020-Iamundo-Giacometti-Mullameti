package it.polimi.ingsw.ps51.events.events_for_client;

/**
 * Event that must be send to a specific player
 */
public abstract class SpecificUserEvent {

    private String receiver;

    /**
     * @param receiver the nickname of the player toward whom the event must be sent
     */
    public SpecificUserEvent(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }
}
