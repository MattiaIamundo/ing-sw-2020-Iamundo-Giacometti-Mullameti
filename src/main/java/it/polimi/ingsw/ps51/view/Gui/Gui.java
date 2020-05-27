package it.polimi.ingsw.ps51.view.Gui;


import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.events.events_for_server.Build;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.view.Supporter;
import org.javatuples.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Gui {

    private JFrame frame;
    private boolean first = true;
    private BoardContainer board;
    private ChooseGodsPanel chooseGodsPanel;
    private MapPanel mapPanel;
    private int buttonNumber;
    private List<Gods> chosenGods;
    private Gods chosenGod;
    private Supporter s;
    private BoardButton[][] boardButtons;
    private ImageIcon workerPic;
    private ColorPanel colorPanel;
    private BufferedImage myImage;
    private String player;
    private Coordinates chosenCoordinates;
    private Pair<Coordinates, List<Level>> chosenPair;
    private Thread undoThread;

    private Coordinates coordinates;
    private Pair<Coordinates,Level> build;

    private Worker chosenWorker;

    public Gui(Supporter supporter) {
        s = supporter;
        frame = new JFrame("Santorini");

        buttonNumber = 0;
        chosenGods = new ArrayList<>();
        undoThread = null;

        try {
            myImage = ImageIO.read(new File("src/main/resources/SantoriniBoard.png"));
        } catch (IOException e) {

        }



    }


    public void launch() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int option = JOptionPane.showConfirmDialog(frame, "Do you want to Exit ?", "Exit Confirmation ", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION)
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if (option == JOptionPane.NO_OPTION)
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public void logIn() {

        frame.getContentPane().removeAll();
        frame.setSize(650, 650);

        LogInPanel logInPanel = new LogInPanel();
        JButton submitButton = logInPanel.getSubmitButton();
        BorderLayout borderLayout = new BorderLayout();
        if (!first) {
            logInPanel.setNicknameErr("Please insert a valid Nickname !");
        }
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logInPanel.getNickname().equals(""))
                    logInPanel.setNicknameError(true);
                else {
                    logInPanel.setNicknameError(false);
                    first = false;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            player = logInPanel.getNickname();
                            EventForFirstPhase eventNickname = new Nickname(logInPanel.getNickname());
                            s.notify(eventNickname);
                        }
                    });
                }
            }
        });

        frame.getContentPane().add(logInPanel);
        frame.setVisible(true);

    }


    public void numberOfPlayers() {
        frame.getContentPane().removeAll();
        frame.setSize(635, 635);

        NrOfPlayersPanel nrOfPlayersPanel = new NrOfPlayersPanel();
        JButton[] nrButton = nrOfPlayersPanel.getNrButton();
        BorderLayout borderLayout = new BorderLayout();

        for (JButton button : nrButton) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 2; i++) {
                        if (e.getSource() == nrButton[i]) {

                            buttonNumber = i + 2;

                        }
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            EventForFirstPhase eventNumberOfPlayers = new NumberOfPlayers(buttonNumber);
                            s.notify(eventNumberOfPlayers);
                        }
                    });

                }
            });
        }
        frame.getContentPane().add(nrOfPlayersPanel);
        frame.setVisible(true);
    }

    public void chooseColor() {
        frame.getContentPane().removeAll();
        frame.setSize(1400, 800);

        colorPanel = new ColorPanel(myImage);
        List<JButton> colorButton = new ArrayList<>();

        for (WorkerColor workerColor : s.getAvailableColors()) {
            colorButton.add(colorPanel.getSpecificButton(workerColor.ordinal()));
            colorPanel.getSpecificButton(workerColor.ordinal()).setVisible(true);
        }

        for (int i = 0; i < colorButton.size(); i++) {
            colorButton.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < colorButton.size(); i++) {
                        if (e.getSource() == colorButton.get(i)) {

                            //colorButton.get(i).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                            colorButton.get(i).setEnabled(false);
                            EventForServer eventColor = new ColorChoice(s.getAvailableColors().get(i));
                            s.notify(eventColor);
                        }


                    }


                }
            });
        }
        frame.getContentPane().add(colorPanel);
        frame.setVisible(true);
    }

    public void chooseGodsDeck() {
        frame.getContentPane().removeAll();
        frame.setSize(1300, 700);

        chooseGodsPanel = new ChooseGodsPanel();
        JLabel label = chooseGodsPanel.getChooseGods();
        label.setText("Choose " + s.getGodsNum() + " Gods");
        JButton[] godButtons = chooseGodsPanel.getGodButtons();


        for (JButton button : godButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 14; i++) {
                        if (e.getSource() == godButtons[i]) {
                            godButtons[i].setBorder(BorderFactory.createLineBorder(new Color(102, 0, 153)));
                            chosenGods.add(Gods.getGodFromString(godButtons[i].getText()));
                            godButtons[i].setEnabled(false);
                            if (chosenGods.size() == s.getGodsNum()) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        EventForServer eventGodsDeck = new GodsDeck(chosenGods);
                                        s.notify(eventGodsDeck);
                                    }
                                });
                            }
                        }
                    }

                }
            });
        }
        frame.getContentPane().add(chooseGodsPanel);
        frame.setVisible(true);

    }

    public void chooseGodsPlayers() {
        frame.getContentPane().removeAll();
        frame.setSize(1300, 700);
        chooseGodsPanel = new ChooseGodsPanel();
        JLabel label = chooseGodsPanel.getChooseGods();
        label.setText("Choose a God");
        List<JButton> chosenButtons = new ArrayList<>();

        chosenGods = s.getAvailableGods();

        for (JButton button : chooseGodsPanel.getGodButtons()) {
            button.setVisible(true);
        }

        for (Gods god : s.getAvailableGods()) {
            chosenButtons.add(chooseGodsPanel.getSpecificGod(god.ordinal()));
            chooseGodsPanel.setGodBorder(god.ordinal());
        }

        for (int i = 0; i < chosenButtons.size(); i++) {
            chosenButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < chosenButtons.size(); i++) {
                        if (e.getSource() == chosenButtons.get(i)) {

                            chosenGod = Gods.getGodFromString(chosenButtons.get(i).getText());

                        }

                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            EventForServer eventGodChoice = new GodChoice(chosenGod);
                            s.notify(eventGodChoice);
                        }
                    });

                }
            });
        }
        frame.getContentPane().add(chooseGodsPanel);
        frame.setVisible(true);


    }

    public void placeWorkers() {



        mapPanel.setChat("Place your " + s.getWorkerNum() + "ª worker");
        mapPanel.setWorkerBorder(s.getWorkerNum() - 1);
        workerPic = mapPanel.getWorkerImages(s.getChosenColors().get(player).toString());
        boardButtons = board.getBoardButtons();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardButtons[j][i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (e.getSource() == boardButtons[j][i]) {
                                    //boardButtons[i][j].setWorker(workerPic);
                                    mapPanel.getUndoContainer().setVisible(true);
                                    getChoice("PLACEWORKER");
                                    coordinates = new Coordinates(i, j);


                                }
                            }
                        }

                    }
                });
            }
        }



        //frame.getContentPane().add(mapPanel);
        //frame.setVisible(true);


    }
    public void updateMap() throws OutOfMapException {

        frame.getContentPane().removeAll();
        frame.setSize(1400, 800);


        mapPanel = new MapPanel(myImage);
        board = mapPanel.getBoardContainer();
        mapPanel.setChat("Update map");

        boardButtons = board.getBoardButtons();

        Map map = s.getMap();
        List<Worker> workerList = s.getWorkers();


        java.util.Map<String, WorkerColor> chosenColors = s.getChosenColors();
        List<Pair<String, Gods>> chosenGods = s.getChosenGods();

        int maxCoordinate = map.getMaxCoordinate() + 1;


        mapPanel.setPlayerName(player, 0, chosenColors.get(player).toString());
        int k = 1;
        for (Pair<String, Gods> pair : chosenGods) {

            if (!pair.getValue0().equals(player)) {
                System.out.println(k);
                mapPanel.setPlayerName(pair.getValue0(), k, chosenColors.get(pair.getValue0()).toString());
                k = 2;

            }

        }


        k = 1;

        for (Pair<String, Gods> god : chosenGods) {
            if (god.getValue0().equals(player))
                mapPanel.setGodPic(god.getValue1().toString(), 0);
            else {
                mapPanel.setGodPic(god.getValue1().toString(), k);
                k = 2;
            }
        }

        mapPanel.setWorkerImages(chosenColors.get(player).toString());


        for (int i = 0; i < maxCoordinate; i++) {
            for (int j = 0; j < maxCoordinate; j++) {

                if (map.getSquare(j, i).getLevel().ordinal() > 0)
                    boardButtons[i][j].setLevel(map.getSquare(j,i).getLevel().ordinal() - 1);


                if (map.getSquare(j,i).isPresentWorker()) {
                    System.out.println("x " + map.getSquare(i,j).getCoordinates().getX() +" y "+ map.getSquare(i,j).getCoordinates().getY() + "i"+ i + "j"+j);
                    for (Worker worker : workerList) {
                        if (worker.getPosition().getCoordinates().getX() == j && worker.getPosition().getCoordinates().getY() == i) {
                            for (Pair<String, Gods> pair : chosenGods)
                                if (worker.getNamePlayer().equals(pair.getValue0())) {
                                    boardButtons[i][j].setWorker(mapPanel.getWorkerImages(s.getChosenColors().get(pair.getValue0()).toString()));
                                }
                        }


                    }
                }

            }
        }

        frame.getContentPane().add(mapPanel);
        frame.setVisible(true);


    }


    public void chooseWorker() {

        //frame.getContentPane().removeAll();
        //frame.setSize(1400, 800);

        //mapPanel = new MapPanel(myImage);
        //board = mapPanel.getBoardContainer();
        mapPanel.setChat("Choose Worker");
        mapPanel.getWorkerContainer().setVisible(false);
        List<BoardButton> workerButtons = new ArrayList<>();

        for (Worker worker : s.getValidChoicesWorkers()) {
            workerButtons.add(board.getSpecificButtons(worker.getPosition().getCoordinates().getX(), worker.getPosition().getCoordinates().getY()));
        }

        for (int i = 0; i < 2; i++) {
            workerButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    for (int i = 0; i < 2; i++) {

                        if (e.getSource() == workerButtons.get(i)) {
                            workerButtons.get(i).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                            //chosenWorker = boardButtons[workerButtons.get(i).getX()][workerButtons.get(i).getY()];

                            mapPanel.getUndoContainer().setVisible(true);
                            getChoice("CHOOSEWORKER");
                            chosenWorker=s.getValidChoicesWorkers().get(i);

                        }
                    }
                }
            });
        }

        //frame.getContentPane().add(mapPanel);
        //frame.setVisible(true);

    }

    public void askMove() {

        //frame.getContentPane().removeAll();
        //frame.setSize(1400, 800);

        //mapPanel = new MapPanel(myImage);
        //board = mapPanel.getBoardContainer();
        mapPanel.setChat("Move");

        List<BoardButton> availableMoveButtons = new ArrayList<>();


        for (Coordinates coordinates : s.getValidChoicesMoves()) {
            availableMoveButtons.add(board.getSpecificButtons(coordinates.getX(), coordinates.getY()));
        }

        for (int i = 0; i < availableMoveButtons.size(); i++) {
            availableMoveButtons.get(i).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));

            availableMoveButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    for (int i = 0; i < availableMoveButtons.size(); i++) {

                        if (e.getSource() == availableMoveButtons.get(i)) {
                            // for(int j=0 ; j<availableMoveButtons.size() ;j++)
                            //   availableMoveButtons.get(i).setBorder(null);

//                            chosenWorker.setBorder(null);
                            availableMoveButtons.get(i).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                            //availableMoveButtons.get(i).setWorker(chosenWorker.getWorker().);
                            chosenCoordinates = s.getValidChoicesMoves().get(i);
                            mapPanel.getUndoContainer().setVisible(true);
                            getChoice("MOVE");
                        }
                    }
                }
            });
        }

        //frame.getContentPane().add(mapPanel);
        //frame.setVisible(true);
    }

    public void askBuild() {

        mapPanel.setChat("BUILD");


        List<BoardButton> availableBuildButtons = new ArrayList<>();
        List<JLabel> availableLevels = new ArrayList<>();


        for (Pair<Coordinates, List<Level>> validBuilds : s.getValidChoicesBuild()) {
            availableBuildButtons.add(board.getSpecificButtons(validBuilds.getValue0().getX(), validBuilds.getValue0().getY()));
        }

        for (int i = 0; i < availableBuildButtons.size(); i++) {
            availableBuildButtons.get(i).setBorder(BorderFactory.createLineBorder(new Color(255, 153, 0), 2));

            availableBuildButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    for (int i = 0; i < availableBuildButtons.size(); i++) {

                        if (e.getSource() == availableBuildButtons.get(i)) {

                            availableBuildButtons.get(i).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                            chosenPair = s.getValidChoicesBuild().get(i);

                            for (Pair<Coordinates, List<Level>> validBuilds : s.getValidChoicesBuild()) {

                                if (validBuilds.getValue0().equals(chosenPair.getValue0())) {

                                    for (Level validLevel : validBuilds.getValue1()) {
                                        mapPanel.setLevelImages(validLevel.ordinal() - 1);
                                        availableLevels.add(mapPanel.getLevel(validLevel.ordinal() - 1));
                                    }
                                }
                            }

                            for (int j = 0; j < availableLevels.size(); j++) {
                                availableLevels.get(j).addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent m) {
                                        for (int j = 0; j < availableLevels.size(); j++) {
                                            if (m.getSource() == availableLevels.get(j)) {

                                                build = new Pair<>(chosenPair.getValue0(), Level.valueOf(availableLevels.get(j).getText().toUpperCase()));
                                                mapPanel.getUndoContainer().setVisible(true);
                                                getChoice("BUILD");

                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });


        }


    }


    public void makeDecision() {
        mapPanel.makeDecision(s.getDecision());

        JButton yes = mapPanel.getYes();
        JButton no = mapPanel.getNo();
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == yes) {
                    EventForServer eventDecisionTaken = new DecisionTaken(true);
                    s.notify(eventDecisionTaken);
                }
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == no) {
                    EventForServer eventDecisionTaken = new DecisionTaken(false);
                    s.notify(eventDecisionTaken);
                }
            }
        });
    }

    public void ack() {
        mapPanel.setChat("Your " + s.getOperationConfirmed() + " event is received by server...");
    }

    public void unsuccessfulOperation() {
        mapPanel.setChat("Sorry , something went wrong server side..."
                + "Repeat your last action !");
    }

    public void winGame() throws IOException {
        frame.getContentPane().removeAll();
        frame.setSize(625 * 3 / 2, 415 * 3 / 2);
        Image winImage;

        winImage = ImageIO.read(new File("src/main/resources/winBackground.png"));


        WinPanel winPanel = new WinPanel(winImage);

        frame.getContentPane().add(winPanel);
        frame.setVisible(true);
    }

    public void loseGame() {
        frame.getContentPane().removeAll();
        frame.setSize(625 * 3 / 2, 415 * 3 / 2);

        EndGamePanel losePanel = new EndGamePanel();
        losePanel.getText().setText("YOU LOST !!!");
        frame.getContentPane().add(losePanel);
        frame.setVisible(true);
    }

    public void disconnectGame() {
        frame.getContentPane().removeAll();
        frame.setSize(625 * 3 / 2, 415 * 3 / 2);

        EndGamePanel disconnectGame = new EndGamePanel();
        disconnectGame.getText().setText("Game Disconnected !!!");
        frame.getContentPane().add(disconnectGame);
        frame.setVisible(true);
    }

    public void endGame() {
        frame.getContentPane().removeAll();
        frame.setSize(625 * 3 / 2, 415 * 3 / 2);

        EndGamePanel endGamePanel = new EndGamePanel();
        endGamePanel.getText().setText("Game Ended");
        frame.getContentPane().add(endGamePanel);
        frame.setVisible(true);
    }

    public void outOfRoom() {
        frame.getContentPane().removeAll();
        frame.setSize(625 * 3 / 2, 415 * 3 / 2);

        EndGamePanel outOfRoom = new EndGamePanel();
        outOfRoom.getText().setText("Out of Room");
        frame.getContentPane().add(outOfRoom);
        frame.setVisible(true);
    }


    public void turnIsEnd() {
        mapPanel.setChat("Your turn has ended !");
    }

    public void gameIsStarting() {
        //mapPanel.setChat( "The game is started!!");
    }



    private void getChoice(String methodName) {

        ThreadFututre tf = new ThreadFututre();
        Future<String> f = tf.getFutureString();
        JButton yes = mapPanel.getUndoContainer().getYes();
        JButton no = mapPanel.getUndoContainer().getNo();
        mapPanel.getUndoContainer().getText().setText("Do you confirm your choice ?");

        undoThread = new Thread(new Runnable() {
            @Override
            public void run() {


                    try {
                        if(f.get(10, TimeUnit.SECONDS).equals("YES")) {
                            switch (methodName) {
                                case "PLACEWORKER":
                                    EventForServer eventWorkerPosition = new WorkerPosition(coordinates);
                                    s.notify(eventWorkerPosition);
                                    break;
                                case "CHOOSEWORKER":
                                    EventForServer eventWorkerChoice = new WorkerChoice(chosenWorker);
                                    s.notify(eventWorkerChoice);
                                    break;
                                case "MOVE":
                                    EventForServer eventMoveChoice = new MoveChoice(chosenCoordinates);
                                    s.notify(eventMoveChoice);
                                    break;
                                case "BUILD":
                                    EventForServer eventBuild = new Build(build);
                                    s.notify(eventBuild);
                                    break;
                            }
                        }

                    } catch (TimeoutException e) {
                        e.printStackTrace();
                        switch (methodName) {
                            case "PLACEWORKER":
                                EventForServer eventWorkerPosition = new WorkerPosition(coordinates);
                                s.notify(eventWorkerPosition);
                                break;
                            case "CHOOSEWORKER":
                                EventForServer eventWorkerChoice = new WorkerChoice(chosenWorker);
                                s.notify(eventWorkerChoice);
                                break;
                            case "MOVE":
                                EventForServer eventMoveChoice = new MoveChoice(chosenCoordinates);
                                s.notify(eventMoveChoice);
                                break;
                            case "BUILD":
                                EventForServer eventBuild = new Build(build);
                                s.notify(eventBuild);
                                break;
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                tf.getEx().shutdown();
            }
        });

        undoThread.start();


        yes.addActionListener(e -> {

            tf.setResult("YES");
            tf.setBool(true);
        });


        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setResult("NO");
                tf.setBool(true);

                    mapPanel.getUndoContainer().setVisible(false);
                    switch (methodName){
                        case "PLACEWORKER":
                            placeWorkers();
                            break;
                        case "CHOOSEWORKER":
                            chooseWorker();
                            break;
                        case "MOVE":
                            askMove();
                            break;
                        case "BUILD":
                            askBuild();
                            break;
                    }

            }
        });


    }


}

