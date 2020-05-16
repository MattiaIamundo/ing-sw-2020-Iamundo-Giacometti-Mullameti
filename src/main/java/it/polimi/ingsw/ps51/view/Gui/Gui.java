package it.polimi.ingsw.ps51.view.Gui;


import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.Coordinates;
import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.view.Supporter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gui  {

    private JFrame frame ;
    private boolean first = true;
    private ChooseGodsPanel chooseGodsPanel ;
    private MapPanel mapPanel;
    private int buttonNumber;
    private List<Gods> chosenGods;
    private Gods chosenGod;
    private Supporter s;
    private JButton[][] borderButtons;
    private JLabel worker;
    private   Coordinates workersCoord ;

    public Gui(Supporter supporter){
        s = supporter;
        frame = new JFrame("Santorini");
        chooseGodsPanel = new ChooseGodsPanel();
        buttonNumber = 0;
        chosenGods = new ArrayList<>();

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


    public void numberOfPlayers(){
        frame.getContentPane().removeAll();
        frame.setSize(635, 635);

        NrOfPlayersPanel nrOfPlayersPanel = new NrOfPlayersPanel();
        JButton[] nrButton = nrOfPlayersPanel.getNrButton();
        BorderLayout borderLayout = new BorderLayout();

        for(JButton button : nrButton) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 2; i++) {
                        if (e.getSource() == nrButton[i]) {

                            buttonNumber = i+2;

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


         for(JButton button : godButtons) {
             button.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     for (int i = 0; i < 9; i++) {
                         if (e.getSource() == godButtons[i]) {
                             godButtons[i].setBorder(BorderFactory.createLineBorder(new Color(102,0,153)));
                             chosenGods.add(Gods.getGodFromString(godButtons[i].getText()));

                             if(chosenGods.size()==s.getGodsNum()) {
                                 for(JButton button1 : godButtons)
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

    public void chooseGodsPlayers(){
        frame.getContentPane().removeAll();
        frame.setSize(1300, 700);

        JLabel label = chooseGodsPanel.getChooseGods();
        label.setText("Choose a God");
        JButton[] godButtons = chooseGodsPanel.getGodButtons();

        chosenGods = s.getAvailableGods();

        for (JButton button : godButtons) {
            button.setEnabled(false);
        }

        for (JButton button : godButtons){
            for(Gods god : chosenGods){
                if(button.getText().equals(god.toString()))
                    button.setEnabled(true);
            }
        }

        for(JButton button : godButtons) {
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

        frame.getContentPane().removeAll();
        frame.setSize(1400, 800);

        BufferedImage myImage = ImageIO.read(new File("src/main/resources/SantoriniBoard.png"));
        mapPanel = new MapPanel(myImage);

        borderButtons = mapPanel.getSquareButtons();
        mapPanel.setChat("Place your workers");

        mapPanel.setChat("Choose a square to place "+s.getWorkerNum()+"ª worker");
        mapPanel.setWorkerImages(s.getWorkerNum()-1);
        worker=mapPanel.getWorkerImages(s.getWorkerNum()-1);

        for(int i=0 ; i<5 ;i++) {
                for (int j = 0; j < 5; j++) {
                    borderButtons[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for (int i = 0; i < 5; i++) {
                                for (int j = 0; j < 5; j++) {
                                    if (e.getSource() == borderButtons[i][j]) {

                                        borderButtons[i][j].add(worker);
                                        workersCoord = new Coordinates(i,j);
                                        SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                EventForServer eventWorkerPosition = new WorkerPosition(workersCoord);
                                                s.notify(eventWorkerPosition);
                                            }
                                        });
                                    }
                                }

                            }
                        }
                    });


                }

            }
        frame.getContentPane().add(mapPanel);
        frame.setVisible(true);



    }
    public void updateMap() throws OutOfMapException {


    }
/*

    public Worker chooseWorker(){

    }

    public Coordinates askMove() {

    }

    public Pair<Coordinates, Level> askBuild(){


    }

    public boolean makeDecision(){



    }
    public void ack(){}
    public void unsuccessfulOperation(){}
    public void winGame(){}

    public void loseGame(){}
    public void disconnectGame(){}

    public void endGame(){}

    public void outOfRoom() {}


*/

}

