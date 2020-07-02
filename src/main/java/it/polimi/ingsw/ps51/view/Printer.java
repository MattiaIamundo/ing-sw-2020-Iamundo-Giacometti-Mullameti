package it.polimi.ingsw.ps51.view;

import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Map;
import it.polimi.ingsw.ps51.model.Worker;
import it.polimi.ingsw.ps51.model.WorkerColor;
import it.polimi.ingsw.ps51.model.gods.Gods;
import org.fusesource.jansi.AnsiConsole;
import org.javatuples.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Printer {

    public Printer(){
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
    }

    public void welcome(){

       println(colorToAnsi(Color.CYAN)+" ────      /\\     |\\    | ───── ─────  ──────   ──────   |  |\\    |  |");
       println(colorToAnsi(Color.CYAN)+"|         /  \\    | \\   |      |      |      | |      |  |  | \\   |  |");
       println(colorToAnsi(Color.CYAN)+" ────    /────\\   |  \\  |      |      |      |  ─────    |  |  \\  |  |");
       println(colorToAnsi(Color.CYAN)+"     |  /      \\  |   \\ |      |      |      | |     \\   |  |   \\ |  |");
       println(colorToAnsi(Color.CYAN)+" ────  /        \\ |    \\|      |       ──────  |      \\  |  |    \\|  |");
    }


    /**
     * This represent the structure of a god loaded from the Gods.txt file.
     */
    private class GodRepresentation{
        String name;
        String godOf;
        List<String> description;

        public GodRepresentation(String name, String godOf, List<String> description) {
            this.name = name;
            this.godOf = godOf;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getGodOf() {
            return godOf;
        }

        public List<String> getDescription() {
            return description;
        }
    }

    /**
     * This method load the list of all the gods supported by the game and then display them to the user
     * who must chosen some of them
     */
    public void printDeck(){
        InputStream in = getClass().getResourceAsStream("/Gods.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        int DESCRIPTION_LINES = 4;
        int ITEM_PER_LINE = 3;

        try {
            List<GodRepresentation> gods = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null){
                List<String> description = new ArrayList<>();
                String name = line;
                String godOf = bufferedReader.readLine();
                for (int i = 0; i < DESCRIPTION_LINES; i++){
                    description.add(bufferedReader.readLine());
                }
                GodRepresentation god = new GodRepresentation(name, godOf, description);
                gods.add(god);
                bufferedReader.readLine();
                line = bufferedReader.readLine();
            }

            for (int x = 0; x < gods.size(); x++){
                if (x % ITEM_PER_LINE == 0){
                    if ((gods.size() - x) < ITEM_PER_LINE){
                        ITEM_PER_LINE = gods.size() - x;
                    }
                    for (int t = 0; t < ITEM_PER_LINE; t++){
                        print(colorToAnsi(Color.CYAN) + "┌─────────────────────────────────────────────┐   " + colorToAnsi(Color.WHITE));
                    }
                    print("\n ");
                    for (int t = 0; t < ITEM_PER_LINE; t++){
                        print(gods.get(x+t).getName());
                        space(gods.get(x+t).getName());
                    }
                    print("\n");
                    for (int t = 0; t < ITEM_PER_LINE; t++){
                        print(colorToAnsi(Color.CYAN) + "├─────────────────────────────────────────────┤   " + colorToAnsi(Color.WHITE));
                    }
                    print("\n ");
                    for (int t = 0; t < ITEM_PER_LINE; t++){
                        print(gods.get(x+t).getGodOf());
                        space(gods.get(x+t).getGodOf());
                    }
                    for (int i = 0; i < DESCRIPTION_LINES; i++) {
                        print("\n ");
                        for (int t = 0; t < ITEM_PER_LINE; t++){
                            print(gods.get(x+t).getDescription().get(i));
                            space(gods.get(x+t).getDescription().get(i));
                        }
                    }
                    print("\n");
                    for (int t = 0; t < ITEM_PER_LINE; t++){
                        print(colorToAnsi(Color.CYAN) + "└─────────────────────────────────────────────┘   " + colorToAnsi(Color.WHITE));
                    }
                    println("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Here, the available gods for the game are printed using the Gods file in the resources
     * @param gods list of god to print
     */
    public void printGods(List<Gods>gods){

        List<String> stringGod = new ArrayList<>();
        for(Gods god : gods)
            stringGod.add(god.toString());

        String line;

        try {
            InputStream in = getClass().getResourceAsStream("/Gods.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));


            while ((line = bufferedReader.readLine()) != null) {
                for(String string : stringGod){
                    if(string.equals(line)){
                        println(colorToAnsi(Color.CYAN) + "┌─────────────────────────────────────────────┐   ");
                        print(" ");
                        println(colorToAnsi(Color.WHITE)+line.toUpperCase());
                        println(colorToAnsi(Color.CYAN) + "├─────────────────────────────────────────────┤   ");
                        for(int i=0 ; i<5 ;i++ ){
                            print(" ");
                            println(colorToAnsi(Color.WHITE)+bufferedReader.readLine());

                        }
                        println(colorToAnsi(Color.CYAN) + "└─────────────────────────────────────────────┘   ");

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Here the printer has to print the map with all the updates.
     * The map to be printed is set with every element and then is printed.
     * After it, the method printLegends is called.
     * @param map           the map of the game
     * @param workerList    the list of the workers in the game
     * @param chosenGods    the god chosen by the players
     * @param playerColor   the color chosen by the players
     * @throws OutOfMapException if there are coordinates out of bounds of the map
     */
    public  void board(Map map , List<Worker> workerList , List<Pair<String, Gods>> chosenGods, java.util.Map<String, WorkerColor> playerColor) throws OutOfMapException {

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
                    print(colorToAnsi( Color.valueOf( playerColor.get(players.get(0)).toString().toUpperCase() ) ) + "  W  ");
                else if(board[i][j].equals("  W1 "))
                    print(colorToAnsi( Color.valueOf( playerColor.get(players.get(1)).toString().toUpperCase() ) ) + "  W  ");
                else if(board[i][j].equals("  W2 "))
                    print(colorToAnsi( Color.valueOf( playerColor.get(players.get(2)).toString().toUpperCase() ) ) + "  W  ");
                else
                    print(colorToAnsi(Color.GREEN) + board[i][j]);

            }


            println("");
        }
        printLegend(chosenGods, playerColor);

    }

    /**
     * Here, the legend is printed
     * @param chosenGod     the god chosen by the players
     * @param playerColor   the color chosen by the players
     */
    public void printLegend(List<Pair<String, Gods>> chosenGod, java.util.Map<String, WorkerColor> playerColor) {

        if (chosenGod.isEmpty()) {
            print("");
        } else {
            
            for (int i = 0; i < 2; i++)
                print(colorToAnsi(Color.GREEN) + "┌──────────────────────────┐");
            println("");
            println(colorToAnsi(Color.WHITE) + "  LEGEND");

            print(colorToAnsi(Color.WHITE) + "  [x,y] = Coordinates");
            space1(" [x,y] = Coordinates");
            println(colorToAnsi( Color.valueOf( playerColor.get(chosenGod.get(0).getValue0()).toString().toUpperCase() ) ) + "  Player1 : " + chosenGod.get(0).getValue0());
            print(colorToAnsi(Color.CYAN) + "  G = Ground");
            space1(" G = Ground");
            println(colorToAnsi( Color.valueOf( playerColor.get(chosenGod.get(1).getValue0()).toString().toUpperCase() ) ) + "  Player2 : " + chosenGod.get(1).getValue0());
            print(colorToAnsi(Color.GREEN) + "  F = First");
            space1(" F = First");
            if(chosenGod.size()==3)
                print(colorToAnsi( Color.valueOf( playerColor.get(chosenGod.get(2).getValue0()).toString().toUpperCase() ) ) + "  Player3 : " + chosenGod.get(2).getValue0());
            println("");
            print(colorToAnsi(Color.YELLOW) + "  S = Second");
            space1(" S = Second");
            println(colorToAnsi( Color.valueOf( playerColor.get(chosenGod.get(0).getValue0()).toString().toUpperCase()) ) + "  "+chosenGod.get(0).getValue0() + " -> " + chosenGod.get(0).getValue1());
            print(colorToAnsi(Color.PURPLE) + "  T = Third");
            space1(" T = Third");
            println(colorToAnsi( Color.valueOf( playerColor.get(chosenGod.get(1).getValue0()).toString().toUpperCase()) ) +"  " +chosenGod.get(1).getValue0() + " -> " + chosenGod.get(1).getValue1());
            print(colorToAnsi(Color.BLUE) + "  D = Dome");
            space1(" D = Dome");
            if(chosenGod.size()==3)
                println(colorToAnsi( Color.valueOf( playerColor.get(chosenGod.get(2).getValue0()).toString().toUpperCase()) ) + "  " + chosenGod.get(2).getValue0() + " -> " + chosenGod.get(2).getValue1());
            println("");
            for (int i = 0; i < 2; i++)
                print(colorToAnsi(Color.GREEN) + "└──────────────────────────┘");
        }
        println("");
    }

        public static void space1(String string){

            int length = string.length();
            //String spaces;

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
                return "\u001B[0;94m";
            case RED:
                return "\u001B[0;91m";
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
            case BRIGHT_GREEN:
                return "\u001B[0;92m";
            case BRIGHT_YELLOW:
                return "\u001B[0;93m";
            default:
                return "\u001B[0;37m";
        }
    }
    public void space(String string){

        int length = string.length();
        //String spaces = new String();

        for(int i=0 ;i<50-length;i++){
            print(" ");
        }
    }
}
