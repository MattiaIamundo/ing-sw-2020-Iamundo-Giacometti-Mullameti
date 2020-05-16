package it.polimi.ingsw.ps51;

import it.polimi.ingsw.ps51.view.Cli;
import it.polimi.ingsw.ps51.view.Printer;

public class testMain {
    public static void main(String[] args) {
        Cli cli = new Cli();
        cli.undo();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cli.undo();
    }
}
