package it.polimi.ingsw.ps51.view.Gui;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {

    private JButton[] welcomePanelButtons ;
    private Container buttonContainer;
    private JLabel welcome;


    public WelcomePanel(){
        this.setLayout(new BorderLayout());
        buttonContainer = new Container();
        buttonContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        buttonContainer.setSize(415,105*2);

        ImageIcon welcomeImage = new ImageIcon((new ImageIcon("src/main/resources/welcome.png").getImage().getScaledInstance(520*2/3,585*2/3,Image.SCALE_DEFAULT)));

        welcome = new JLabel(welcomeImage);
        add(welcome , BorderLayout.NORTH);
        welcomePanelButtons = new JButton[3];

        ImageIcon purpleButton = new ImageIcon((new ImageIcon("src/main/resources/Buttons/btn_purple.png").getImage().getScaledInstance(415*2/3,105*2/3,Image.SCALE_DEFAULT)));

        welcomePanelButtons[0] = new JButton("New Game");
        welcomePanelButtons[1] = new JButton("Join Existing Game");
        welcomePanelButtons[2] = new JButton("Exit");

        for( int i=0 ; i<3 ; i++){
            welcomePanelButtons[i].setIcon(purpleButton);
            welcomePanelButtons[i].setSize(415*2/3,105*2/3);
            welcomePanelButtons[i].setHorizontalTextPosition(JButton.CENTER);
            welcomePanelButtons[i].setVerticalTextPosition(JButton.CENTER);
            welcomePanelButtons[i].setFont(new Font("Times New Roman", Font.BOLD, 24));
            welcomePanelButtons[i].setForeground(Color.WHITE);
            welcomePanelButtons[i].setOpaque(false);
            welcomePanelButtons[i].setContentAreaFilled(false);
            welcomePanelButtons[i].setBorderPainted(false);
            welcomePanelButtons[i].setBorder(null);
            gbc.gridx = 0;
            gbc.gridy = i;
            buttonContainer.add(welcomePanelButtons[i] , gbc);
        }
        add(buttonContainer , BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Start");
        WelcomePanel welcomePanel = new WelcomePanel();
        frame.add(welcomePanel);
        frame.setSize(635, 635);
        frame.setResizable(true);
        frame.setVisible(true);

    }

}