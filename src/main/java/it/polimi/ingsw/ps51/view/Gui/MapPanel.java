package it.polimi.ingsw.ps51.view.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapPanel extends JPanel {

    private Image background;
    private Container buttonContainer;
    private Container playerContainer;
    private JButton [][] squareButtons;
    private JLabel santorini;
    private JTextPane chat ;
    private JLabel name;
    private JLabel god;
    GridBagConstraints gbc = new GridBagConstraints();

    public MapPanel(Image background) {
        this.background = background;
        this.setLayout(new GridBagLayout());
       /* definePlayerContainer("meri");
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weightx = 5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(playerContainer , gbc);
*/
        defineMapContainer();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 15;
        gbc.weighty = 15;
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(buttonContainer , gbc);

        santorini = new JLabel();
        santorini.setText("Santorini");
        santorini.setFont(new Font("Times new roman" , Font.BOLD , 42));
        santorini.setForeground(Color.BLUE);
        santorini.setPreferredSize(new Dimension(887 * 2 / 3,70));
        santorini.setVerticalAlignment(SwingConstants.CENTER);
        santorini.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(santorini , gbc);

        chat = new JTextPane();
        chat.setPreferredSize(new Dimension(887 * 2 / 3,70));
        chat.setBackground(Color.GRAY);
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(chat, gbc);
        /*definePlayerContainer("Luca");
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        //gbc.weightx = 10;
        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(playerContainer , gbc);

        definePlayerContainer("Mattia");
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        //gbc.weightx = 10;
        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(playerContainer , gbc);*/
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0,this.getWidth(),this.getHeight(), this);
    }

    private void defineMapContainer(){
        buttonContainer = new Container();
        buttonContainer.setPreferredSize(new Dimension(887 * 2 / 3, 875 * 2 / 3));
        buttonContainer.setLayout(new GridLayout(5, 5));

        squareButtons = new JButton[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                squareButtons[i][j] = new JButton();
                squareButtons[i][j].setSize(buttonContainer.getWidth()/5, buttonContainer.getHeight()/5);
                squareButtons[i][j].setText("[" + (i + 1) + "," + (j + 1) + "]");
                squareButtons[i][j].setHorizontalAlignment(SwingConstants.RIGHT);
                squareButtons[i][j].setVerticalAlignment(SwingConstants.NORTH);
                squareButtons[i][j].setFont(new Font("Times New Roman", Font.BOLD, 24));
                squareButtons[i][j].setForeground(new Color(0, 102, 0));
                //button[i][j].setOpaque(false);
                squareButtons[i][j].setContentAreaFilled(false);
                //button[i][j].setBorderPainted(false);
                squareButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                buttonContainer.add(squareButtons[i][j]);
            }
        }
    }

    private void definePlayerContainer(String string){
        playerContainer = new Container();
        playerContainer.setLayout(new GridBagLayout());
        name = new JLabel();
        name.setText(string);
        name.setFont(new Font("Times New Roman", Font.BOLD, 24));
        name.setForeground(new Color(0, 102, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        playerContainer.add(name , gbc);
        god = new JLabel();
        ImageIcon godImage = new ImageIcon((new ImageIcon("src/main/resources/GodCards/apollo.png").getImage().getScaledInstance(100,200,Image.SCALE_DEFAULT)));
        god.setIcon(godImage);
        god.setSize(godImage.getIconWidth(),godImage.getIconHeight());
        gbc.gridx = 0;
        gbc.gridy = 1;
        playerContainer.add(god , gbc);
    }
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Start");
        BufferedImage myImage = ImageIO.read(new File("src/main/resources/SantoriniBoard.png"));
        frame.setContentPane(new MapPanel(myImage));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

    }
}
