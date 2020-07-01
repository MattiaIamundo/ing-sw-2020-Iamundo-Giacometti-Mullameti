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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    private BufferedImage myImage;
    private String player;
    private Coordinates chosenCoordinates;
    private Pair<Coordinates, List<Level>> chosenPair;
    private Thread undoThread;
    private WorkerColor chosenColor;
    private Coordinates coordinates;
    private Pair<Coordinates, Level> build;
    private String firstPlayer;
    private Worker chosenWorker;
    private WinPanel winPanel;
    private LosePanel losePanel;
    int width;
    int height;
    GraphicsDevice gd;

    public Gui(Supporter supporter) {
        s = supporter;
        frame = new JFrame("Santorini");

        buttonNumber = 0;
        chosenGods = new ArrayList<>();
        undoThread = null;
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
        try {
            myImage = ImageIO.read(getClass().getResourceAsStream("/santoriniBoard.png"));
        } catch (IOException e) {
            e.printStackTrace();
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
        //frame.pack();
        frame.setSize(95*width/100,90*height/100);
        frame.setVisible(true);
        frame.setResizable(false);

    }

    public void logIn() {

        frame.getContentPane().removeAll();
        //frame.setSize(650, 650);

        LogInPanel logInPanel = new LogInPanel();
        JButton submitButton = logInPanel.getSubmitButton();

        if (!first) {
            logInPanel.setNicknameErr("Please insert a valid Nickname !");
        }
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButton.setEnabled(false);
                if (logInPanel.getNickname().equals("") || logInPanel.getNickname().contains(" ")) {
                    submitButton.setEnabled(true);
                    logInPanel.setNicknameError(true);
                } else {
                    logInPanel.setNicknameError(false);
                    first = false;
                    Thread nick = new Thread(() -> {
                        player = logInPanel.getNickname();
                        EventForFirstPhase eventNickname = new Nickname(logInPanel.getNickname());
                        s.notify(eventNickname);
                    });
                    nick.start();
                }
            }
        });

        frame.getContentPane().add(logInPanel);
        frame.setVisible(true);


    }

    public void numberOfPlayers() {
        frame.getContentPane().removeAll();
        //frame.setSize(635, 635);

        NrOfPlayersPanel nrOfPlayersPanel = new NrOfPlayersPanel();
        JButton[] nrButton = nrOfPlayersPanel.getNrButton();

        for (JButton button : nrButton) {
            button.addActionListener(e -> {
                for (int i = 0; i < 2; i++) {
                    if (e.getSource() == nrButton[i]) {
                        for (JButton jButton : nrButton)
                            jButton.setEnabled(false);
                        buttonNumber = i + 2;

                    }
                }
                Thread number = new Thread(() -> {
                    EventForFirstPhase eventNumberOfPlayers = new NumberOfPlayers(buttonNumber);
                    s.notify(eventNumberOfPlayers);
                });
                number.start();

            });
        }
        frame.getContentPane().add(nrOfPlayersPanel);
        frame.setVisible(true);
    }

    public void chooseColor() {
        frame.getContentPane().removeAll();
        //frame.setSize(1400, 800);

        ColorPanel colorPanel = new ColorPanel(myImage);
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
                            for (JButton button : colorButton) {
                                button.setEnabled(false);
                            }
                            chosenColor = s.getAvailableColors().get(i);


                            Thread color = new Thread(() -> {
                                EventForServer eventColor = new ColorChoice(chosenColor);
                                s.notify(eventColor);
                            });
                            color.start();
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
        //frame.setSize(1300, 700);

        chooseGodsPanel = new ChooseGodsPanel();
        JLabel label = chooseGodsPanel.getChooseGods();
        label.setText("Choose " + s.getGodsNum() + " Gods");
        JButton[] godButtons = chooseGodsPanel.getGodButtons();


        for (JButton button : godButtons) {
            if (button.getActionListeners().length > 0) {
                button.removeActionListener(button.getActionListeners()[0]);
            }
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 14; i++) {
                        if (e.getSource() == godButtons[i]) {

                            godButtons[i].setBorder(BorderFactory.createLineBorder(new Color(102, 0, 153)));
                            chosenGods.add(Gods.getGodFromString(godButtons[i].getText()));
                            godButtons[i].setEnabled(false);

                            if (chosenGods.size() == s.getGodsNum()) {
                                for(JButton god : godButtons){
                                    god.setEnabled(false);
                                }
                                Thread god = new Thread(() -> {
                                    EventForServer eventGodsDeck = new GodsDeck(chosenGods);
                                    s.notify(eventGodsDeck);
                                });
                                god.start();
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
        //frame.setSize(1300, 700);
        chooseGodsPanel = new ChooseGodsPanel();
        JLabel label = chooseGodsPanel.getChooseGods();
        label.setText("Choose a God");
        List<JButton> chosenButtons = new ArrayList<>();

        chosenGods = s.getAvailableGods();

        for (Gods god : Gods.values()) {
            if (s.getAvailableGods().contains(god)) {
                chosenButtons.add(chooseGodsPanel.getSpecificGod(god.ordinal()));
            } else {
                chooseGodsPanel.setGodUnavailable(god.ordinal());
            }
        }

        for (int i = 0; i < chosenButtons.size(); i++) {


            if (chosenButtons.get(i).getActionListeners().length > 0) {

                chosenButtons.get(i).removeActionListener(chosenButtons.get(i).getActionListeners()[0]);

            }

            chosenButtons.get(i).addActionListener(e -> {
                for (JButton chosenButton : chosenButtons) {
                    if (e.getSource() == chosenButton) {
                        for (JButton button : chosenButtons) {
                            button.setEnabled(false);
                        }
                        chosenGod = Gods.getGodFromString(chosenButton.getText());

                        Thread godChoice = new Thread(() -> {
                            EventForServer eventGodChoice = new GodChoice(chosenGod);
                            s.notify(eventGodChoice);
                        });
                        godChoice.start();

                    }


                }

            });

        }
        frame.getContentPane().add(chooseGodsPanel);
        frame.setVisible(true);

    }

    public void chooseFirstPlayer() {

        frame.getContentPane().removeAll();
        //frame.setSize(1400, 800);

        FirstPlayerPanel firstPlayerPanel = new FirstPlayerPanel(myImage);
        JButton[] playerButton = firstPlayerPanel.getPlayers();

        for (int i = 0; i < s.getPlayers().size(); i++) {
            playerButton[i].setText(s.getPlayers().get(i));
            playerButton[i].setVisible(true);
        }

        for (int i = 0; i < s.getPlayers().size(); i++) {
            playerButton[i].addActionListener(e -> {
                for (int i1 = 0; i1 < s.getPlayers().size(); i1++) {
                    if (e.getSource() == playerButton[i1]) {
                        for (JButton button : playerButton) {
                            button.setEnabled(false);
                        }
                        firstPlayer = s.getPlayers().get(i1);


                        Thread firstPlayerthread = new Thread(() -> {
                            EventForServer playerChoice = new FirstPlayerChoice(firstPlayer);
                            s.notify(playerChoice);
                        });
                        firstPlayerthread.start();
                    }

                }

            });
        }
        frame.getContentPane().add(firstPlayerPanel);
        frame.setVisible(true);
    }

    public void placeWorkers() {


        mapPanel.setChat("Place your " + s.getWorkerNum() + "ª worker");
        mapPanel.setWorkerBorder(s.getWorkerNum() - 1);
        boardButtons = board.getBoardButtons();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardButtons[j][i].setVisible(true);
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (boardButtons[j][i].getActionListeners().length > 0) {
                    boardButtons[j][i].removeActionListener(boardButtons[j][i].getActionListeners()[0]);

                }
                boardButtons[j][i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (e.getSource() == boardButtons[j][i]) {

                                    for (int col = 0; col < 5; col++)
                                        for (int row = 0; row < 5; row++)
                                            if (!boardButtons[row][col].equals(boardButtons[j][i]))
                                                boardButtons[row][col].setVisible(false);

                                    mapPanel.getUndoContainer().setVisible(true);
                                    getChoice("PLACEWORKER");
                                    coordinates = new Coordinates(i, j);
                                       /* Thread workerPos = new Thread(() -> {
                                            EventForServer eventWorkerPosition = new WorkerPosition(coordinates);
                                            s.notify(eventWorkerPosition);
                                        });
                                        workerPos.start();*/

                                }
                            }
                        }

                    }
                });
            }
        }
    }



    public void updateMap() throws OutOfMapException {

        frame.getContentPane().removeAll();
        //frame.setSize(1400, 800);


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
                    boardButtons[i][j].setLevel(map.getSquare(j, i).getLevel().ordinal() - 1);


                if (map.getSquare(j, i).isPresentWorker()) {
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
        boardButtons = board.getBoardButtons();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardButtons[j][i].setVisible(true);
            }
        }
        for (Worker worker : s.getValidChoicesWorkers()) {
            workerButtons.add(board.getSpecificButtons(worker.getPosition().getCoordinates().getX(), worker.getPosition().getCoordinates().getY()));
        }

        for (int i = 0; i < 2; i++) {
            workerButtons.get(i).setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

            if (workerButtons.get(i).getActionListeners().length > 0) {
                workerButtons.get(i).removeActionListener(workerButtons.get(i).getActionListeners()[0]);
            }
            workerButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    for (int i = 0; i < 2; i++) {

                        if (e.getSource() == workerButtons.get(i)) {
                            workerButtons.get(i).setBorder(BorderFactory.createLineBorder(Color.red, 2));
                            //chosenWorker = boardButtons[workerButtons.get(i).getX()][workerButtons.get(i).getY()];
                           /* for(int col=0 ;col<5 ; col++)
                                for(int row=0 ; row<5 ; row++)
                                    if(!boardButtons[row][col].equals(boardButtons[s.getValidChoicesWorkers().get(i).getPosition().getCoordinates().getX()][s.getValidChoicesWorkers().get(i).getPosition().getCoordinates().getY()]))
                                        boardButtons[row][col].setVisible(false);
*/
                            mapPanel.getUndoContainer().setVisible(true);
                            getChoice("CHOOSEWORKER");
                            chosenWorker = s.getValidChoicesWorkers().get(i);

                        }
                    }
                }
            });
        }
    }

    //frame.getContentPane().add(mapPanel);
    //frame.setVisible(true);




    public void askMove() {

        mapPanel.setChat("Move");

        List<BoardButton> availableMoveButtons = new ArrayList<>();
        boardButtons = board.getBoardButtons();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardButtons[j][i].setVisible(true);
            }
        }
        for (Coordinates coordinates : s.getValidChoicesMoves()) {
            availableMoveButtons.add(board.getSpecificButtons(coordinates.getX(), coordinates.getY()));
        }

        for (int i = 0; i < availableMoveButtons.size(); i++) {
            availableMoveButtons.get(i).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            System.out.println(availableMoveButtons.get(i).getActionListeners().length);
            if (availableMoveButtons.get(i).getActionListeners().length > 0) {
                availableMoveButtons.get(i).removeActionListener(availableMoveButtons.get(i).getActionListeners()[0]);
            }
            availableMoveButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    for (int i = 0; i < availableMoveButtons.size(); i++) {

                        if (e.getSource() == availableMoveButtons.get(i)) {

                            availableMoveButtons.get(i).setBorder(BorderFactory.createLineBorder(Color.red, 2));

                            for (BoardButton button : availableMoveButtons)
                                if (!button.equals(availableMoveButtons.get(i)))
                                    button.setVisible(false);

                            chosenCoordinates = s.getValidChoicesMoves().get(i);
                            mapPanel.getUndoContainer().setVisible(true);
                            getChoice("MOVE");
                        }
                    }
                }

            });
        }

    }



    public void askBuild() {

        mapPanel.setChat("BUILD");


        List<BoardButton> availableBuildButtons = new ArrayList<>();
        List<JLabel> availableLevels = new ArrayList<>();
        boardButtons = board.getBoardButtons();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardButtons[j][i].setVisible(true);
            }
        }

        for (Pair<Coordinates, List<Level>> validBuilds : s.getValidChoicesBuild()) {
            availableBuildButtons.add(board.getSpecificButtons(validBuilds.getValue0().getX(), validBuilds.getValue0().getY()));
        }

        for (int i = 0; i < availableBuildButtons.size(); i++) {
            availableBuildButtons.get(i).setBorder(BorderFactory.createLineBorder(new Color(255, 153, 0), 2));
            if (availableBuildButtons.get(i).getActionListeners().length > 0) {
                availableBuildButtons.get(i).removeActionListener(availableBuildButtons.get(i).getActionListeners()[0]);
            }
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
                                        for (JLabel availableLevel : availableLevels) {
                                            if (m.getSource() == availableLevel) {

                                                build = new Pair<>(chosenPair.getValue0(), Level.valueOf(availableLevel.getText().toUpperCase()));
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
        if (yes.getActionListeners().length > 1) {
            yes.removeActionListener(yes.getActionListeners()[0]);
        }
        yes.addActionListener(e -> {
            if (e.getSource() == yes) {
                Thread decisionYes = new Thread(() -> {
                    EventForServer eventDecisionTaken = new DecisionTaken(true);
                    s.notify(eventDecisionTaken);
                });
                decisionYes.start();
            }
        });

        if (no.getActionListeners().length > 1) {
            no.removeActionListener(no.getActionListeners()[0]);
        }
        no.addActionListener(e -> {
            if (e.getSource() == no) {
                Thread decisionNo = new Thread(() -> {
                    EventForServer eventDecisionTaken = new DecisionTaken(false);
                    s.notify(eventDecisionTaken);
                });
                decisionNo.start();
            }
        });

    }

    public void ack() {
        mapPanel.setChat("Your " + s.getOperationConfirmed() + " event is received by server...");
    }

    public void unsuccessfulOperation() {
        mapPanel.setChat("Sorry , something went wrong server side..." + "\n" + "Repeat your last action !");
    }

    public void winGame() throws IOException {
        frame.getContentPane().removeAll();
        frame.setSize(625 * 3 / 2, 415 * 3 / 2);
        Image winImage;

        winImage = ImageIO.read(getClass().getResourceAsStream("winBackground.jpg"));

        winPanel = new WinPanel(winImage);

        frame.getContentPane().add(winPanel);
        frame.setVisible(true);
    }

    public void loseGame() {
        frame.getContentPane().removeAll();
        frame.setSize(625 * 3 / 2, 415 * 3 / 2);

        LosePanel losePanel = new LosePanel();
        losePanel.getText().setText("YOU LOST !!!");
        frame.getContentPane().add(losePanel);
        frame.setVisible(true);
    }

    public void disconnectGame() {
        /*frame.getContentPane().removeAll();
        frame.setSize(625 * 3 / 2, 415 * 3 / 2);

        EndGamePanel disconnectGame = new EndGamePanel();
        disconnectGame.getText().setText("Game Disconnected !!!");
        frame.getContentPane().add(disconnectGame);
        frame.setVisible(true);*/
    }

    public void endGame() {
       winPanel.getGameEnded().setVisible(true);
       losePanel.getGameEnded().setVisible(true);
    }

    public void outOfRoom() {
        frame.getContentPane().removeAll();
        frame.setSize(625 * 3 / 2, 415 * 3 / 2);

        /*EndGamePanel outOfRoom = new EndGamePanel();
        outOfRoom.getText().setText("Out of Room");
        frame.getContentPane().add(outOfRoom);
        frame.setVisible(true);*/
    }


    public void turnIsEnd() {
        mapPanel.setChat("Your turn has ended !");
    }

    public void gameIsStarting() {
        //mapPanel.setChat( "The game is started!!");
    }



    private void getChoice(String methodName) {

        JButton yes = mapPanel.getUndoContainer().getYes();
        JButton no = mapPanel.getUndoContainer().getNo();
        mapPanel.getUndoContainer().getText().setText("Do you confirm your choice ?");

        Timer yesTimer = new Timer(5000, null);

        ActionListener yesActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yesTimer.stop();
                yes.removeActionListener(this);
                no.removeActionListener(no.getActionListeners()[0]);
                //here we have to handle the yes case...

                SwingUtilities.invokeLater( () -> mapPanel.getUndoContainer().setVisible(false) );

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
        };

        ActionListener noActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yesTimer.stop();
                yes.removeActionListener(yes.getActionListeners()[0]);
                no.removeActionListener(this);
                //here we have to handle the no case...

                SwingUtilities.invokeLater( () -> mapPanel.getUndoContainer().setVisible(false) );

                switch (methodName){
                    case "PLACEWORKER":
                        SwingUtilities.invokeLater( () -> { placeWorkers(); } );
                        break;
                    case "CHOOSEWORKER":
                        SwingUtilities.invokeLater( () -> { chooseWorker(); } );
                        break;
                    case "MOVE":
                        SwingUtilities.invokeLater( () -> { askMove(); } );
                        break;
                    case "BUILD":
                        SwingUtilities.invokeLater( () -> { askBuild(); } );
                        break;
                }
            }
        };

        undoThread = new Thread(() -> {

            yes.addActionListener(yesActionListener);

            yesTimer.addActionListener(yesActionListener);
            yesTimer.setRepeats(false);
            yesTimer.start();

            no.addActionListener(noActionListener);

        });

        undoThread.start();

    }


}

