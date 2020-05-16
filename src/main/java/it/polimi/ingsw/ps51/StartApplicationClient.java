package it.polimi.ingsw.ps51;

import it.polimi.ingsw.ps51.network.client.Client;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents the first class to be launched from the client,
 * it admits the user to start the thread {@link Client}
 * @author Luca Giacometti
 */
public class StartApplicationClient {

    public static void main(String[] args) {
        System.out.println("Welcome to our SANTORINI game!");
        int typeOfSupporter = 0;
        String url = "127.0.0.1";

        if (args.length == 2) {
            url = args[0];

            if (Integer.parseInt(args[1]) == 0 || Integer.parseInt(args[1]) == 1){
                typeOfSupporter = Integer.parseInt(args[1]);
            }else {
                typeOfSupporter = 0;
            }
        } else {
            System.out.println("Do you want to use a Cli or a Gui");
            System.out.println("Press respectively 0 or 1");

            Scanner scanner = new Scanner(System.in);
            boolean ok = false;

            while(!ok) {
                try {
                    typeOfSupporter = scanner.nextInt();
                    if (typeOfSupporter == 0 || typeOfSupporter == 1)
                        ok = true;
                    else
                        System.out.println("Insert 0 for Cli or 1 for Gui...");

                } catch (InputMismatchException e) {
                    System.out.println("Please, insert 0 or 1, no a string!");
                    scanner.nextLine();
                }
            }
        }

        Client client = new Client(typeOfSupporter, url, 20000, 20000);
        Thread t = new Thread(client);
        t.start();
    }
}
