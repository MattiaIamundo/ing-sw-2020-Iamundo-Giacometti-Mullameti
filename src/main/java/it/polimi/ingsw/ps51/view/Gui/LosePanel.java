package it.polimi.ingsw.ps51.view.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LosePanel extends JPanel {

    private JLabel[] trumpets ;
    JLabel text;
    JLabel gameEnded;

    public LosePanel(){

        this.setLayout(new BorderLayout());
        defineTrumpets();
        text = new JLabel();
        text.setFont(new Font("Times new roman" , Font.BOLD , 48));
        text.setForeground(Color.BLACK);
        text.setHorizontalAlignment(0);
        text.setVerticalAlignment(0);
        add(text, BorderLayout.CENTER);
        gameEnded = new JLabel("Game Ended");
        gameEnded.setFont(new Font("Times new roman" , Font.BOLD , 48));
        gameEnded.setForeground(Color.BLACK);
        gameEnded.setHorizontalAlignment(0);
        gameEnded.setVerticalAlignment(0);
        gameEnded.setVisible(false);
        add(gameEnded, BorderLayout.SOUTH);
        add(trumpets[0], BorderLayout.WEST);
        add(trumpets[1], BorderLayout.EAST);

    }

    public JLabel getText() {
        return text;
    }
    public JLabel getGameEnded(){return gameEnded;}
    private void defineTrumpets(){

        trumpets = new JLabel[2];

        for(int i=0 ; i<2 ; i++) {

            trumpets[i] = new JLabel();

            trumpets[i].setHorizontalAlignment(SwingConstants.CENTER);
            trumpets[i].setVerticalAlignment(SwingConstants.CENTER);
            trumpets[i].setPreferredSize(new Dimension(415/2,460/2));

        }
        try {
            BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/defeattrumpet1.png"));
            ImageIcon trumpets1 = new ImageIcon(new ImageIcon(bufferedImage1).getImage().getScaledInstance(415/2,460/2,Image.SCALE_DEFAULT));
            trumpets[0].setIcon(trumpets1);
            BufferedImage bufferedImage2 = ImageIO.read(getClass().getResourceAsStream("/defeattrumpet2.png"));
            ImageIcon trumpets2 = new ImageIcon(new ImageIcon(bufferedImage2).getImage().getScaledInstance(415/2,460/2,Image.SCALE_DEFAULT));
            trumpets[1].setIcon(trumpets2);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*public static void main(String[] args) {

        JFrame frame = new JFrame("Start");

        frame.setContentPane(new LosePanel());
        frame.setSize(625*3/2, 415*3/2);
        frame.setResizable(true);
        frame.setVisible(true);

    }*/


}
