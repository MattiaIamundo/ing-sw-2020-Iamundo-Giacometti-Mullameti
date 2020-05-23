package it.polimi.ingsw.ps51.view.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinPanel extends JPanel {

    private Image background;
    private JLabel[] trumpets ;
    private JLabel text;

    public WinPanel(Image background){
        this.background = background;
        this.setLayout(new BorderLayout());
        defineTrumpets();
        text = new JLabel();
        text.setText("You Won !");
        text.setFont(new Font("Times new roman" , Font.BOLD , 48));
        text.setForeground(Color.RED);
        text.setHorizontalAlignment(0);
        text.setVerticalAlignment(0);
        add(text, BorderLayout.CENTER);
        add(trumpets[0], BorderLayout.WEST);
        add(trumpets[1], BorderLayout.EAST);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0,this.getWidth(),this.getHeight(), this);
    }

    private void defineTrumpets(){

        trumpets = new JLabel[2];

        for(int i=0 ; i<2 ; i++) {

            trumpets[i] = new JLabel();

            trumpets[i].setHorizontalAlignment(SwingConstants.CENTER);
            trumpets[i].setVerticalAlignment(SwingConstants.CENTER);
            trumpets[i].setPreferredSize(new Dimension(415/2,460/2));

        }
        ImageIcon trumpets1 = new ImageIcon((new ImageIcon("src/main/resources/victorytrumpet1.png").getImage().getScaledInstance(415/2,460/2,Image.SCALE_DEFAULT)));
        trumpets[0].setIcon(trumpets1);
        ImageIcon trumpets2 = new ImageIcon((new ImageIcon("src/main/resources/victorytrumpet2.png").getImage().getScaledInstance(415/2,460/2,Image.SCALE_DEFAULT)));
        trumpets[1].setIcon(trumpets2);

    }

   /* public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Start");
        BufferedImage myImage = ImageIO.read(new File("src/main/resources/winBackground.jpg"));
        frame.setContentPane(new WinPanel(myImage));
        frame.setSize(625*3/2, 415*3/2);
        frame.setResizable(true);
        frame.setVisible(true);

    }*/
}
