package it.polimi.ingsw.ps51.view;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import org.fusesource.jansi.AnsiConsole;

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
        int k = 0;

        for(int j=0 ; j<3 ;j++) {
            for (int i = j*18; i <(j*18+6); i++) {
                if (i == j*18) {
                    for(int l=0 ; l<3 ; l++)
                        print(colorToAnsi(Color.BLUE) + "┌─────────────────────────────────────────────┐   ");
                    println("");
                }
                try {
                    print("  ");
                    line = Files.readAllLines(Paths.get("C:\\Users\\merit\\Desktop\\ing-sw-2020-Iamundo-Giacometti-Mullameti\\src\\main\\resources\\Gods.txt")).get(i);
                    print(colorToAnsi(Color.WHITE)+line);
                    space(line);
                    k = i + 6;
                    line = Files.readAllLines(Paths.get("C:\\Users\\merit\\Desktop\\ing-sw-2020-Iamundo-Giacometti-Mullameti\\src\\main\\resources\\Gods.txt")).get(k);
                    print(colorToAnsi(Color.WHITE)+line);
                    space(line);
                    k = i + 2*6;
                    line = Files.readAllLines(Paths.get("C:\\Users\\merit\\Desktop\\ing-sw-2020-Iamundo-Giacometti-Mullameti\\src\\main\\resources\\Gods.txt")).get(k);
                    print(colorToAnsi(Color.WHITE)+line);
                    println("");

                } catch (IOException e) {
                    System.out.println(e);
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
    public void printGods(List<String>gods){
        String line;

        try {
            FileReader reader = new FileReader("C:\\Users\\merit\\Desktop\\ing-sw-2020-Iamundo-Giacometti-Mullameti\\src\\main\\resources\\Gods.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);


            while ((line = bufferedReader.readLine()) != null) {
                for(String string : gods){
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

    public  void board(Map map , List<Worker> workerList) throws OutOfMapException {


        String[][] board = new String[16][16];

        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
                board[i][j] = "   ";


        for (int row = 0; row < 16; row++) {
            if (row % 3 == 0) {
                for (int col = 0; col < 16; col++) {
                    board[row][col] = "───";
                    if (col % 3 == 0)
                        board[row][col] = "┼";
                    if (row == 0 && col % 3 == 0)
                        board[row][col] = "┬";
                    if (row == 15 && col % 3 == 0)
                        board[row][col] = "┴";
                    if (col == 0)
                        board[row][col] = "├";
                    if (col == 15)
                        board[row][col] = "┤";
                }

            } else {
                for (int col = 0; col < 16; col++) {
                    if (col % 3 == 0)
                        board[row][col] = "|";

                }
            }
        }
        board[0][0] = "┌";
        board[0][15] = "┐";
        board[15][0] = "└";
        board[15][15] = "┘";

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if (map.getSquare(j,i).getLevel()== Level.GROUND) {
                    board[2 * i + i + 1][2 * j + j + 1] = " G ";
                } else if (map.getSquare(j,i).getLevel()== Level.FIRST) {
                    board[2 * i + i + 1][2 * j + j + 1] = " F ";
                } else if (map.getSquare(j,i).getLevel()== Level.SECOND) {
                    board[2 * i + i + 1][2 * j + j + 1] = " S ";
                } else if (map.getSquare(j,i).getLevel()== Level.THIRD) {
                    board[2 * i + i + 1][2 * j + j + 1] = " T ";
                } else if (map.getSquare(j,i).getLevel()== Level.DOME) {
                    board[2 * i + i + 1][2 * j + j + 1] = " D ";
                }

                if(map.getSquare(j, i).isPresentWorker()) {
                    board[2 * i + i + 2][2 * j + j + 2] = " W ";

                }


            }
        }
        for(int i=0 ; i<16 ;i++){
            for(int j=0 ; j<16 ;j++) {
                if(board[i][j] .equals( " G "))
                    print(colorToAnsi(Color.WHITE)+board[i][j]);
                else if(board[i][j] .equals(" F "))
                    print(colorToAnsi(Color.YELLOW)+board[i][j]);
                else if(board[i][j] .equals(" S "))
                    print(colorToAnsi(Color.YELLOW)+board[i][j]);
                else if(board[i][j].equals(" T "))
                    print(colorToAnsi(Color.PURPLE)+board[i][j]);
                else if(board[i][j].equals(" D "))
                    print(colorToAnsi(Color.BLUE)+board[i][j]);
                else if(board[i][j].equals(" W "))
                    print(colorToAnsi(Color.RED)+board[i][j]);
                else
                    print(colorToAnsi(Color.GREEN) + board[i][j]);

            }
            println("");
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
                return "\u001B[0;36m";
            case WHITE:
                return "\u001B[0;37m";
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
