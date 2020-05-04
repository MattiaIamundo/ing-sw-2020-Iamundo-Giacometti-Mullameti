package it.polimi.ingsw.ps51.view;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import it.polimi.ingsw.ps51.model.gods.Gods;
import org.fusesource.jansi.AnsiConsole;
import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Printer {

    public Printer(){
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
    }

    public void welcome(){

       println(colorToAnsi(Color.BLUE)+" ────      /\\     |\\    | ───── ─────  ──────   ──────   |  |\\    |  |");
       println(colorToAnsi(Color.BLUE)+"|         /  \\    | \\   |      |      |      | |      |  |  | \\   |  |");
       println(colorToAnsi(Color.BLUE)+" ────    /────\\   |  \\  |      |      |      |  ─────    |  |  \\  |  |");
       println(colorToAnsi(Color.BLUE)+"     |  /      \\  |   \\ |      |      |      | |     \\   |  |   \\ |  |");
       println(colorToAnsi(Color.BLUE)+" ────  /        \\ |    \\|      |       ──────  |      \\  |  |    \\|  |");
    }
    public void printDeck(){

        String line;
        int k;

        for(int j=0 ; j<3 ;j++) {
            for (int i = j*18; i <(j*18+6); i++) {
                if (i == j*18) {
                    for(int l=0 ; l<3 ; l++)
                        print(colorToAnsi(Color.BLUE) + "┌─────────────────────────────────────────────┐   ");
                    println("");
                }
                try {
                    print("  ");
                    line = Files.readAllLines(Paths.get("src/main/resources/Gods.txt")).get(i);
                    print(colorToAnsi(Color.WHITE)+line);
                    space(line);
                    k = i + 6;
                    line = Files.readAllLines(Paths.get("src/main/resources/Gods.txt")).get(k);
                    print(colorToAnsi(Color.WHITE)+line);
                    space(line);
                    k = i + 2*6;
                    line = Files.readAllLines(Paths.get("src/main/resources/Gods.txt")).get(k);
                    print(colorToAnsi(Color.WHITE)+line);
                    println("");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (i == j*18) {
                    for(int l=0 ; l<3 ; l++)
                        print(colorToAnsi(Color.BLUE) + "├─────────────────────────────────────────────┤   ");

                    println("");
                }else if (i == (j*18+5)) {
                    for(int l=0 ; l<3 ; l++)
                        print(colorToAnsi(Color.BLUE) + "└─────────────────────────────────────────────┘   ");

                    println("");

                }
            }
            System.out.println();
        }

    }
    public void printGods(List<Gods>gods){

        List<String> stringGod = new ArrayList<>();
        for(Gods god : gods)
            stringGod.add(god.toString());

        String line;

        try {
            FileReader reader = new FileReader("src/main/resources/Gods.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);


            while ((line = bufferedReader.readLine()) != null) {
                for(String string : stringGod){
                    if(string.equals(line)){
                        println(colorToAnsi(Color.BLUE) + "┌─────────────────────────────────────────────┐   ");
                        print(" ");
                        println(colorToAnsi(Color.WHITE)+line.toUpperCase());
                        println(colorToAnsi(Color.BLUE) + "├─────────────────────────────────────────────┤   ");
                        for(int i=0 ; i<5 ;i++ ){
                            print(" ");
                            println(colorToAnsi(Color.WHITE)+bufferedReader.readLine());

                        }
                        println(colorToAnsi(Color.BLUE) + "└─────────────────────────────────────────────┘   ");

                    }
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  void board(Map map , List<Worker> workerList , List<Pair<String, Gods>> chosenGods) throws OutOfMapException {

        ArrayList<String> players = new ArrayList<>();
        int maxCoordinate = map.getMaxCoordinate() + 1;
        int dim = 3*maxCoordinate + 1;
        String[][] board = new String[dim][dim];

        for(Worker worker : workerList){
            if(!players.contains(worker.getNamePlayer()))
                players.add(worker.getNamePlayer());
        }

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = "     ";


        for (int row = 0 ;row < dim ; row++) {
            if (row % 3 == 0) {
                for (int col = 0; col < dim; col++) {
                    board[row][col] = "─────";
                    if(col%3==0)
                        board[row][col] = "┼";
                    if( row==0 && col%3==0)
                        board[row][col] = "┬";
                    if( row==dim-1 && col%3==0)
                        board[row][col] = "┴";
                    if (col==0)
                        board[row][col] = "├";
                    if (col==dim-1)
                        board[row][col] = "┤";
                }

            }else{
                for (int col = 0; col < dim; col++) {
                    if (col % 3 == 0)
                        board[row][col] = "|";

                }
            }
        }
        board[0][0]="┌";
        board[0][dim-1]="┐";
        board[dim-1][0]="└";
        board[dim-1][dim-1]="┘";

        for (int i = 0; i < maxCoordinate ; i++) {
            for (int j = 0; j < maxCoordinate; j++) {

                if (map.getSquare(j,i).getLevel()== Level.GROUND) {
                    board[2 * i + i + 1][2 * j + j + 1] = "  G  ";
                } else if (map.getSquare(j,i).getLevel()== Level.FIRST) {
                    board[2 * i + i + 1][2 * j + j + 1] = "  F  ";
                } else if (map.getSquare(j,i).getLevel()== Level.SECOND) {
                    board[2 * i + i + 1][2 * j + j + 1] = "  S  ";
                } else if (map.getSquare(j,i).getLevel()== Level.THIRD) {
                    board[2 * i + i + 1][2 * j + j + 1] = "  T  ";
                } else if (map.getSquare(j,i).getLevel()== Level.DOME) {
                    board[2 * i + i + 1][2 * j + j + 1] = "  D  ";
                }

                if(map.getSquare(j, i).isPresentWorker()) {
                    for (Worker worker : workerList) {
                        if (worker.getPosition().getCoordinates().getX() == j && worker.getPosition().getCoordinates().getY() == i) {
                            if (worker.getNamePlayer().equals(players.get(0)))
                                board[2 * i + i + 2][2 * j + j + 2] = "  W0 ";
                            else if (worker.getNamePlayer().equals(players.get(1)))
                                board[2 * i + i + 2][2 * j + j + 2] = "  W1 ";
                            else
                                board[2 * i + i + 2][2 * j + j + 2] = "  W2 ";
                        }
                    }
                }

                board[2 * i + i + 2][2 * j + j + 1] = "["+(j+1)+","+(i+1)+"]";
                            
            }
        }
        for(int i=0 ; i<dim ;i++){
            for(int j=0 ; j<dim ;j++) {
                if(board[i][j] .equals( "  G  "))
                    print(colorToAnsi(Color.CYAN)+board[i][j]);
                else if(board[i][j] .equals("  F  "))
                    print(colorToAnsi(Color.GREEN)+board[i][j]);
                else if(board[i][j] .equals("  S  "))
                    print(colorToAnsi(Color.YELLOW)+board[i][j]);
                else if(board[i][j].equals("  T  "))
                    print(colorToAnsi(Color.PURPLE)+board[i][j]);
                else if(board[i][j].equals("  D  "))
                    print(colorToAnsi(Color.BLUE)+board[i][j]);
                else if(board[i][j].equals("  W0 "))
                    print(colorToAnsi(Color.RED)+"  W  ");
                else if(board[i][j].equals("  W1 "))
                    print(colorToAnsi(Color.BLUE)+"  W  ");
                else if(board[i][j].equals("  W2 "))
                    print(colorToAnsi(Color.WHITE)+"  W  ");
                else
                    print(colorToAnsi(Color.GREEN) + board[i][j]);

            }


            println("");
        }
        printLegend(chosenGods);

    }
    public void printLegend(List<Pair<String, Gods>> chosenGod) {

        if (chosenGod.isEmpty()) {
            print("");
        } else {
            
            for (int i = 0; i < 2; i++)
                print(colorToAnsi(Color.GREEN) + "┌──────────────────────────┐");
            println("");
            println(colorToAnsi(Color.WHITE) + "  LEGEND");

            print(colorToAnsi(Color.WHITE) + "  [x,y] = Coordinates");
            space1(" [x,y] = Coordinates");
            println(colorToAnsi(Color.RED) + "  Player1 : " + chosenGod.get(0).getValue0());
            print(colorToAnsi(Color.CYAN) + "  G = Ground");
            space1(" G = Ground");
            println(colorToAnsi(Color.BLUE) + "  Player2 : " + chosenGod.get(1).getValue0());
            print(colorToAnsi(Color.GREEN) + "  F = First");
            space1(" F = First");
            if(chosenGod.size()==3)
                print(colorToAnsi(Color.WHITE) + "  Player3 : " + chosenGod.get(2).getValue0());
            println("");
            print(colorToAnsi(Color.YELLOW) + "  S = Second");
            space1(" S = Second");
            println(colorToAnsi(Color.RED) + "  "+chosenGod.get(0).getValue0() + " -> " + chosenGod.get(0).getValue1());
            print(colorToAnsi(Color.PURPLE) + "  T = Third");
            space1(" T = Third");
            println(colorToAnsi(Color.BLUE) +"  " +chosenGod.get(1).getValue0() + " -> " + chosenGod.get(1).getValue1());
            print(colorToAnsi(Color.BLUE) + "  D = Dome");
            space1(" T = Third");
            if(chosenGod.size()==3)
                println(colorToAnsi(Color.WHITE) + "  " + chosenGod.get(2).getValue0() + " -> " + chosenGod.get(2).getValue1());
            println("");
            for (int i = 0; i < 2; i++)
                print(colorToAnsi(Color.GREEN) + "└──────────────────────────┘");
        }
        println("");
    }

        public static void space1(String string){

            int length = string.length();
            String spaces;

            for(int i=0 ;i<30-length;i++){
                System.out.print(" ");
            }
        }
    public void println(String toPrint){
        AnsiConsole.out.println(toPrint);
    }
    public void print(String toPrint){
        AnsiConsole.out.print(toPrint);
    }

    public String colorToAnsi(Color color){
        switch (color){
            case BLUE:
                return "\u001B[0;34m";
            case RED:
                return "\u001B[0;31m";
            case YELLOW:
                return "\u001B[0;33m";
            case GREEN:
                return "\u001B[0;32m";
            case PURPLE:
                return "\u001B[0;35m";
            case WHITE:
                return "\u001B[0;37m";
            case CYAN:
                return "\u001b[0;36m";
            default:
                return "\u001B[0;37m";
        }
    }
    public  void space(String string){

        int length = string.length();
        String spaces;

        for(int i=0 ;i<50-length;i++){
            System.out.print(" ");
        }
    }
}
