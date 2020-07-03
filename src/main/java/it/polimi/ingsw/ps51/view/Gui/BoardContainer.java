package it.polimi.ingsw.ps51.view.Gui;

import javax.swing.*;
import java.awt.*;
/**
 * @author Merita Mullameti
 * This class is a Container that containes all the buttons of the board in a 5x5 matrix.
 */
public class BoardContainer extends Container {

    private BoardButton [][] boardButtons;

    public BoardContainer(){
        this.setPreferredSize(new Dimension(550,500));
        this.setLayout(new GridLayout(5,5));

        boardButtons = new BoardButton[5][5];

        for(int i=0 ; i<5 ; i++){
            for(int j=0 ; j<5 ; j++){
                boardButtons[i][j] = new BoardButton();
                boardButtons[i][j].setSize(110,100);
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

}
