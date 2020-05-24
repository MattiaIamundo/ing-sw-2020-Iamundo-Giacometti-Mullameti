package it.polimi.ingsw.ps51.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum WorkerColor {
    RED,
    BLUE,
    WHITE;

    public static final List<WorkerColor> toList(){
        return new ArrayList<>(Arrays.asList(WorkerColor.values()));
    }
}
