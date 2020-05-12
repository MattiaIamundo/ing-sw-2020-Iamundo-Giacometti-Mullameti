package it.polimi.ingsw.ps51.view.Gui;

import it.polimi.ingsw.ps51.model.gods.Gods;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



public class ChooseGodsPanel extends JPanel {

    private JButton[] godButtons;
    private Container buttonContainer;
    private Gods[] godToString;
    private JLabel background;
    private JLabel chooseGods ;

    public ChooseGodsPanel() {

        background = new JLabel(new ImageIcon((new ImageIcon("src/main/resources/GodCards/background.png").getImage().getScaledInstance(1300, 700,Image.SCALE_DEFAULT))));
        background.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 5, 50, 5);
        buttonContainer = new Container();
        buttonContainer.setLayout(new GridLayout(1,9));

        chooseGods = new JLabel();
        chooseGods.setHorizontalTextPosition(JLabel.CENTER);
        chooseGods.setVerticalTextPosition(JLabel.CENTER);
        chooseGods.setFont(new Font("Times New Roman", Font.BOLD, 42));
        chooseGods.setForeground(new Color(102,0,153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        background.add(chooseGods , gbc);
        godToString=Gods.values();

        godButtons = new JButton[9];


        for( int i=0 ; i<9 ; i++) {

            ImageIcon godImage = new ImageIcon((new ImageIcon("src/main/resources/GodCards/"+ godToString[i].toString().toLowerCase() +".png").getImage().getScaledInstance(140, 240,Image.SCALE_DEFAULT)));
            godButtons[i] = new JButton(""+godToString[i].toString()+"");
            godButtons[i].setForeground(new Color(102,0,153));
            godButtons[i].setHorizontalTextPosition(JButton.CENTER);
            godButtons[i].setVerticalTextPosition(JButton.BOTTOM);
            godButtons[i].setIcon(godImage);
            godButtons[i].setSize(140, 240);
            godButtons[i].setOpaque(false);
            godButtons[i].setContentAreaFilled(false);
            godButtons[i].setBorderPainted(false);
            godButtons[i].setBorder(null);

            buttonContainer.add(godButtons[i]);
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        background.add(buttonContainer , gbc);
        add(background);
    }

    public JButton[] getGodButtons() {
        return godButtons;
    }

    public JLabel getChooseGods() {
        return chooseGods;
    }

/* public static void main(String[] args) {
        JFrame frame = new JFrame("Start");
        ChooseGodsPanel chooseGodsPanel = new ChooseGodsPanel();
        frame.add(chooseGodsPanel);
        frame.setSize(1300, 700);
        frame.setResizable(true);
        frame.setVisible(true);

    }*/
}
