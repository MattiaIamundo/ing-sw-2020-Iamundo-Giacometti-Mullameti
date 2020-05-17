package it.polimi.ingsw.ps51.view.Gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ButtonContainer extends Container {


    GridBagConstraints gbc  = new GridBagConstraints();

    private JLabel[] level;
    private JLabel worker;

    public ButtonContainer(){
        this.setPreferredSize(new Dimension(865*2/15,830*2/15));
        this.setLayout(new GridBagLayout());
        defineLevels();
    }
    private void defineLevels(){

        level = new JLabel[4];

        for(int k=0 ; k<4 ; k++) {

            level[k] = new JLabel();

            level[k].setHorizontalAlignment(SwingConstants.CENTER);
            level[k].setVerticalAlignment(SwingConstants.CENTER);
            level[k].setPreferredSize(new Dimension(865/30,830/30));
            gbc.gridx = 0;
            gbc.gridy = k;
            this.add(level[k] ,gbc);
        }

        ImageIcon dome = new ImageIcon((new ImageIcon("src/main/resources/Levels/dome.png").getImage().getScaledInstance(865/50,830/50,Image.SCALE_DEFAULT)));
        level[0].setIcon(dome);
        ImageIcon third = new ImageIcon((new ImageIcon("src/main/resources/Levels/third.png").getImage().getScaledInstance(865/30,830/30,Image.SCALE_DEFAULT)));
        level[1].setIcon(third);
        ImageIcon second = new ImageIcon((new ImageIcon("src/main/resources/Levels/second.png").getImage().getScaledInstance(865/30,830/30,Image.SCALE_DEFAULT)));
        level[2].setIcon(second);
        ImageIcon first = new ImageIcon((new ImageIcon("src/main/resources/Levels/first.png").getImage().getScaledInstance(865/30,830/30,Image.SCALE_DEFAULT)));
        level[3].setIcon(first);

        worker = new JLabel();
        ImageIcon workerPic = new ImageIcon((new ImageIcon("src/main/resources/Workers/blue_female_worker.png").getImage().getScaledInstance(865*3/60,830*3/30,Image.SCALE_DEFAULT)));
        worker.setIcon(workerPic);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.fill = 1;
        this.add(worker,gbc);

    }

   /* public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Start");
        frame.setContentPane(new ButtonContainer());
        frame.setSize(865*2/15,830*2/15);
        frame.setResizable(false);
        frame.setVisible(true);

    }*/
}
