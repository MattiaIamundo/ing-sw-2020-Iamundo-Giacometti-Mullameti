package it.polimi.ingsw.ps51.view.Gui;

import com.google.common.escape.ArrayBasedEscaperMap;
import it.polimi.ingsw.ps51.model.WorkerColor;
import it.polimi.ingsw.ps51.model.gods.Gods;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FirstPlayerPanel extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();

    Container workerContainer;
    Image background;
    private JButton[] players;
    JLabel choosePlayer;

    public FirstPlayerPanel(Image background) {

        this.background = background;
        setLayout(new GridBagLayout());

        choosePlayer = new JLabel("Choose the first player : ");
        choosePlayer.setHorizontalTextPosition(JLabel.CENTER);
        choosePlayer.setVerticalTextPosition(JLabel.CENTER);
        choosePlayer.setFont(new Font("Times New Roman", Font.BOLD, 42));
        choosePlayer.setForeground(new Color(0,102,0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(choosePlayer , gbc);

        definePlayerButtons();


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0,this.getWidth(),this.getHeight(), this);
    }


    private void definePlayerButtons(){
        workerContainer = new Container();
        workerContainer.setLayout(new GridLayout(1,3,50,50));
        workerContainer.setSize(500, 300);

        players = new JButton[3];


        for( int i=0 ; i<3 ; i++) {
            players[i] = new JButton();
            try {
                BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/Workers/red.png"));
                ImageIcon workerImage = new ImageIcon(new ImageIcon(bufferedImage1).getImage().getScaledInstance(140, 240,Image.SCALE_DEFAULT));
                players[i].setIcon(workerImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            players[i].setForeground(Color.RED);
            players[i].setFont(new Font("Times New Roman", Font.BOLD,32));
            players[i].setHorizontalTextPosition(JButton.CENTER);
            players[i].setVerticalTextPosition(JButton.BOTTOM);
            players[i].setSize(140, 240);
            players[i].setOpaque(false);
            players[i].setContentAreaFilled(false);
            players[i].setBorder(null);
            players[i].setVisible(false);
            workerContainer.add(players[i]);
        }


        gbc.insets = new Insets(50,0,50,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(workerContainer , gbc);

    }

    public JButton[] getPlayers() {
        return players;
    }


    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Start");
        BufferedImage myImage = ImageIO.read(new File("src/main/resources/SantoriniBoard.png"));
        frame.setContentPane(new ColorPanel(myImage));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

    }
   
}
