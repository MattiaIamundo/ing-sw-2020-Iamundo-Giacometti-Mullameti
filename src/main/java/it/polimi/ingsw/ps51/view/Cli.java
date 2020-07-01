package it.polimi.ingsw.ps51.view;

import it.polimi.ingsw.ps51.events.events_for_client.EventForClient;
import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.Worker;
import it.polimi.ingsw.ps51.model.WorkerColor;
import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.utility.InputReader;
import it.polimi.ingsw.ps51.utility.InterruptibleInputStream;
import it.polimi.ingsw.ps51.utility.MessageHandler;
import org.javatuples.Pair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;

public class Cli extends Supporter {

    private boolean ok;
    boolean isFinish;
    MessageHandler mh;
    Printer printer;
    InputReader reader;


    public Cli() {
        super();
        ok = false;
        isFinish = false;
        mh = new MessageHandler(this);
        printer = new Printer();
        reader = new InputReader();
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

                    switch (stringFuture.get()) {

                        case "NICKNAME":

                            String nickname = logIn();
                            EventForFirstPhase eventNickname = new Nickname(nickname);
                            notify(eventNickname);
                            break;
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
                        case "FIRSTPLAYER":
                            String firstPlayer = chooseFirstPlayer();
                            EventForServer playerChoice = new FirstPlayerChoice(firstPlayer);
                            notify(playerChoice);
                            break;
                        case "WORKERPOSITION" :
                            EventForServer eventWorkerPosition;
                            do {
                                Coordinates workerCoordinates = placeWorkers();
                                eventWorkerPosition = new WorkerPosition(workerCoordinates);
                            } while (!undo());
                            notify(eventWorkerPosition);
                            break;

                        case "WORKER" :
                            EventForServer eventWorkerChoice;
                            do {
                                Worker worker = chooseWorker();
                                eventWorkerChoice = new WorkerChoice(worker);
                            } while (!undo());
                            notify(eventWorkerChoice);
                            break;
                        case "MOVE":
                            EventForServer eventMoveChoice;
                            do {
                                Coordinates coordinates = askMove();
                                eventMoveChoice = new MoveChoice(coordinates);
                            } while (!undo());
                            notify(eventMoveChoice);
                            break;
                        case "BUILD":
                            EventForServer eventBuild;
                            do {
                                Pair<Coordinates, Level> buildOn =askBuild();
                                eventBuild = new Build(buildOn);
                            } while (!undo());
                            notify(eventBuild);
                            break;
                        case "MAP":
                            updateMap();
                            break;
                        case "DECISION" :
                            boolean decision = makeDecision();
                            EventForServer eventDecisionTaken = new DecisionTaken(decision);
                            notify(eventDecisionTaken);
                            break;
                        case "ACK":
                            ack();
                            break;
                        case "UNSUCCESSFULOPERATION":
                            unsuccessfulOperation();
                            break;
                        case "GAME_IS_STARTING":
                            gameIsStarting();
                            break;
                        case "TURN_IS_END":
                            turnIsEnd();
                            break;
                        case "WIN":
                            winGame();
                            break;
                        case "LOSE":
                            loseGame();
                            break;
                        case "ROOM":
                            outOfRoom();
                            isFinish = true;
                            break;
                        case "DISCONNECTION":
                            disconnectGame();
                            isFinish = true;
                            break;
                        case "END":
                            endGame();
                            isFinish = true;
                            break;
                        case "COLOR":
                            WorkerColor colorChoice = chooseColor();
                            ColorChoice colorChoiceEvent = new ColorChoice(colorChoice);
                            notify(colorChoiceEvent);
                            break;
                        default:
                            ok = false;
                            break;

                    }
                } catch (InterruptedException | ExecutionException | OutOfMapException e) {
                    ok = false;
                }
            }
        }
        mh.getEx().shutdownNow();
        reader.close();
        System.exit(0);
    }

    private void turnIsEnd() {
        printer.println(printer.colorToAnsi(Color.PURPLE) + "Your turn has ended !" + printer.colorToAnsi(Color.CYAN));
    }

    private void gameIsStarting(){
        printer.println(printer.colorToAnsi(Color.GREEN) + "The game is started!!" + printer.colorToAnsi(Color.CYAN));
    }

    public void launch(){
        printer.welcome();
    }

    public String logIn() throws InterruptedException {

        String nickname = "";
        ok = false;

        while(!ok){
            printer.println(printer.colorToAnsi(Color.GREEN) + "Insert your Nickname:" + printer.colorToAnsi(Color.CYAN));
            nickname = reader.read();

            if ( nickname.equals("")) {
                printer.println(printer.colorToAnsi(Color.RED)+"Write something..." + printer.colorToAnsi(Color.CYAN));
            }else {
                ok = true;
            }
        }

        return nickname;
    }

    public int numberOfPlayers() throws InterruptedException {

        int numberOfPlayers = 0;
        ok = false;
        printer.println(printer.colorToAnsi(Color.CYAN) + "Choose the number of players !");
        printer.println(printer.colorToAnsi(Color.CYAN) + "2 players or 3 players ? ");

        while(!ok){
            try{
                numberOfPlayers = reader.readInt();

                if(numberOfPlayers!=2 && numberOfPlayers!=3){
                    printer.println(printer.colorToAnsi(Color.RED) + "You can only have 2 or 3 players!!");
                    printer.println("Enter 2 or 3 !!" + printer.colorToAnsi(Color.CYAN));
                }else {
                    ok = true;
                }

            }catch(NumberFormatException e){
                printer.println(printer.colorToAnsi(Color.RED) + "What you entered is NOT OK !!");
                printer.println("Enter a number!!" + printer.colorToAnsi(Color.CYAN));
            }
        }
        return numberOfPlayers;
    }

    public List<Gods> chooseGodsDeck() throws InterruptedException {

        List<Gods> chosenGods = new ArrayList<>();
        String chosenGod;
        printer.printDeck();
        printer.println(printer.colorToAnsi(Color.CYAN) + "You must choose only " + getGodsNum() + " Gods !!");

        for (int i = 0; i < getGodsNum(); i++) {
            ok = false;
            while (!ok) {
                printer.println(printer.colorToAnsi(Color.CYAN) + "Enter the name of the " + (i + 1) + "º God?");
                try {
                    chosenGod = reader.read().toUpperCase();
                    if((chosenGods.isEmpty()) || (!(chosenGods.contains(Gods.valueOf(chosenGod))))){
                        chosenGods.add(Gods.valueOf(chosenGod));
                        ok = true;
                    }else{
                        printer.println(printer.colorToAnsi(Color.RED) + "You already chose that one !!");
                        printer.println("Choose another!!" + printer.colorToAnsi(Color.CYAN));
                    }

                } catch (IllegalArgumentException e) {
                    printer.println(printer.colorToAnsi(Color.RED) + "This name is NOT ACCEPTABLE !!");
                    printer.println("Enter a VALID name of a  God !!" + printer.colorToAnsi(Color.CYAN));
                }
            }
        }

        return chosenGods;
    }

    public Gods chooseGodsPlayers() throws InterruptedException {

        Gods chosenGod = null;
        String chosen;
        ok=false;
        printer.printGods(getAvailableGods());

        printer.println(printer.colorToAnsi(Color.CYAN) + "You must choose only 1 God !!");
        printer.println(printer.colorToAnsi(Color.CYAN) + "Type the name RIGHT !!");

        while(!ok){
            try{
                chosen = reader.read().toUpperCase();
                if(getAvailableGods().contains(Gods.valueOf(chosen))) {
                    chosenGod = Gods.valueOf(chosen);
                    ok = true;
                }else {
                    printer.println(printer.colorToAnsi(Color.RED) + "The God you choose is NOT AVAILABLE !!");
                    printer.println("Choose one of the given Gods!!" + printer.colorToAnsi(Color.CYAN));
                }

            }catch (IllegalArgumentException e){
                printer.println(printer.colorToAnsi(Color.RED) + "This name is NOT ACCEPTABLE !!");
                printer.println("Enter a VALID name of a  God !!" + printer.colorToAnsi(Color.CYAN));
            }
        }

        return chosenGod;
    }

    public String chooseFirstPlayer() throws InterruptedException {
        String firstPlayer = null;
        ok = false;

        printer.println(printer.colorToAnsi(Color.CYAN)+"You have to choose who is the first to play:");
        printer.println(printer.colorToAnsi(Color.CYAN) + getPlayers());

        while (!ok){
            firstPlayer = reader.read();
            if (getPlayers().contains(firstPlayer)){
                ok = true;
            }else {
                printer.println(printer.colorToAnsi(Color.RED) + "'" + firstPlayer +
                        "' isn't a valid player's username, please select a name from the given list" + printer.colorToAnsi(Color.CYAN));
            }
        }
        return firstPlayer;
    }

    public void updateMap() throws OutOfMapException {
        printer.board(getMap() , getWorkers() ,getChosenGods(), getChosenColors());
    }

    public Coordinates placeWorkers() throws InterruptedException {

        Coordinates coordinates;
        int x;
        int y;

        printer.println(printer.colorToAnsi(Color.CYAN) + "Where do you want to place your " + getWorkerNum() + "º worker ?");
        printer.println(printer.colorToAnsi(Color.CYAN) + "Enter VALID coordinates !! ");

        x = coordinateCheck("X");

        y = coordinateCheck("Y");

        coordinates = new Coordinates(x-1,y-1);
        return coordinates;
    }


    public Worker chooseWorker() throws InterruptedException {
        int choice = 0;
        Worker worker;
        ok =  false;
        int count = 1;
        printer.println(printer.colorToAnsi(Color.CYAN) + "Which worker do you want to use ?");
        for(Worker workers : getValidChoicesWorkers()) {
            printer.println(printer.colorToAnsi(Color.CYAN) + count +".The Worker in position [" + (workers.getPosition().getCoordinates().getX() + 1) + "]" +
                    "[" + (workers.getPosition().getCoordinates().getY() + 1) + "]");
            count = count + 1;
        }
        while(!ok){
            try{
                choice = reader.readInt();
                if(choice!=1 && choice!=2){
                    printer.println(printer.colorToAnsi(Color.RED) + "Enter a VALID number !");
                    printer.println("Enter 1 or 2 !!" + printer.colorToAnsi(Color.CYAN));
                }else {
                    ok = true;
                }
            }catch(NumberFormatException e) {
                printer.println(printer.colorToAnsi(Color.RED) + "What you entered is NOT OK !!");
                printer.println("Enter a number!!" + printer.colorToAnsi(Color.CYAN));
            }
        }
        worker = getValidChoicesWorkers().get(choice-1);
        return worker;
    }

    public Coordinates askMove() throws InterruptedException {

        int x = 0;
        int y = 0;
        Coordinates coordinates;
        boolean notAvailable = true;
        ok = false;
        printer.println(printer.colorToAnsi(Color.CYAN) + "Where do you want your worker to move ?");
        printer.println(printer.colorToAnsi(Color.CYAN) + "These are the available coordinates :");
        for(Coordinates coord : getValidChoicesMoves()){
            printer.print(printer.colorToAnsi(Color.CYAN) + " [" + (coord.getX()+1) + " , " + (coord.getY()+1) +"]");
        }
        printer.println("");
        while (notAvailable) {

            x = coordinateCheck("X");

            y = coordinateCheck("Y");

            for(Coordinates coord : getValidChoicesMoves()){
                if(coord.getX()==(x-1) && coord.getY()==(y-1)){
                    notAvailable = false;
                    break;
                }

            }

            if(notAvailable){
                printer.println(printer.colorToAnsi(Color.RED) + "This coordinates are not available");
                printer.println(printer.colorToAnsi(Color.CYAN) + "Enter valid Coordinates");
            }
        }
        coordinates = new Coordinates(x-1,y-1);
        return coordinates;
    }

      public Pair<Coordinates, Level> askBuild() throws InterruptedException {

        int x;
        int y;
        String z = "";
        boolean notAvailable = false;
        Coordinates coordinates = null;
        Level level = null ;
        boolean validCoordinates = false;
        Pair<Coordinates, Level> buildOn;


        printer.println(printer.colorToAnsi(Color.CYAN) + "Where do you want your worker to build ?");
        printer.println(printer.colorToAnsi(Color.CYAN) + "These are the available coordinates :");
        for(Pair<Coordinates, List<Level>> validBuilds : getValidChoicesBuild()){
            for(Level l : validBuilds.getValue1())
                printer.print(printer.colorToAnsi(Color.CYAN) + " [" + (validBuilds.getValue0().getX() + 1) + " , " + (validBuilds.getValue0().getY() + 1) + " , " + l +"]");
        }
        printer.println("");
        while (!notAvailable) {

            x = coordinateCheck("X");
            y = coordinateCheck("Y");

            ok = false;

            for(Pair<Coordinates, List<Level>> valid : getValidChoicesBuild()) {
                if (valid.getValue0().getX() == (x - 1) && valid.getValue0().getY() == (y - 1)) {
                    validCoordinates = true;
                    while (!ok) {
                        printer.println(printer.colorToAnsi(Color.CYAN) + "What level do you want to build ?");

                        for (Pair<Coordinates, List<Level>> validBuilds : getValidChoicesBuild())
                            if (validBuilds.getValue0().getX() == x - 1 && validBuilds.getValue0().getY() == y - 1)
                                for (Level validLevel : validBuilds.getValue1())
                                    printer.println(printer.colorToAnsi(Color.CYAN) + validLevel.toString());


                        try {
                            z = reader.read().toUpperCase();
                            while (z.equals("")) {
                                printer.println(printer.colorToAnsi(Color.RED) + "Don't press enter without write " +
                                        "something!" + printer.colorToAnsi(Color.CYAN));
                                z = reader.read().toUpperCase();
                            }
                            ok = true;
                            Level.valueOf(z);

                        } catch (IllegalArgumentException e) {

                            printer.println(printer.colorToAnsi(Color.RED) + "This level is NOT ACCEPTABLE !!");
                            printer.println("Enter a VALID level !!" + printer.colorToAnsi(Color.CYAN));
                        }
                    }
                    for (Pair<Coordinates, List<Level>> validBuilds : getValidChoicesBuild()) {
                        if (validBuilds.getValue0().getX() == (x - 1) && validBuilds.getValue0().getY() == (y - 1)) {
                            for (Level validLevel : validBuilds.getValue1()) {

                                if (validLevel == Level.valueOf(z)) {
                                    level = validLevel;
                                    coordinates = new Coordinates(x - 1, y - 1);

                                    notAvailable = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!notAvailable) {
                        printer.println(printer.colorToAnsi(Color.RED) + "This level is NOT AVAILABLE !!");
                        printer.println("Enter other coordinates !!" + printer.colorToAnsi(Color.CYAN));
                        notAvailable = false;
                    }
                    break;
                }

            }

            if(!validCoordinates)
                printer.println(printer.colorToAnsi(Color.RED) + "This Coordinates are NOT AVAILABLE !!" + printer.colorToAnsi(Color.CYAN));
        }
        buildOn = new Pair<>(coordinates,level);
        return buildOn;
    }

    public boolean makeDecision() throws InterruptedException {

        String answer="";
        ok = false;
        printer.println(printer.colorToAnsi(Color.GREEN) + getDecision());

        while(!ok){
            printer.println(printer.colorToAnsi(Color.GREEN) + "Enter Y for Yes or N for No !!");
            answer = reader.read().toUpperCase();
            if(!(answer.equals("Y")||answer.equals("N"))){
                printer.println(printer.colorToAnsi(Color.RED) + "This is NOT VALID !!");
            }else {
                ok = true;
            }
        }
        return answer.equals("Y");
    }

    public boolean undo(){
        printer.println(printer.colorToAnsi(Color.BRIGHT_GREEN)+"Do you confirm your choice, press Y for yes, " +
                "or N to abort and redo your last action" + printer.colorToAnsi(Color.CYAN));
        String userChoice;
        while (true) {
            try {
                userChoice = reader.read(5, TimeUnit.SECONDS);
                if (userChoice == null){
                    printer.println(printer.colorToAnsi(Color.RED) + "Time out, action automatically accepted" + printer.colorToAnsi(Color.CYAN));
                    return true;
                }else {
                    switch (userChoice.toLowerCase()) {
                        case "y":
                            printer.println(printer.colorToAnsi(Color.GREEN) + "Decided to accept" + printer.colorToAnsi(Color.CYAN));
                            return true;
                        case "n":
                            printer.println(printer.colorToAnsi(Color.RED) + "Decided to redo" + printer.colorToAnsi(Color.CYAN));
                            return false;
                        default:
                            printer.println(printer.colorToAnsi(Color.RED) + "Invalid input, please redo your selection" + printer.colorToAnsi(Color.CYAN));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void ack(){
        printer.println(printer.colorToAnsi(Color.PURPLE) + "Your "+getOperationConfirmed()+" event is received by server..."
                + printer.colorToAnsi(Color.CYAN));
    }
    public void unsuccessfulOperation(){
        printer.println(printer.colorToAnsi(Color.RED) + "Sorry , something went wrong server side...");
        printer.println(printer.colorToAnsi(Color.GREEN) + "Repeat your last action !" + printer.colorToAnsi(Color.CYAN));
    }
    public void winGame(){
        printer.println(printer.colorToAnsi(Color.GREEN) + "Congratulations , YOU WON !!");
    }

    public void loseGame(){
        printer.println(printer.colorToAnsi(Color.RED) + "Sorry , you lost ...");
    }
    public void disconnectGame(){
        printer.println(printer.colorToAnsi(Color.CYAN) + "The server is shutting down for some reasons or");
        printer.println("A player disconnected himself so everyone is disconnected..." + printer.colorToAnsi(Color.WHITE));
    }

    public void endGame(){
        printer.println(printer.colorToAnsi(Color.RED) + "End Game" + printer.colorToAnsi(Color.WHITE));
    }

    public void outOfRoom() {
        printer.println(printer.colorToAnsi(Color.RED) + "The game is already started, try again later..." + printer.colorToAnsi(Color.WHITE));
    }

    private int  coordinateCheck(String car) throws InterruptedException {
        ok = false;
        int coordinate = 0;
        while (!ok) {
            printer.print(printer.colorToAnsi(Color.CYAN) + "Enter the "+ car +" coordinate : ");
            try {
                coordinate = reader.readInt();

                if( coordinate<1 || coordinate>getMap().getMaxCoordinate()+1){
                    printer.println(printer.colorToAnsi(Color.RED) + "This Coordinate is  NOT VALID !!");
                    printer.println("Enter a VALID coordinate !! " + printer.colorToAnsi(Color.CYAN));
                }else {
                    ok = true;
                }

            } catch (NumberFormatException e) {
                printer.println(printer.colorToAnsi(Color.RED) + "What you entered is NOT OK !!");
                printer.println("Enter a number!!" + printer.colorToAnsi(Color.CYAN));
            }
        }
        return coordinate;
    }

    public WorkerColor chooseColor() throws InterruptedException {
        ok = false;
        String choice;
        WorkerColor userChoice = getAvailableColors().get(0);

        while (!ok) {

            printer.println(printer.colorToAnsi(Color.GREEN) + "What color do you want?");
            for (WorkerColor c: getAvailableColors()) {
                printer.println(printer.colorToAnsi( Color.valueOf(c.toString().toUpperCase()) ) + c.toString().toUpperCase());
            }

            printer.println(printer.colorToAnsi(Color.GREEN) + "Write the color you want:" + printer.colorToAnsi(Color.CYAN));
            choice = reader.read();

            for (WorkerColor c: getAvailableColors()) {
                if (choice.equalsIgnoreCase(c.toString())) {
                    userChoice = c;
                    ok = true;
                    break;
                }
            }

            if (!ok) {
                try {
                    WorkerColor.valueOf(choice);
                    printer.println(printer.colorToAnsi(Color.RED) + "This color exist but not in the available colors!!");
                } catch (IllegalArgumentException e) {
                    printer.println(printer.colorToAnsi(Color.RED) + "What your typed is not a color!");
                }
                printer.println("Write a color now please!!" + printer.colorToAnsi(Color.CYAN));
            }
        }

        return userChoice;
    }

}
