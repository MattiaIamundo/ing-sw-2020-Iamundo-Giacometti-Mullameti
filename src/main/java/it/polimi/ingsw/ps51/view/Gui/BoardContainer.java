package it.polimi.ingsw.ps51.view.Gui;

import javax.swing.*;
import java.awt.*;

public class BoardContainer extends Container {


    private BoardButton [][] boardButtons;



    public BoardContainer(){
        this.setPreferredSize(new Dimension(865*2/3,830*2/3));
        this.setLayout(new GridLayout(5,5));

        boardButtons = new BoardButton[5][5];

        for(int i=0 ; i<5 ; i++){
            for(int j=0 ; j<5 ; j++){
                boardButtons[i][j] = new BoardButton();
                boardButtons[i][j].setSize(865*2/15,830*2/15);
                boardButtons[i][j].setText("[" + (j+1)+","+(i+1) + "]");
                boardButtons[i][j].setHorizontalAlignment(SwingConstants.RIGHT);
                boardButtons[i][j].setVerticalAlignment(SwingConstants.NORTH);
                boardButtons[i][j].setFont(new Font("Times New Roman", Font.BOLD, 24));
                boardButtons[i][j].setForeground(new Color(0 ,102,0));
                //button[i][j].setOpaque(false);
                boardButtons[i][j].setContentAreaFilled(false);
                //button[i][j].setBorderPainted(false);
                boardButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2));

                this.add(boardButtons[i][j]);
            }
        }


    }

    public BoardButton[][] getBoardButtons() {
        return boardButtons;
    }
    public BoardButton getSpecificButtons(int j, int i){return boardButtons[i][j];}


    /*public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Start");
        frame.setContentPane(new BoardContainer());
        frame.setSize(865*2/3,880*2/3);
        frame.setResizable(false);
        frame.setVisible(true);

    }*/

}
