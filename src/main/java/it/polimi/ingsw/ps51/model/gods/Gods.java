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
        CARD_TO_GODS.put(new Apollo().getGodName(), APOLLO);
        CARD_TO_GODS.put(new Artemis().getGodName(), ARTEMIS);
        CARD_TO_GODS.put(new Athena().getGodName(), ATHENA);
        CARD_TO_GODS.put(new Atlas().getGodName(), ATLAS);
        CARD_TO_GODS.put(new Demeter().getGodName(), DEMETER);
        CARD_TO_GODS.put(new Hephaestus().getGodName(), HEPHAESTUS);
        CARD_TO_GODS.put(new Minotaur().getGodName(), MINOTAUR);
        CARD_TO_GODS.put(new Pan().getGodName(), PAN);
        CARD_TO_GODS.put(new Prometheus().getGodName(), PROMETHEUS);
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
        return CARD_TO_GODS.get(((CommonAction) card).getGodName());
    }

    /**
     * Given the name of a god return the corresponding enum
     * @param name the God's name
     * @return the enum corresponding to the given god's name
     */
    public static Gods getGodFromString(String name){
        String normalizedName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return CARD_TO_GODS.get(normalizedName);
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
