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

    private BufferedImage myImage;
    private String player;
    private BoardButton chosenButton;
    private Coordinates chosenCoordinates;
    private Pair<Coordinates, List<Level>> chosenPair;
    private Level chosenLevel;

    public Gui(Supporter supporter) {
        s = supporter;
        frame = new JFrame("Santorini");
        chooseGodsPanel = new ChooseGodsPanel();
        buttonNumber = 0;
        chosenGods = new ArrayList<>();

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
        frame.setSize(635, 635);

        LogInPanel logInPanel = new LogInPanel();
        JButton submitButton = logInPanel.getSubmitButton();
        BorderLayout borderLayout = new BorderLayout();
        if (!first) {
            logInPanel.setNicknameErr("Please insert another Nickname :");
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

    public void chooseGodsDeck() {
        frame.getContentPane().removeAll();
        frame.setSize(1300, 700);


        JLabel label = chooseGodsPanel.getChooseGods();
        label.setText("Choose " + s.getGodsNum() + " Gods");
        JButton[] godButtons = chooseGodsPanel.getGodButtons();


        for (JButton button : godButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 9; i++) {
                        if (e.getSource() == godButtons[i]) {
                            godButtons[i].setBorder(BorderFactory.createLineBorder(new Color(102, 0, 153)));
                            chosenGods.add(Gods.getGodFromString(godButtons[i].getText()));

                            if (chosenGods.size() == s.getGodsNum()) {
                                for (JButton button1 : godButtons)
                                    button1.setEnabled(false);
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

        JLabel label = chooseGodsPanel.getChooseGods();
        label.setText("Choose a God");
        JButton[] godButtons = chooseGodsPanel.getGodButtons();

        chosenGods = s.getAvailableGods();

        for (JButton button : godButtons) {
            button.setEnabled(false);
        }

        for (JButton button : godButtons) {
            for (Gods god : chosenGods) {
                if (button.getText().equals(god.toString()))
                    button.setEnabled(true);
            }
        }

        for (JButton button : godButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 9; i++) {
                        if (e.getSource() == godButtons[i]) {

                            chosenGod = Gods.getGodFromString(godButtons[i].getText());

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

    public void placeWorkers() throws IOException {

        mapPanel.setChat("Place your " + s.getWorkerNum() + "ª worker");
        mapPanel.setWorkerImages(s.getWorkerNum() - 1);
        workerPic = mapPanel.getWorkerImages(s.getWorkerNum() - 1);
        boardButtons = board.getBoardButtons();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (e.getSource() == boardButtons[i][j]) {
                                    boardButtons[i][j].setWorker(workerPic);

                                    EventForServer eventWorkerPosition = new WorkerPosition(new Coordinates(i, j));
                                    s.notify(eventWorkerPosition);

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
        List<Pair<String, Gods>> chosenGods = s.getChosenGods();


        ArrayList<String> players = new ArrayList<>();
        int maxCoordinate = map.getMaxCoordinate() + 1;


        for (Worker worker : workerList) {
            if (!players.contains(worker.getNamePlayer()))
                players.add(worker.getNamePlayer());
        }


        if (!chosenGods.isEmpty()) {
            for (int i = 0; i < chosenGods.size(); i++) {
                mapPanel.setPlayerName(players.get(i), i);
                mapPanel.setGodPic(chosenGods.get(i).getValue1().toString(), i);
            }
        }


        workerPic = mapPanel.getWorkerImages(1);

        for (int i = 0; i < maxCoordinate; i++) {
            for (int j = 0; j < maxCoordinate; j++) {

                if (map.getSquare(j, i).getLevel().ordinal() > 0)
                    boardButtons[j][i].setLevel(map.getSquare(j, i).getLevel().ordinal() - 1);


                if (map.getSquare(j, i).isPresentWorker()) {
                    boardButtons[j][i].setWorker(workerPic);
                   /* for (Worker worker : workerList) {
                        if (worker.getPosition().getCoordinates().getX() == j && worker.getPosition().getCoordinates().getY() == i) {
                            if (worker.getNamePlayer().equals(players.get(0)))
                                boardButtons[i][j].setWorker(workerPic);
                            else if (worker.getNamePlayer().equals(players.get(1)))
                            //
                            else
                            //
                        }*/
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
                            EventForServer eventWorkerChoice = new WorkerChoice(s.getValidChoicesWorkers().get(i));
                            s.notify(eventWorkerChoice);
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
                            EventForServer eventMoveChoice = new MoveChoice(chosenCoordinates);
                            s.notify(eventMoveChoice);
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

                                                EventForServer eventBuild = new Build(new Pair<>(chosenPair.getValue0(), Level.valueOf(availableLevels.get(j).getText().toUpperCase())));
                                                s.notify(eventBuild);

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


}