package it.polimi.ingsw.ps51;

import it.polimi.ingsw.ps51.network.socket.Client;

public class StartApplicationClient {

    public static void main(String[] args) {
        System.out.println("Welcome to out Santorini game!");
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
        Client client = new Client(0);
        Thread t = new Thread(client);
        t.start();
    }
}
