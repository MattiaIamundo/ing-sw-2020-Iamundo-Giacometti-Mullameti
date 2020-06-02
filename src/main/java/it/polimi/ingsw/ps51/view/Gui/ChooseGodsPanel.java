package it.polimi.ingsw.ps51.view.Gui;

import it.polimi.ingsw.ps51.model.gods.Gods;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ChooseGodsPanel extends JPanel {

    JButton[] godButtons;
    Container buttonContainer;
    Gods[] godToString;
    JLabel background;
    JLabel chooseGods ;

    public ChooseGodsPanel() {

        try {
            BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/GodCards/background.png"));
            ImageIcon colorImage = new ImageIcon(new ImageIcon(bufferedImage1).getImage().getScaledInstance(1300, 700,Image.SCALE_DEFAULT));
            background = new JLabel(colorImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        background.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 5, 50, 5);
        buttonContainer = new Container();
        buttonContainer.setLayout(new GridLayout(2,7,10,10));

        chooseGods = new JLabel();
        chooseGods.setHorizontalTextPosition(JLabel.CENTER);
        chooseGods.setVerticalTextPosition(JLabel.CENTER);
        chooseGods.setFont(new Font("Times New Roman", Font.BOLD, 42));
        chooseGods.setForeground(new Color(102,0,153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        background.add(chooseGods , gbc);
        godToString=Gods.values();

        godButtons = new JButton[14];


        for( int i=0 ; i<14 ; i++) {

            godButtons[i] = new JButton(""+godToString[i].toString()+"");
            try {
                BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/GodCards/" + godToString[i].toString().toLowerCase() + ".png"));
                ImageIcon godImage = new ImageIcon(new ImageIcon(bufferedImage1).getImage().getScaledInstance(140, 240,Image.SCALE_DEFAULT));
                godButtons[i].setIcon(godImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            godButtons[i].setForeground(new Color(102,0,153));
            godButtons[i].setHorizontalTextPosition(JButton.CENTER);
            godButtons[i].setVerticalTextPosition(JButton.BOTTOM);
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

    public JButton getSpecificGod(int nr){
        return godButtons[nr];
    }

  /*  public void setGodBorder(int nr ){
        godButtons[nr].setBorderPainted(true);
        godButtons[nr].setBorder(BorderFactory.createLineBorder(Color.RED , 2));
    }*/
      public void setGodUnavailable(int nr ){
        godButtons[nr].setEnabled(false);
      }

    public JLabel getChooseGods() {
        return chooseGods;
    }

 /*   public static void main(String[] args) {
        JFrame frame = new JFrame("Start");
        ChooseGodsPanel chooseGodsPanel = new ChooseGodsPanel();
        frame.add(chooseGodsPanel);
        frame.setSize(1300, 700);
        frame.setResizable(true);
        frame.setVisible(true);

    }*/
}
