package it.polimi.ingsw.ps51;

import it.polimi.ingsw.ps51.network.client.Client;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
        int port = 20000;

        if (args.length % 2 == 0) {
            List<Pair<String, String>> inputArgument = new ArrayList<>();
            for (int i = 0; i < args.length; i++){
                String command = args[i];
                i++;
                String value = args[i];
                inputArgument.add(new Pair<>(command, value));
            }
            for (Pair<String, String> pair : inputArgument){
                switch (pair.getValue0()){
                    case "-ip":
                        url = pair.getValue1();
                        break;
                    case "-p":
                        port = Integer.parseInt(pair.getValue1());
                        break;
                    case "-i":
                        typeOfSupporter = Integer.parseInt(pair.getValue1());
                        break;
                    default:
                        System.out.println("\u001B[0;31m"+pair.getValue1()+" isn't a valid command\u001B[0;37m");
                }
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

        Client client = new Client(typeOfSupporter, url, port, 20000);
        Thread t = new Thread(client);
        t.start();
    }
}
