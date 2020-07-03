package it.polimi.ingsw.ps51.view.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Merita Mullameti
 * This class is a JPanel for the first player to choose how many players he wants to play with.
 */

public class NrOfPlayersPanel extends JPanel {

    JButton[] nrButton;

    Container container;
    JLabel welcome;
    JLabel enterNrOfPlayers;

    public NrOfPlayersPanel() {

        this.setLayout(new BorderLayout());
        container = new Container();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 5);

        try {
            BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/welcome.png"));
            ImageIcon welcomeImage = new ImageIcon(new ImageIcon(bufferedImage1).getImage().getScaledInstance(520*2/3,585*2/3,Image.SCALE_DEFAULT));
            welcome = new JLabel(welcomeImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(welcome , BorderLayout.NORTH);

        enterNrOfPlayers = new JLabel();
        enterNrOfPlayers.setText("Number of Players :");
        enterNrOfPlayers.setFont(new Font("Times New Roman", Font.BOLD, 36));
        enterNrOfPlayers.setForeground(new Color(102,0,153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(enterNrOfPlayers , gbc);

        nrButton = new JButton[2];

        for(int i=0 ; i<2 ;i++) {
            nrButton[i] =new JButton(""+(i+2)+"");
            try {
                BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/Buttons/btn_purple.png"));
                ImageIcon purpleButton = new ImageIcon(new ImageIcon(bufferedImage1).getImage().getScaledInstance(415 / 4, 105 * 2 / 3, Image.SCALE_DEFAULT));
                nrButton[i].setIcon(purpleButton);
            } catch (IOException e) {
                e.printStackTrace();
            }
            nrButton[i].setSize(415 / 4, 105 * 2 / 3);
            nrButton[i].setHorizontalTextPosition(JButton.CENTER);
            nrButton[i].setVerticalTextPosition(JButton.CENTER);
            nrButton[i].setFont(new Font("Times New Roman", Font.BOLD, 24));
            nrButton[i].setForeground(Color.WHITE);
            nrButton[i].setOpaque(false);
            nrButton[i].setContentAreaFilled(false);
            nrButton[i].setBorderPainted(false);
            nrButton[i].setBorder(null);
            gbc.gridx = i+1;
            container.add(nrButton[i] ,gbc);
        }
        add(container , BorderLayout.CENTER);


    }

    public JButton[] getNrButton() {
        return nrButton;
    }


}



