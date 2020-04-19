package it.polimi.ingsw.ps51.model.gods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The enum include all the God supported by the game, it has a map that associate to each {@code Card} the associated god
 * in the enumeration. This Enum also has a list of the god whose effect act on the opponent's turn
 */
public enum Gods {
    APOLLO,
    ARTEMIS,
    ATHENA,
    ATLAS,
    DEMETER,
    HEPHAESTUS,
    MINOTAUR,
    PAN,
    PROMETHEUS;

    private static final List<String> opponetsTurnEffect = new ArrayList<>();
    private static final Map<String, Gods> CARD_TO_GODS = new HashMap<>();

    static {
        CARD_TO_GODS.put(Apollo.class.getName(), APOLLO);
        CARD_TO_GODS.put(Artemis.class.getName(), ARTEMIS);
        CARD_TO_GODS.put(Athena.class.getName(), ATHENA);
        CARD_TO_GODS.put(Atlas.class.getName(), ATLAS);
        CARD_TO_GODS.put(Demeter.class.getName(), DEMETER);
        CARD_TO_GODS.put(Hephaestus.class.getName(), HEPHAESTUS);
        CARD_TO_GODS.put(Minotaur.class.getName(), MINOTAUR);
        CARD_TO_GODS.put(Pan.class.getName(), PAN);
        CARD_TO_GODS.put(Prometheus.class.getName(), PROMETHEUS);
    }

    static {
        opponetsTurnEffect.add(Athena.class.getName());
    }

    /**
     * Given a card return the corresponding element in the enumeration
     * @param card the god's card for which is requested the equivalent value in the gods enumeration
     * @return the value in the enumeration that represent the given card
     */
    public static Gods getGodFromCard(Card card){
        return CARD_TO_GODS.get(card.getClass().getName());
    }

    /**
     * The method check if the given card correspond to a God whose effect act on the opponent's turn
     * @param card the god's card to verify
     * @return true if the given card correspond to a god that act on the opponent's turn, false otherwise
     */
    public static boolean isOpponentTurnEffect(Card card){
        return opponetsTurnEffect.contains(card.getClass().getName());
    }
}
