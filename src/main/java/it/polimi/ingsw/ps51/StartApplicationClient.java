package it.polimi.ingsw.ps51;

import it.polimi.ingsw.ps51.network.client.Client;

/**
 * This class represents the first class to be launched from the client,
 * it admits the user to start the thread {@link Client}
 * @author Luca Giacometti
 */
public class StartApplicationClient {

    public static void main(String[] args) {
        System.out.println("Welcome to our SANTORINI game!");
        /*
        System.out.println("Do you want to use a Cli or a Gui");
        System.out.println("Press respectively 0 or 1");

        Scanner scanner = new Scanner(System.in);
        boolean ok = false;
        Integer i = 0

        while(!ok) {
            try {
                i = scanner.nextInt();
                if (i != 0 && i != 1) new NoGoodNumberException();
                ok = true;
            } catch (InputMismatchException e) {
                System.out.println("Please, insert 0 or 1, no a string!");
            } catch (NoGoodNumberException n) {
                System.out.println("Please, insert 0 or 1, no an another number!");
            }
        }
        */
        int typeOfSupporter;
        String url;

        try {
            if (args[1].equals("1"))
                typeOfSupporter = 1;
            else {
                if (!args[1].equals("0") )
                    System.out.println("The number digested is NOT VALID, so there will be set a cli");
                typeOfSupporter = 0;
            }

            if (args[0].isEmpty() || args[0].contains(" "))
                url = "127.0.0.1";
            else
                url = args[0];
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("There are one or two NOT VALID parameters!");
            System.out.println("Therefore, the client will be started with localhost and cli");
            typeOfSupporter = 0;
            url = "127.0.0.1";
        }

        Client client = new Client(typeOfSupporter, url, 20000, 20000);
        Thread t = new Thread(client);
        t.start();
    }
}
