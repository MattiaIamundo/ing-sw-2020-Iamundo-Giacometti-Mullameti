package it.polimi.ingsw.ps51.model.gods;

/**
 * This class implement a factory pattern needed to creates the correct {@code Card} given the god, as an enum contained
 * in {@code gods}, chosen by the player
 */
public class CardFactory {

    /**
     * The method return a new Card based on the given god
     * @param god the enum representing the God chosen by the player
     * @return a new Card that implement the corrects method for the selected God
     */
    public static Card getCard(Gods god){
        switch (god){
            case APOLLO:
                return new Apollo();
            case ATLAS:
                return new Atlas();
            case ATHENA:
                return new Athena();
            case PROMETHEUS:
                return new Prometheus();
            case DEMETER:
                return new Demeter();
            case PAN:
                return new Pan();
            case ARTEMIS:
                return new Artemis();
            case MINOTAUR:
                return new Minotaur();
            case HEPHAESTUS:
                return new Hephaestus();
            case ZEUS:
                return new Zeus();
            case HESTIA:
                return new Hestia();
            case HERA:
                return new Hera();
            case POSEIDON:
                return new Poseidon();
            case TRITON:
                return new Triton();
            default:
                return null;
        }
    }
}
