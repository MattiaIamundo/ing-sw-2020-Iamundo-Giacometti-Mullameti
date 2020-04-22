package it.polimi.ingsw.ps51.model.gods;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.utility.Observer;

class Stub implements Observer<EventForClient> {

    protected EventForClient event;

    @Override
    public void update(EventForClient message) {
        event = message;
    }
}
