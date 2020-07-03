package it.polimi.ingsw.ps51.view.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * @author Merita Mullameti
 * This class is a JPanel that goes to the main frame when the game ends and we have a winner.
 */
public class WinPanel extends JPanel {

    Image background;
    private JLabel[] trumpets ;
    JLabel text;
    JLabel gameEnded;

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
        gameEnded = new JLabel();
        gameEnded.setText("Game Ended");
        gameEnded.setFont(new Font("Times new roman" , Font.BOLD , 48));
        gameEnded.setForeground(Color.RED);
        gameEnded.setVisible(false);
        gameEnded.setHorizontalAlignment(0);
        gameEnded.setVerticalAlignment(0);
        add(gameEnded, BorderLayout.SOUTH);

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

        try {
            BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/victorytrumpet1.png"));
            ImageIcon trumpets1 = new ImageIcon(new ImageIcon(bufferedImage1).getImage().getScaledInstance(415/2,460/2,Image.SCALE_DEFAULT));
            trumpets[0].setIcon(trumpets1);
            BufferedImage bufferedImage2 = ImageIO.read(getClass().getResourceAsStream("/victorytrumpet2.png"));
            ImageIcon trumpets2 = new ImageIcon(new ImageIcon(bufferedImage2).getImage().getScaledInstance(415/2,460/2,Image.SCALE_DEFAULT));
            trumpets[1].setIcon(trumpets2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public JLabel getGameEnded(){return gameEnded;}

}
