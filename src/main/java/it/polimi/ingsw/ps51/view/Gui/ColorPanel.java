package it.polimi.ingsw.ps51.view.Gui;

import it.polimi.ingsw.ps51.model.Level;
import it.polimi.ingsw.ps51.model.WorkerColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ColorPanel extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();

    private Container colorContainer ;
    private Image background;
    private JButton[] colors;
    private JLabel chooseColor;

    public ColorPanel(Image background) {

        this.background = background;
        setLayout(new GridBagLayout());

        chooseColor = new JLabel("Choose Your Color : ");
        chooseColor.setHorizontalTextPosition(JLabel.CENTER);
        chooseColor.setVerticalTextPosition(JLabel.CENTER);
        chooseColor.setFont(new Font("Times New Roman", Font.BOLD, 42));
        chooseColor.setForeground(new Color(0,102,0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(chooseColor , gbc);

        defineColorButtons();


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0,this.getWidth(),this.getHeight(), this);
    }


    private void defineColorButtons(){
        colorContainer = new Container();
        colorContainer.setLayout(new GridLayout(1,3,50,50));
        colorContainer.setSize(500, 300);

        colors = new JButton[3];
        WorkerColor []colorStrings = WorkerColor.values();


        for( int i=0 ; i<3 ; i++) {

            ImageIcon colorImage = new ImageIcon((new ImageIcon("src/main/resources/Workers/"+ colorStrings[i].toString().toLowerCase() +".png").getImage().getScaledInstance(140, 240,Image.SCALE_DEFAULT)));
            colors[i] = new JButton(""+ colorStrings[i].toString()+"");
            colors[i].setFont(new Font("Times New Roman", Font.BOLD,18 ));
            colors[i].setHorizontalTextPosition(JButton.CENTER);
            colors[i].setVerticalTextPosition(JButton.BOTTOM);
            colors[i].setIcon(colorImage);
            colors[i].setSize(140, 240);
            colors[i].setOpaque(false);
            colors[i].setContentAreaFilled(false);
            //colors[i].setBorderPainted(false);
            colors[i].setBorder(null);

            colorContainer.add(colors[i]);
        }

        colors[0].setForeground(Color.RED);

        colors[1].setForeground(Color.BLUE);

        colors[2].setForeground(Color.WHITE);


        gbc.insets = new Insets(50,0,50,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(colorContainer , gbc);

    }

    public JButton getSpecificButton(int nr){
        return colors[nr];
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
