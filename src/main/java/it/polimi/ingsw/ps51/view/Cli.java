package it.polimi.ingsw.ps51.view;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Worker;
import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.utility.MessageHandler;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Cli extends Supporter {

    private boolean ok;
    private boolean isFinish;
    private MessageHandler mh;
    private Printer printer;
    private Scanner scanner = new Scanner(System.in);

    public Cli() {
        super();
        ok = false;
        isFinish = false;
        mh = new MessageHandler(this);
        printer = new Printer();
    }

    @Override
    public void update(EventForClient message) {
        getEvents().add(message);
    }

    @Override
    public void run() {
        launch();

        while(!isFinish) {

            Future<String> stringFuture = mh.handleTheFuture();
            ok = false;
            while (!ok) {
                ok = true;
                try {
                    setTypeOfEvent(stringFuture.get(1, TimeUnit.SECONDS));

                    switch (getTypeOfEvent()) {

                        case "NICKNAME":

                            String nickname = logIn();
                            EventForFirstPhase eventNickname = new Nickname(nickname);
                            notify(eventNickname);
                            break;
                        //continue by yourself :* <3
                        case"NUMBEROFPLAYER" :
                            int numberOfPlayers = numberOfPlayers();
                            EventForFirstPhase eventNumberOfPlayers = new NumberOfPlayers(numberOfPlayers);
                            notify(eventNumberOfPlayers);
                            break;
                        case "GODSDECK":
                            List<Gods> chosenGods = chooseGodsDeck();
                            EventForServer eventGodsDeck = new GodsDeck(chosenGods);
                            notify(eventGodsDeck);
                            break;

                        case "GOD" :
                            Gods god = chooseGodsPlayers();
                            EventForServer eventGodChoice = new GodChoice(god);
                            notify(eventGodChoice);
                            break;
                        case "WORKERPOSITION" :
                            Coordinates workerCoordinates = placeWorkers();
                            EventForServer eventWorkerPosition = new WorkerPosition(workerCoordinates);
                            notify(eventWorkerPosition);
                            break;

                        case "WORKER" :
                            Worker worker = chooseWorker();
                            EventForServer eventWorkerChoice = new WorkerChoice(worker);
                            notify(eventWorkerChoice);
                            break;
                        case "MOVE":
                            Coordinates coordinates = askMove();
                            EventForServer eventMoveChoice = new MoveChoice(coordinates);
                            notify(eventMoveChoice);
                            break;
                        case "BUILD":
                            Pair<Coordinates, Level> buildOn =askBuild();
                            EventForServer eventBuild = new Build(buildOn);
                            notify(eventBuild);
                            break;
                        case "MAP":

                            updateMap();

                            break;
                        case "WIN":

                            break;
                        case "LOSE":

                            break;
                        case "TIE":


                            break;
                        case "DISCONNECT":

                            break;
                        case "END":

                            break;
                        default:
                            ok = false;
                            break;

                    }
                } catch (InterruptedException | TimeoutException | ExecutionException | OutOfMapException e) {
                    // e.printStackTrace();
                    ok=false;
                }
            }
        }

    }

    public void launch(){
        printer.welcome();
    }

    public String logIn(){
        printer.println(printer.colorToAnsi(Color.GREEN)+"Insert your Nickname:");

        String nickname = "";
        while (nickname.equals("")) {
            nickname = scanner.nextLine();
            if ( nickname.contains(" ") ) {
                printer.println(printer.colorToAnsi(Color.RED)+"Write something without space...");
            }
            if ( nickname.equals("")) {
                printer.println(printer.colorToAnsi(Color.RED)+"Write something...");
            }
        }
        return nickname;

    }

    public int numberOfPlayers(){
        int numberOfPlayers = 0;
        printer.println(printer.colorToAnsi(Color.BLUE)+"Choose the number of players : ");
        printer.println(printer.colorToAnsi(Color.BLUE)+"2 players or 3 players ? ");
        while(numberOfPlayers!=2 && numberOfPlayers!=3){
            numberOfPlayers=scanner.nextInt();

            if(numberOfPlayers!=2 && numberOfPlayers!=3){
                printer.println(printer.colorToAnsi(Color.RED)+"You can only have 2 or 3 players!! ");
                printer.println(printer.colorToAnsi(Color.RED)+"Choose another number!");
            }

        }
        return numberOfPlayers;
    }

    public List<Gods> chooseGodsDeck(){

        List<Gods> choosenGods = new ArrayList<>();
        String choosenGod;

        printer.printDeck();
        printer.println(printer.colorToAnsi(Color.BLUE) + "You must choose only "+ getGodsNum() +" Gods !!");
        for(int i=0 ; i<getGodsNum();i++ ){
            printer.println(printer.colorToAnsi(Color.BLUE) + "Enter the name of the "+i+"º God?");
            choosenGod =scanner.nextLine();
           if(Gods.valueOf(choosenGod.toUpperCase()).toString().equals(choosenGod.toUpperCase())){
               choosenGods.add(Gods.valueOf(choosenGod.toUpperCase()));
           }else{
               while(!(Gods.valueOf(choosenGod.toUpperCase()).toString().equals(choosenGod.toUpperCase()))) {
                   printer.println(printer.colorToAnsi(Color.RED) + "This name is NOT ACCEPTABLE !!");
                   printer.println(printer.colorToAnsi(Color.BLUE) + "Enter a VALID name of the  God !!");
                   choosenGod =scanner.nextLine();
               }

               choosenGods.add(Gods.valueOf(choosenGod.toUpperCase()));
           }
        }


        return choosenGods;
    }
    //Da mettere apposto
    public Gods chooseGodsPlayers(){

        Gods choosenGod = null ;
        String choosen = null;
        List<String> stringGod = new ArrayList<>();
        for(Gods god : getAvailableGods()){
            stringGod.add(god.toString());
        }
        printer.printGods(stringGod);

        printer.println(printer.colorToAnsi(Color.BLUE) + "You must choose only 1 God !!");
        printer.println(printer.colorToAnsi(Color.BLUE) + "Type the name RIGHT !!");
        choosen = scanner.nextLine();

        for(Gods god : getAvailableGods()){
            if(god==Gods.valueOf(choosen.toUpperCase()))
                choosenGod=god;

        }

        while(choosenGod==null){

            printer.println(printer.colorToAnsi(Color.RED) + "Type the name RIGHT !!");
            printer.println(printer.colorToAnsi(Color.RED) + "Enter a VALID name !!");
            choosen = scanner.nextLine();

            for(Gods god : getAvailableGods()){
                if(god==Gods.valueOf(choosen.toUpperCase()))
                    choosenGod=god;

            }
        }


        return choosenGod;

    }

    public void updateMap() throws OutOfMapException {

        printer.board(getMap() , getWorkers());
    }
    //Da mettere apposto
    public Coordinates placeWorkers(){

        Coordinates coordinates ;
        int x = 0;
        int y = 0;

        printer.println(printer.colorToAnsi(Color.BLUE) + "Where do you want to place your " + getWorkerNum() + "º worker ?");
        printer.println(printer.colorToAnsi(Color.BLUE) + "Enter VALID coordinates !! ");
        printer.print(printer.colorToAnsi(Color.BLUE) + "Enter the X coordinate from 1 to 5: ");
        x = scanner.nextInt();
        printer.print(printer.colorToAnsi(Color.BLUE) + "Enter the y coordinate from 1 to 5: ");
        y = scanner.nextInt();
        while((x<1 || x>5)||(y<1 || y>5)){
            printer.println(printer.colorToAnsi(Color.BLUE) + "This Coordinates are NOT VALID !!");
            printer.println(printer.colorToAnsi(Color.BLUE) + "Enter VALID coordinates !! ");
            printer.print(printer.colorToAnsi(Color.BLUE) + "Enter the X coordinate from 1 to 5: ");
            x = scanner.nextInt();
            printer.print(printer.colorToAnsi(Color.BLUE) + "Enter the y coordinate from 1 to 5: ");
            y = scanner.nextInt();
        }
        coordinates = new Coordinates(x-1,y-1);

        return coordinates;
    }


    public Worker chooseWorker(){

        int choice = 0;
        Worker worker = null;

        printer.println(printer.colorToAnsi(Color.BLUE) + "Which worker do you want to use ?");
        printer.println(printer.colorToAnsi(Color.BLUE) + "1.The Worker in position ["+getValidChoicesWorkers().get(0).getPosition().getCoordinates().getX()+"]" +
                        "["+getValidChoicesWorkers().get(0).getPosition().getCoordinates().getY()+"]");
        printer.println(printer.colorToAnsi(Color.BLUE) + "2.The Worker in position ["+getValidChoicesWorkers().get(1).getPosition().getCoordinates().getX()+"]" +
                "["+getValidChoicesWorkers().get(1).getPosition().getCoordinates().getY()+"]");
        printer.println(printer.colorToAnsi(Color.BLUE) + "Enter 1 or 2 !!");
        choice = scanner.nextInt();
        while(choice!=1 && choice!=2){
            printer.println(printer.colorToAnsi(Color.BLUE) + "Enter a VALID number !");
            printer.println(printer.colorToAnsi(Color.BLUE) + "Enter 1 or 2 !!");
            choice = scanner.nextInt();
        }

        worker = getValidChoicesWorkers().get(choice-1);

        return worker;

    }

    public Coordinates askMove(){

        int x = 0;
        int y = 0;
        Coordinates coord = null;

        printer.println(printer.colorToAnsi(Color.BLUE) + "Where do you want your worker to move ?");
        printer.print(printer.colorToAnsi(Color.BLUE) + "Enter the X coordinate : ");
        x = scanner.nextInt();
        printer.println(printer.colorToAnsi(Color.BLUE) + "Enter the Y coordinate : ");
        y = scanner.nextInt();

        for(Coordinates coordinates : getValidChoicesMoves()){
            if(coordinates.getX()==x && coordinates.getY()==y)
                coord=coordinates;
        }

        while (coord==null){
            printer.println(printer.colorToAnsi(Color.BLUE) + "The coordinates you entered are not available!!");
            printer.print(printer.colorToAnsi(Color.BLUE) + "Enter a VALID X coordinate ! ");
            x = scanner.nextInt();
            printer.println(printer.colorToAnsi(Color.BLUE) + "Enter a VALID Y coordinate ! ");
            y = scanner.nextInt();

            for(Coordinates coordinates : getValidChoicesMoves()){
                if(coordinates.getX()==x && coordinates.getY()==y)
                    coord=coordinates;
            }
        }
        return coord;

    }
    //Da mettere apposto
    public Pair<Coordinates, Level> askBuild(){

        //DA CONTROLLAREEEEEE

        Coordinates coordinates = null;
        Level level = null ;
        Level levelPossibilities = null;
        Pair<Coordinates, Level> buildOn = null;

        int x = 0;
        int y = 0;
        int z = 0;


        printer.println(printer.colorToAnsi(Color.BLUE) + "Where do you want to build ?");
        printer.print(printer.colorToAnsi(Color.BLUE) + "Enter the X coordinate : ");
        x = scanner.nextInt();
        printer.println(printer.colorToAnsi(Color.BLUE) + "Enter the Y coordinate : ");
        y = scanner.nextInt();

        for(Pair<Coordinates, List<Level>> validBuilds : getValidChoicesBuild()){
            if(validBuilds.getValue0().getX()==x && validBuilds.getValue0().getY()==y) {
                coordinates = new Coordinates(x, y);

            }
        }

        while(coordinates==null){
            printer.println(printer.colorToAnsi(Color.BLUE) + "The coordinates you entered are not available!!");
            printer.print(printer.colorToAnsi(Color.BLUE) + "Enter a VALID X coordinate ! ");
            x = scanner.nextInt();
            printer.println(printer.colorToAnsi(Color.BLUE) + "Enter a VALID Y coordinate ! ");
            y = scanner.nextInt();

            for(Pair<Coordinates, List<Level>> validBuilds : getValidChoicesBuild()){
                if(validBuilds.getValue0().getX()==x && validBuilds.getValue0().getY()==y) {
                    coordinates = new Coordinates(x, y);

                }
            }
        }

        printer.println(printer.colorToAnsi(Color.BLUE) + "What level do you want to build ?");
        printer.println(printer.colorToAnsi(Color.BLUE) + "1.First");
        printer.println(printer.colorToAnsi(Color.BLUE) + "2.Second");
        printer.println(printer.colorToAnsi(Color.BLUE) + "3.Third");
        printer.println(printer.colorToAnsi(Color.BLUE) + "4.Dome");

        z = scanner.nextInt();

        while (z<1||z>4){
            printer.println(printer.colorToAnsi(Color.RED) + "This number is NOT available!!");
            printer.println(printer.colorToAnsi(Color.BLUE) + "Enter a number from 1 to 4 !!");
            z=scanner.nextInt();
        }

        switch(z) {
            case 1 :
                levelPossibilities = Level.FIRST;
                break;
            case 2 :
                levelPossibilities = Level.SECOND;
                break;
            case 3 :
                levelPossibilities = Level.THIRD;
                break;
            case 4 :
                levelPossibilities = Level.DOME;
                break;
            default:
                break;
        }

        for(Pair<Coordinates, List<Level>> validBuilds : getValidChoicesBuild()){
            if(validBuilds.getValue0().getX()==x && validBuilds.getValue0().getY()==y) {
                for(Level validLevel : validBuilds.getValue1()){
                    if(validLevel==levelPossibilities){
                        level = levelPossibilities;
                    }
                }

            }
        }

        while(level==null){
            printer.println(printer.colorToAnsi(Color.RED) + "This Level is NOT available!!");
            printer.println(printer.colorToAnsi(Color.BLUE) + "Enter a valid Level !!");

        }

        buildOn = new Pair<>(coordinates,level);

        return buildOn;
    }

    public void winGame(){

    }

    public void loseGame(){

    }

    public void disconnectGame(){

    }

    public void endGame(){

    }
}
