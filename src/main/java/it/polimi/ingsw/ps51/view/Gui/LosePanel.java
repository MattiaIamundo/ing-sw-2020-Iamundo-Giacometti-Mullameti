package it.polimi.ingsw.ps51.view.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LosePanel extends JPanel {

    private JLabel[] trumpets ;
    private JLabel text;

    public LosePanel(){

        this.setLayout(new BorderLayout());
        defineTrumpets();
        text = new JLabel();
        text.setText("You Lost !");
        text.setFont(new Font("Times new roman" , Font.BOLD , 48));
        text.setForeground(Color.BLACK);
        text.setHorizontalAlignment(0);
        text.setVerticalAlignment(0);
        add(text, BorderLayout.CENTER);
        add(trumpets[0], BorderLayout.WEST);
        add(trumpets[1], BorderLayout.EAST);


    }

    private void defineTrumpets(){

        trumpets = new JLabel[2];

        for(int i=0 ; i<2 ; i++) {

            trumpets[i] = new JLabel();

            trumpets[i].setHorizontalAlignment(SwingConstants.CENTER);
            trumpets[i].setVerticalAlignment(SwingConstants.CENTER);
            trumpets[i].setPreferredSize(new Dimension(415/2,460/2));

        }
        ImageIcon trumpets1 = new ImageIcon((new ImageIcon("src/main/resources/defeattrumpet1.png").getImage().getScaledInstance(415/2,460/2,Image.SCALE_DEFAULT)));
        trumpets[0].setIcon(trumpets1);
        ImageIcon trumpets2 = new ImageIcon((new ImageIcon("src/main/resources/defeattrumpet2.png").getImage().getScaledInstance(415/2,460/2,Image.SCALE_DEFAULT)));
        trumpets[1].setIcon(trumpets2);

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Start");

        frame.setContentPane(new LosePanel());
        frame.setSize(625*3/2, 415*3/2);
        frame.setResizable(true);
        frame.setVisible(true);

    }
}
