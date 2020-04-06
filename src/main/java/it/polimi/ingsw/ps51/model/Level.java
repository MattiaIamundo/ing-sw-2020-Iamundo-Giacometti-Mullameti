package it.polimi.ingsw.ps51.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The possible levels that a square can have, listed in the order they must be built
 */
public enum Level {
    GROUND,
    FIRST,
    SECOND,
    THIRD,
    DOME;

    private static final Map<Integer, Level> LEVEL_MAP = new HashMap<>();

    static {
        for (Level level : values()){
            LEVEL_MAP.put(level.ordinal(), level);
        }
    }

    public static Level getByValue(Integer value){
        return LEVEL_MAP.get(value);
    }
}
