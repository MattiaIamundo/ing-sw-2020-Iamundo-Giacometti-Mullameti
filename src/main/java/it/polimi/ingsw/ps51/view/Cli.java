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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Cli extends Supporter {

    private boolean ok;
    boolean isFinish;
    MessageHandler mh;
    Printer printer;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    Scanner reader = new Scanner(bufferedReader);


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
                        default:
                            ok = false;
                            break;

                    }
                } catch (InterruptedException | ExecutionException | OutOfMapException e) {
                    ok = false;
                }
            }
        }
        this.mh.getEx().shutdown();
    }

    private void turnIsEnd() {
        printer.println(printer.colorToAnsi(Color.PURPLE) + "Your turn has ended !");
    }

    private void gameIsStarting(){
        printer.println(printer.colorToAnsi(Color.GREEN) + "The game is started!!");
    }

    public void launch(){
        printer.welcome();
    }

    public String logIn(){

        String nickname = "";
        ok = false;

        while(!ok){
            printer.println(printer.colorToAnsi(Color.GREEN)+"Insert your Nickname:");
            ok = true;
            nickname = reader.nextLine();

            if ( nickname.contains(" ") ) {
                printer.println(printer.colorToAnsi(Color.RED)+"Write something without space...");
                ok = false;
            }
            if ( nickname.equals("")) {
                printer.println(printer.colorToAnsi(Color.RED)+"Write something...");
                ok = false;
            }
        }

        return nickname;
    }

    public int numberOfPlayers(){

        int numberOfPlayers = 0;
        ok = false;
        printer.println(printer.colorToAnsi(Color.BLUE) + "Choose the number of players !");
        printer.println(printer.colorToAnsi(Color.BLUE) + "2 players or 3 players ? ");

        while(!ok){
            reader.reset();
            try{
                numberOfPlayers=reader.nextInt();
                ok = true;

                if(numberOfPlayers!=2 && numberOfPlayers!=3){
                    printer.println(printer.colorToAnsi(Color.RED) + "You can only have 2 or 3 players!!");
                    printer.println(printer.colorToAnsi(Color.RED) + "Enter 2 or 3 !!");
                    ok = false;
                }

            }catch(InputMismatchException e){
                printer.println(printer.colorToAnsi(Color.RED) + "What you entered is NOT OK !!");
                printer.println(printer.colorToAnsi(Color.RED) + "Enter a number!!");
                reader.nextLine();

            }
        }
        reader.nextLine();

        return numberOfPlayers;
    }

    public List<Gods> chooseGodsDeck() {

        List<Gods> chosenGods = new ArrayList<>();
        String chosenGod;
        printer.printDeck();
        printer.println(printer.colorToAnsi(Color.BLUE) + "You must choose only " + getGodsNum() + " Gods !!");

        for (int i = 0; i < getGodsNum(); i++) {
            ok = false;
            printer.println(printer.colorToAnsi(Color.BLUE) + "Enter the name of the " + (i + 1) + "º God?");
            while (!ok) {
                try {
                    chosenGod = reader.nextLine();
                    chosenGod=chosenGod.toUpperCase();
                    ok = true;
                    if((chosenGods.isEmpty()) || (!(chosenGods.contains(Gods.valueOf(chosenGod))))){
                        chosenGods.add(Gods.valueOf(chosenGod));
                    }else{
                        printer.println(printer.colorToAnsi(Color.RED) + "You already chose that one !!");
                        printer.println(printer.colorToAnsi(Color.RED) + "Choose another!!");
                        ok = false;
                    }

                } catch (IllegalArgumentException e) {
                    printer.println(printer.colorToAnsi(Color.RED) + "This name is NOT ACCEPTABLE !!");
                    printer.println(printer.colorToAnsi(Color.RED) + "Enter a VALID name of a  God !!");
                    ok = false;
                }
            }
        }

        return chosenGods;
    }

    public Gods chooseGodsPlayers(){

        Gods chosenGod = null;
        String chosen;
        ok=false;
        printer.printGods(getAvailableGods());

        printer.println(printer.colorToAnsi(Color.BLUE) + "You must choose only 1 God !!");
        printer.println(printer.colorToAnsi(Color.BLUE) + "Type the name RIGHT !!");
        while(!ok){

            try{
                ok = true;
                chosen = reader.nextLine();
                chosen = chosen.toUpperCase();
                if(getAvailableGods().contains(Gods.valueOf(chosen)))
                    chosenGod = Gods.valueOf(chosen);
                else {
                    printer.println(printer.colorToAnsi(Color.RED) + "The God you choose is NOT AVAILABLE !!");
                    printer.println(printer.colorToAnsi(Color.RED) + "Choose one of the given Gods!!");
                    ok = false;
                }

            }catch (IllegalArgumentException e){

                printer.println(printer.colorToAnsi(Color.RED) + "This name is NOT ACCEPTABLE !!");
                printer.println(printer.colorToAnsi(Color.RED) + "Enter a VALID name of a  God !!");
                ok = false;
            }
        }

        return chosenGod;
    }

    public void updateMap() throws OutOfMapException {

        printer.board(getMap() , getWorkers() ,getChosenGods());
    }

    public Coordinates placeWorkers(){

        Coordinates coordinates;
        int x;
        int y;


        printer.println(printer.colorToAnsi(Color.BLUE) + "Where do you want to place your " + getWorkerNum() + "º worker ?");
        printer.println(printer.colorToAnsi(Color.BLUE) + "Enter VALID coordinates !! ");

        x = coordinateCheck("X");


        y = coordinateCheck("Y");

        coordinates = new Coordinates(x-1,y-1);
        return coordinates;
    }

    //da mettere apposto il fatto che non sempre ci sono due worker disponibili a muoversi
    //a volte uno dei due non può muoversi ma ciò non vuol dire che il giocatore abbia perso
    //infatti può continuare a giocare anche se può muovere e costruire con solo uno dei suoi worker
    public Worker chooseWorker(){
        int choice = 0;
        Worker worker;
        ok =  false;
        int count = 1;
        printer.println(printer.colorToAnsi(Color.BLUE) + "Which worker do you want to use ?");
        for(Worker workers : getValidChoicesWorkers()) {
            printer.println(printer.colorToAnsi(Color.BLUE) + count +".The Worker in position [" + (workers.getPosition().getCoordinates().getX() + 1) + "]" +
                    "[" + (workers.getPosition().getCoordinates().getY() + 1) + "]");
            count = count + 1;
        }
        while(!ok){
            try{
                choice = reader.nextInt();
                ok = true;
                if(choice!=1 && choice!=2){
                    printer.println(printer.colorToAnsi(Color.BLUE) + "Enter a VALID number !");
                    printer.println(printer.colorToAnsi(Color.BLUE) + "Enter 1 or 2 !!");
                    ok = false;
                }
            }catch(InputMismatchException e) {
                printer.println(printer.colorToAnsi(Color.RED) + "What you entered is NOT OK !!");
                printer.println(printer.colorToAnsi(Color.RED) + "Enter a number!!");
                reader.nextLine();
            }
        }
        if(getValidChoicesWorkers().size()==1)
            worker = getValidChoicesWorkers().get(choice);
        else
            worker = getValidChoicesWorkers().get(choice-1);
        return worker;
    }

    public Coordinates askMove() {

        int x = 0;
        int y = 0;
        Coordinates coordinates;
        boolean notAvailable = false;
        ok = false;
        printer.println(printer.colorToAnsi(Color.BLUE) + "Where do you want your worker to move ?");
        printer.println(printer.colorToAnsi(Color.BLUE) + "These are the available coordinates :");
        for(Coordinates coord : getValidChoicesMoves()){
            printer.print(printer.colorToAnsi(Color.BLUE) + " [" + (coord.getX()+1) + " , " + (coord.getY()+1) +"]");
        }
        printer.println("");
        while (!notAvailable) {

            x = coordinateCheck("X");


            y = coordinateCheck("Y");

            for(Coordinates coord : getValidChoicesMoves()){
                if(coord.getX()==(x-1) && coord.getY()==(y-1)){
                    notAvailable = true;
                    break;
                }

            }

            if(!notAvailable){
                printer.println(printer.colorToAnsi(Color.RED) + "This coordinates are not available");
                printer.println(printer.colorToAnsi(Color.BLUE) + "Enter valid Coordinates");
            }
        }
        coordinates = new Coordinates(x-1,y-1);
        return coordinates;
    }

    //mettere apposto il fatto che se scrivo un livello diverso da quelli che posso costruire, si blocca tutto
    //togliere il ramo else o meglio non far stampare perché se ho first and dome, e scelgo dome, il livello è valido
    public Pair<Coordinates, Level> askBuild(){

        int x;
        int y;
        String z = "";
        boolean notAvailable = false;
        Coordinates coordinates = null;
        Level level = null ;
        boolean validCoordinates = false;
        Pair<Coordinates, Level> buildOn;


        printer.println(printer.colorToAnsi(Color.BLUE) + "Where do you want your worker to build ?");
        printer.println(printer.colorToAnsi(Color.BLUE) + "These are the available coordinates :");
        for(Pair<Coordinates, List<Level>> validBuilds : getValidChoicesBuild()){
            for(Level l : validBuilds.getValue1())
                printer.print(printer.colorToAnsi(Color.BLUE) + " [" + (validBuilds.getValue0().getX() + 1) + " , " + (validBuilds.getValue0().getY() + 1) + " , " + l +"]");
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
                        printer.println(printer.colorToAnsi(Color.BLUE) + "What level do you want to build ?");

                        for (Pair<Coordinates, List<Level>> validBuilds : getValidChoicesBuild())
                            if (validBuilds.getValue0().getX() == x - 1 && validBuilds.getValue0().getY() == y - 1)
                                for (Level validLevel : validBuilds.getValue1())
                                    printer.println(printer.colorToAnsi(Color.BLUE) + validLevel.toString());


                        try {
                            z = reader.nextLine();
                            while (z.equals("")) {
                                System.out.println("Don't press enter without write something!");
                                z = reader.nextLine();
                            }
                            z = z.toUpperCase();
                            ok = true;
                            Level.valueOf(z);

                        } catch (IllegalArgumentException e) {

                            printer.println(printer.colorToAnsi(Color.RED) + "This level is NOT ACCEPTABLE !!");
                            printer.println(printer.colorToAnsi(Color.RED) + "Enter a VALID level !!");
                            ok = false;
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
                        printer.println(printer.colorToAnsi(Color.RED) + "Enter other coordinates !!");
                        notAvailable = false;
                    }
                    break;
                }

            }

            if(!validCoordinates)
                printer.println(printer.colorToAnsi(Color.RED) + "This Coordinates are NOT AVAILABLE !!");
        }
        buildOn = new Pair<>(coordinates,level);
        return buildOn;
    }

    public boolean makeDecision(){

        String answer="";
        ok = false;
        printer.println(printer.colorToAnsi(Color.GREEN)+getDecision());

        while(!ok){
            printer.println(printer.colorToAnsi(Color.GREEN)+"Enter Y for Yes or N for No !!");
            answer = reader.nextLine();
            answer = answer.toUpperCase();
            ok = true;
            if(!(answer.equals("Y")||answer.equals("N"))){
                printer.println(printer.colorToAnsi(Color.GREEN)+"This is NOT VALID !!");
                ok = false;
            }
        }
        return answer.equals("Y");


    }
    public void ack(){
        printer.println(printer.colorToAnsi(Color.PURPLE)+"Your "+getOperationConfirmed()+" event is received by server...");
    }
    public void unsuccessfulOperation(){
        printer.println(printer.colorToAnsi(Color.RED) + "Sorry , something went wrong server side...");
        printer.println(printer.colorToAnsi(Color.GREEN) + "Repeat your last action !");
    }
    public void winGame(){
        printer.println(printer.colorToAnsi(Color.GREEN) + "Congratulations , YOU WON !!");
    }

    public void loseGame(){
        printer.println(printer.colorToAnsi(Color.RED) + "Sorry , you lost ...");
    }
    public void disconnectGame(){
        printer.println(printer.colorToAnsi(Color.BLUE) + "The server is shutting down for some reasons or");
        printer.println(printer.colorToAnsi(Color.BLUE) + "A player disconnected himself so everyone is disconnected...");
    }

    public void endGame(){
        printer.println(printer.colorToAnsi(Color.RED) + "End Game");
    }

    public void outOfRoom() {
        printer.println(printer.colorToAnsi(Color.RED) + "The game is already started, try again later...");
    }

    private int  coordinateCheck(String car){
        ok = false;
        int coordinate = 0;
        while (!ok) {
            printer.print(printer.colorToAnsi(Color.BLUE) + "Enter the "+ car +" coordinate : ");
            try {
                coordinate = reader.nextInt();
                ok = true;

                if( coordinate<1 || coordinate>getMap().getMaxCoordinate()+1){
                    printer.println(printer.colorToAnsi(Color.BLUE) + "This Coordinate is  NOT VALID !!");
                    printer.println(printer.colorToAnsi(Color.BLUE) + "Enter a VALID coordinate !! ");
                    ok = false;
                }

                reader.nextLine();
            } catch (InputMismatchException e) {
                printer.println(printer.colorToAnsi(Color.RED) + "What you entered is NOT OK !!");
                printer.println(printer.colorToAnsi(Color.RED) + "Enter a number!!");
                reader.nextLine();
            }
        }
        return coordinate;
    }

}
