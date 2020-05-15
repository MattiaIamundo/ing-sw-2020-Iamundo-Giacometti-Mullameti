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


    private Container buttonContainer;
    private JButton[] undoButton;


    public GBL(Image background) {

        this.background = background;
        this.setLayout(new GridBagLayout());


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
        add(buttons[0][0], gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        add(buttons[0][5], gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(buttons[0][7], gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(buttons[0][8], gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(buttons[1][0], gbc);


        gbc.gridx = 1;
        gbc.gridy = 5;
        add(buttons[1][5], gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(buttons[1][7], gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        add(buttons[1][8], gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(buttons[2][0], gbc);

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
        add(buttons[2][4], gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        add(buttons[2][5], gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        add(buttons[2][6], gbc);

        gbc.gridx = 2;
        gbc.gridy = 7;
        add(buttons[2][7], gbc);
        gbc.gridx = 2;
        gbc.gridy = 8;
        add(buttons[2][8], gbc);


        gbc.gridx = 10;
        gbc.gridy = 0;
        add(buttons[10][0], gbc);

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
        add(buttons[10][4], gbc);

        gbc.gridx = 10;
        gbc.gridy = 5;
        add(undoButton[4], gbc);

        gbc.gridx = 10;
        gbc.gridy = 6;
        add(buttons[10][6], gbc);

        gbc.gridx = 10;
        gbc.gridy = 7;
        add(buttons[10][7], gbc);
        gbc.gridx = 10;
        gbc.gridy = 8;
        add(buttons[10][8], gbc);

        gbc.gridx = 11;
        gbc.gridy = 8;
        add(buttons[11][8], gbc);


        gbc.gridx = 12;
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
        gbc.gridy = 6;
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
        gbc.gridwidth =7;
        gbc.gridheight = 1;
        gbc.gridx = 3;
        gbc.gridy = 8;
        add(chat, gbc);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0,this.getWidth(),this.getHeight(), this);
    }


    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Start");
        BufferedImage myImage = ImageIO.read(new File("src/main/resources/SantoriniBoard.png"));
        frame.setContentPane(new GBL(myImage));
        frame.setSize(1350, 750);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
