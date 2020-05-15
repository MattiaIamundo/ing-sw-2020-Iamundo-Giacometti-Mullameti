package it.polimi.ingsw.ps51.view.Gui;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardPanel extends JPanel {

    private Container buttonContainer;
    private JButton [][] squareButtons;
    private Image board;

    public BoardPanel(Image board){
        this.board = board;
        buttonContainer = new Container();
        buttonContainer.setSize(865*2/3,880*2/3);
        buttonContainer.setLayout(new GridLayout(5,5));

        squareButtons = new JButton[5][5];

        for(int i=0 ; i<5 ; i++){
            for(int j=0 ; j<5 ; j++){
                squareButtons[i][j] = new JButton();
                squareButtons[i][j].setSize(865*2/15,880*2/15);
                squareButtons[i][j].setText("[" + (i+1)+","+(j+1) + "]");
                squareButtons[i][j].setHorizontalAlignment(SwingConstants.RIGHT);
                squareButtons[i][j].setVerticalAlignment(SwingConstants.NORTH);
                squareButtons[i][j].setFont(new Font("Times New Roman", Font.BOLD, 24));
                squareButtons[i][j].setForeground(new Color(0 ,102,0));
                //button[i][j].setOpaque(false);
                squareButtons[i][j].setContentAreaFilled(false);
                //button[i][j].setBorderPainted(false);
                squareButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
                buttonContainer.add(squareButtons[i][j]);
            }
        }
        this.add(buttonContainer);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(board, 0, 0,this.getWidth(),this.getHeight(), this);
    }
 /*   public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Start");
        BufferedImage myImage = ImageIO.read(new File("src/main/resources/board.png"));
        frame.setContentPane(new BoardPanel(myImage));
        frame.setSize(865*2/3,880*2/3);
        frame.setResizable(false);
        frame.setVisible(true);

    }*/

}
