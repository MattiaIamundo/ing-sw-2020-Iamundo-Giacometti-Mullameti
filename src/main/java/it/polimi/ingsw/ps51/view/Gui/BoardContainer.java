package it.polimi.ingsw.ps51.view.Gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoardContainer extends Container {


    private JButton [][] squareButtons;
    private ButtonContainer[][] buttonContainer;


    public BoardContainer(){
        this.setPreferredSize(new Dimension(865*2/3,830*2/3));
        this.setLayout(new GridLayout(5,5));

        buttonContainer= new ButtonContainer[5][5];
        for(int i=0 ; i<5 ; i++){
            for(int j=0 ; j<5 ; j++){
                buttonContainer[i][j] =  new ButtonContainer();
            }
        }

        squareButtons = new JButton[5][5];

        for(int i=0 ; i<5 ; i++){
            for(int j=0 ; j<5 ; j++){
                squareButtons[i][j] = new JButton();
                squareButtons[i][j].setSize(865*2/15,830*2/15);
                squareButtons[i][j].setText("[" + (i+1)+","+(j+1) + "]");
                squareButtons[i][j].setHorizontalAlignment(SwingConstants.RIGHT);
                squareButtons[i][j].setVerticalAlignment(SwingConstants.NORTH);
                squareButtons[i][j].setFont(new Font("Times New Roman", Font.BOLD, 24));
                squareButtons[i][j].setForeground(new Color(0 ,102,0));
                //button[i][j].setOpaque(false);
                squareButtons[i][j].setContentAreaFilled(false);
                //button[i][j].setBorderPainted(false);
                squareButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
                squareButtons[i][j].add(buttonContainer[i][j]);
                this.add(squareButtons[i][j]);
            }
        }


    }

    /*public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Start");
        frame.setContentPane(new BoardContainer());
        frame.setSize(865*2/3,880*2/3);
        frame.setResizable(false);
        frame.setVisible(true);

    }*/

}
