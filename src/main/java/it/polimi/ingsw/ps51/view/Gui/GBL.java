package it.polimi.ingsw.ps51.view.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GBL extends JPanel {

    private Image background;
    private JButton[][] squareButtons;
    private JButton[][] buttons;
    GridBagConstraints gbc = new GridBagConstraints();
    private JLabel chat ;
    private JLabel[] godPic;
    private JLabel[] playerName;
    private JLabel santorini;
    private Container buttonContainer;
    private JButton[] undoButton;
    private JLabel[] workerImages;
    private JLabel[] levelImages;

    public GBL(Image background) {

        this.background = background;
        this.setLayout(new GridBagLayout());

        defineUndoButton();
        defineChatLabel();
        defineBoardContainer();
        definePlayerContainer();
        defineWorkerImages();
        defineLevelImages();
        santorini = new JLabel("Santorini");
        santorini.setVerticalAlignment(0);
        santorini.setHorizontalAlignment(0);
        santorini.setForeground(Color.WHITE);
        santorini.setFont(new Font("Times new Roman" ,Font.BOLD , 42));

        buttons = new JButton[13][9];

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new JButton();

                buttons[i][j].setPreferredSize(new Dimension(1300 / 13, 700 / 9));

                buttons[i][j].setText("[" + i + "," + j + "]");
                buttons[i][j].setHorizontalAlignment(SwingConstants.RIGHT);
                buttons[i][j].setVerticalAlignment(SwingConstants.NORTH);
                buttons[i][j].setFont(new Font("Times New Roman", Font.BOLD, 24));
                buttons[i][j].setForeground(new Color(0, 102, 0));
                //button[i][j].setOpaque(false);
                buttons[i][j].setContentAreaFilled(false);
                //button[i][j].setBorderPainted(false);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

            }

        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        //add(buttons[0][0], gbc);

/*
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(buttons[0][5], gbc);


        gbc.gridx = 0;
        gbc.gridy = 6;
        add(buttons[0][6], gbc);*/

        gbc.gridx = 0;
        gbc.gridy = 7;
        //add(buttons[0][7], gbc);
        /*gbc.gridx = 0;
        gbc.gridy = 8;
        add(buttons[0][8], gbc);*/

        gbc.gridx = 1;
        gbc.gridy = 0;
        //add(buttons[1][0], gbc);


        gbc.gridx = 1;
        gbc.gridy = 5;
        //add(buttons[1][5], gbc);
        /*gbc.gridx = 1;
        gbc.gridy = 6;
        add(buttons[1][6], gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(buttons[1][7], gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        add(buttons[1][8], gbc);*/

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(levelImages[4], gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        add(buttons[2][1], gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        add(undoButton[2], gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        add(buttons[2][3], gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        add(levelImages[3], gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        add(levelImages[2], gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        add(levelImages[1], gbc);

        gbc.gridx = 2;
        gbc.gridy = 7;
        add(levelImages[0], gbc);
        /*gbc.gridx = 2;
        gbc.gridy = 8;
        add(buttons[2][8], gbc);*/


        gbc.gridx = 10;
        gbc.gridy = 0;
        add(levelImages[5], gbc);

        gbc.gridx = 10;
        gbc.gridy = 1;
        add(undoButton[3], gbc);

        gbc.gridx = 10;
        gbc.gridy = 2;
        add(buttons[10][2], gbc);

        gbc.gridx = 10;
        gbc.gridy = 3;
        add(buttons[10][3], gbc);

        gbc.gridx = 10;
        gbc.gridy = 4;
      //  add(buttons[10][4], gbc);

        gbc.gridx = 10;
        gbc.gridy = 5;
        add(undoButton[4], gbc);

        gbc.gridx = 10;
        gbc.gridy = 6;
        //add(buttons[10][6], gbc);

        gbc.gridx = 10;
        gbc.gridy = 7;
        //add(buttons[10][7], gbc);
        /*gbc.gridx = 10;
        gbc.gridy = 8;
        add(buttons[10][8], gbc);*/

        gbc.gridx = 11;
        gbc.gridy = 8;
        //add(buttons[11][8], gbc);

        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 11;
        gbc.gridy = 8;
        add(undoButton[1], gbc);
        gbc.gridwidth = 7;
        gbc.gridheight = 8;

        gbc.gridx = 3;
        gbc.gridy = 0;
        add(buttonContainer, gbc);

        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;


        gbc.gridx = 0;
        gbc.gridy = 1;
        add(playerName[0], gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(undoButton[0], gbc);

        gbc.gridx = 11;
        gbc.gridy = 0;
        add(playerName[1], gbc);

        gbc.gridx = 11;
        gbc.gridy = 4;
        add(playerName[2], gbc);


        //Adds Player's Container

        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(godPic[0], gbc);

        gbc.gridx = 11;
        gbc.gridy = 1;
        add(godPic[1], gbc);

        gbc.gridx = 11;
        gbc.gridy = 5;
        add(godPic[2], gbc);


        //Adds Chat
        gbc.gridwidth =9;
        gbc.gridheight = 1;
        gbc.gridx = 2;
        gbc.gridy = 8;
        add(chat, gbc);

        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(santorini, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(workerImages[0], gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(workerImages[1], gbc);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0,this.getWidth(),this.getHeight(), this);
    }

    private void defineBoardContainer(){
        buttonContainer = new Container();
        buttonContainer.setPreferredSize(new Dimension(865*2/3,830*2/3));
        buttonContainer.setLayout(new GridLayout(5,5));

        squareButtons = new JButton[5][5];

        for(int i=0 ; i<5 ; i++){
            for(int j=0 ; j<5 ; j++){
                squareButtons[i][j] = new JButton();
                squareButtons[i][j].setSize(865*2/15,830*2/15);
                squareButtons[i][j].setText("[" + (i+1)+","+(j+1) + "]");
                squareButtons[i][j].setHorizontalAlignment(SwingConstants.RIGHT);
                squareButtons[i][j].setVerticalAlignment(SwingConstants.NORTH);
                squareButtons[i][j].setFont(new Font("Times New Roman", Font.BOLD, 18));
                squareButtons[i][j].setForeground(new Color(0 ,102,0));
                //button[i][j].setOpaque(false);
                squareButtons[i][j].setContentAreaFilled(false);
                //button[i][j].setBorderPainted(false);
                squareButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.RED,2));
                buttonContainer.add(squareButtons[i][j]);
            }
        }
        this.add(buttonContainer);
    }

    private void defineChatLabel(){
        chat = new JLabel();
        chat.setText("It's your Turn !!");
        chat.setHorizontalAlignment(SwingConstants.CENTER);
        chat.setVerticalAlignment(SwingConstants.CENTER);
        chat.setForeground(new Color(0 ,0 ,153));
        chat.setFont(new Font ("Times new Roman" , Font.ITALIC , 18));
        chat.setBackground(new Color(51,204,255));
        chat.setBorder(BorderFactory.createMatteBorder(5,5,5,5, new Color(0 ,0 ,153)));


    }
    private void definePlayerContainer(){
        godPic = new JLabel[3];
        playerName = new JLabel[3];

        for (int i=0 ; i<3 ; i++){
            playerName[i] = new JLabel();
            playerName[i].setHorizontalAlignment(SwingConstants.CENTER);
            playerName[i].setVerticalAlignment(SwingConstants.CENTER);
            playerName[i].setFont(new Font ("Times new Roman" , Font.BOLD , 36));
        }

        playerName[0].setText("Meri");
        playerName[0].setForeground(new Color(204,0 , 0));

        playerName[1].setText("Luca");
        playerName[1].setForeground(Color.GREEN);


        playerName[2].setText("Mattia");
        playerName[2].setForeground(Color.BLUE);


        for(int i=0 ; i<3 ; i++) {

            godPic[i] = new JLabel();

            godPic[i].setHorizontalAlignment(SwingConstants.CENTER);
            godPic[i].setVerticalAlignment(SwingConstants.CENTER);
            godPic[i].setPreferredSize(new Dimension(1300*3/26,700*3/9));

        }
        ImageIcon godImage1 = new ImageIcon((new ImageIcon("src/main/resources/GodCards/apollo.png").getImage().getScaledInstance(1300*3/26,700*3/9,Image.SCALE_DEFAULT)));
        godPic[0].setIcon(godImage1);
        ImageIcon godImage2 = new ImageIcon((new ImageIcon("src/main/resources/GodCards/pan.png").getImage().getScaledInstance(1300*3/26,700*3/9,Image.SCALE_DEFAULT)));
        godPic[1].setIcon(godImage2);
        ImageIcon godImage3 = new ImageIcon((new ImageIcon("src/main/resources/GodCards/artemis.png").getImage().getScaledInstance(1300*3/26,700*3/9,Image.SCALE_DEFAULT)));
        godPic[2].setIcon(godImage3);

    }
    private void defineUndoButton(){
        undoButton = new JButton[5];
        for (int i=0 ; i<5 ;i++) {
            undoButton[i] = new JButton();
            undoButton[i].setVerticalTextPosition(SwingConstants.CENTER);
            undoButton[i].setHorizontalTextPosition(SwingConstants.CENTER);
            undoButton[i].setSize(1300 * 2 / 13, 700 / 9);
            undoButton[i].setFont(new Font("Times New Roman", Font.BOLD, 24));
            undoButton[i].setForeground(Color.WHITE);
            undoButton[i].setOpaque(false);
            undoButton[i].setContentAreaFilled(false);
            undoButton[i].setBorderPainted(false);
            undoButton[i].setBorder(null);
        }
        ImageIcon undoImage = new ImageIcon((new ImageIcon("src/main/resources/Buttons/btn_coral.png").getImage().getScaledInstance(1300 * 2 / 13, 700 / 9, Image.SCALE_DEFAULT)));
        undoButton[0].setIcon(undoImage);
        undoButton[0].setText("UNDO");
        ImageIcon exitImage = new ImageIcon((new ImageIcon("src/main/resources/Buttons/btn_blue.png").getImage().getScaledInstance(1300 * 2 / 13, 700 / 9, Image.SCALE_DEFAULT)));
        undoButton[1].setIcon(exitImage);
        undoButton[1].setText("EXIT");
        ImageIcon buttonImage = new ImageIcon((new ImageIcon("src/main/resources/Buttons/btn_god.png").getImage().getScaledInstance(1300  / 26, 700 / 18, Image.SCALE_DEFAULT)));
        undoButton[2].setIcon(buttonImage);
        undoButton[3].setIcon(buttonImage);
        undoButton[4].setIcon(buttonImage);

    }
    private void defineWorkerImages(){

        workerImages = new JLabel[2];

        for(int i=0 ; i<2 ; i++) {

            workerImages[i] = new JLabel();

            workerImages[i].setHorizontalAlignment(SwingConstants.CENTER);
            workerImages[i].setVerticalAlignment(SwingConstants.CENTER);
            workerImages[i].setPreferredSize(new Dimension(1300/13,700*2/9));

        }
        ImageIcon workerImages1 = new ImageIcon((new ImageIcon("src/main/resources/Workers/red_female_worker.png").getImage().getScaledInstance(1300/13,700*2/9,Image.SCALE_DEFAULT)));
        workerImages[0].setIcon(workerImages1);
        ImageIcon workerImages2 = new ImageIcon((new ImageIcon("src/main/resources/Workers/red_male_worker.png").getImage().getScaledInstance(1300/13,700*2/9,Image.SCALE_DEFAULT)));
        workerImages[1].setIcon(workerImages2);

    }

    private void defineLevelImages(){

        levelImages = new JLabel[6];

        for(int i=0 ; i<6 ; i++) {

            levelImages[i] = new JLabel();

            levelImages[i].setHorizontalAlignment(SwingConstants.CENTER);
            levelImages[i].setVerticalAlignment(SwingConstants.CENTER);
            levelImages[i].setPreferredSize(new Dimension(1300/13,700/9));

        }
        ImageIcon levelImages1 = new ImageIcon((new ImageIcon("src/main/resources/Levels/first.png").getImage().getScaledInstance(1300*2/39,700*2/27,Image.SCALE_DEFAULT)));
        levelImages[0].setIcon(levelImages1);
        ImageIcon levelImages2 = new ImageIcon((new ImageIcon("src/main/resources/Levels/second.png").getImage().getScaledInstance(1300*2/39,700*2/27,Image.SCALE_DEFAULT)));
        levelImages[1].setIcon(levelImages2);
        ImageIcon levelImages3 = new ImageIcon((new ImageIcon("src/main/resources/Levels/third.png").getImage().getScaledInstance(1300*2/39,700*2/27,Image.SCALE_DEFAULT)));
        levelImages[2].setIcon(levelImages3);
        ImageIcon levelImages4 = new ImageIcon((new ImageIcon("src/main/resources/Levels/dome.png").getImage().getScaledInstance(1300/39,700/27,Image.SCALE_DEFAULT)));
        levelImages[3].setIcon(levelImages4);

        ImageIcon cloud1 = new ImageIcon((new ImageIcon("src/main/resources/Levels/cloudright.png").getImage().getScaledInstance(1300*2/39,700*2/27,Image.SCALE_DEFAULT)));
        levelImages[4].setIcon(cloud1);
        ImageIcon cloud2 = new ImageIcon((new ImageIcon("src/main/resources/Levels/cloudleft.png").getImage().getScaledInstance(1300*2/39,700*2/27,Image.SCALE_DEFAULT)));
        levelImages[5].setIcon(cloud2);
    }

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Start");
        BufferedImage myImage = ImageIO.read(new File("src/main/resources/SantoriniBoard.png"));
        frame.setContentPane(new GBL(myImage));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }
}
