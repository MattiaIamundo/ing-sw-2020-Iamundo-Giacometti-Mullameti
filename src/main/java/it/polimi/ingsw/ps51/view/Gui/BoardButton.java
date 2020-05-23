package it.polimi.ingsw.ps51.view.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardButton extends JButton {


    GridBagConstraints gbc  = new GridBagConstraints();
    private Container buttonContainer;
    private JLabel[] level;
    private JLabel worker;

    public BoardButton(){
        this.setPreferredSize(new Dimension(865*2/15,830*2/15));
        buttonContainer = new Container();
        buttonContainer.setLayout(new GridBagLayout());
        buttonContainer.setPreferredSize(new Dimension(865*2/15,830*2/15));
        defineLevels();
        this.add(buttonContainer);
    }
    private void defineLevels(){

        level = new JLabel[4];

        for(int k=0 ; k<4 ; k++) {

            level[k] = new JLabel();

            level[k].setHorizontalAlignment(SwingConstants.CENTER);
            level[k].setVerticalAlignment(SwingConstants.CENTER);
            level[k].setPreferredSize(new Dimension(865/30,830/30));
            level[k].setVisible(false);
            gbc.gridx = 0;
            gbc.gridy = 4-k;
            buttonContainer.add(level[k] ,gbc);
        }

        ImageIcon dome = new ImageIcon((new ImageIcon("src/main/resources/Levels/dome.png").getImage().getScaledInstance(865/50,830/50,Image.SCALE_DEFAULT)));
        level[3].setIcon(dome);
        ImageIcon third = new ImageIcon((new ImageIcon("src/main/resources/Levels/third.png").getImage().getScaledInstance(865/30,830/30,Image.SCALE_DEFAULT)));
        level[2].setIcon(third);
        ImageIcon second = new ImageIcon((new ImageIcon("src/main/resources/Levels/second.png").getImage().getScaledInstance(865/30,830/30,Image.SCALE_DEFAULT)));
        level[1].setIcon(second);
        ImageIcon first = new ImageIcon((new ImageIcon("src/main/resources/Levels/first.png").getImage().getScaledInstance(865/30,830/30,Image.SCALE_DEFAULT)));
        level[0].setIcon(first);

        worker = new JLabel();
        worker.setPreferredSize(new Dimension(865*3/60,830*3/30));

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.fill = 1;
        buttonContainer.add(worker,gbc);

    }

    public void setLevel(int nr) {
        level[nr].setVisible(true);
    }

    public void setWorker(ImageIcon icon) {
        worker.setIcon(new ImageIcon(icon.getImage().getScaledInstance(865*3/60,830*3/30,Image.SCALE_DEFAULT)));

    }

    public JLabel[] getLevel() {
        return level;
    }

    public JLabel getWorker() {
        return worker;
    }

    public Container getButtonContainer() {
        return buttonContainer;
    }

    public void setButtonContainer(Container buttonContainer) {
        this.buttonContainer = buttonContainer;
    }

    /* public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Start");
        frame.setContentPane(new ButtonContainer());
        frame.setSize(865*2/15,830*2/15);
        frame.setResizable(false);
        frame.setVisible(true);

    }*/
}
