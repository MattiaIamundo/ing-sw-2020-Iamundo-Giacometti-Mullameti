package it.polimi.ingsw.ps51.view.Gui;

import it.polimi.ingsw.ps51.model.Level;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MapPanel extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();

    private BoardContainer boardContainer ;
    private Image background;

    private JLabel chat ;
    private JLabel decision;
    private JButton yes;
    private JButton no;
    private Container decisionContainer;
    private Container[] playerContainer;
    private JLabel[] godPic;
    private JLabel[] playerName;
    private Container workerContainer;
    private ImageIcon[] workerImages;
    private JLabel[] workers;
    private Container levelContainer;
    private JLabel[] levels;
    private ImageIcon[] levelImages;
    private JButton[] button;

    public MapPanel(Image background) {

        this.setLayout(null);
        this.background = background;

        boardContainer = new BoardContainer();
        boardContainer.setLocation(395, 50);
        boardContainer.setSize(865 * 2 / 3, 880 * 2 / 3);
        this.add(boardContainer);

        defineChatLabel();
        defineDecision();
        defineButton();
        definePlayerContainer();
        defineWorkerContainer();
        defineLevelImages();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0,this.getWidth(),this.getHeight(), this);
    }
    private void defineChatLabel(){
        chat = new JLabel();
        chat.setLocation(395,650);
        chat.setSize(865 * 2 / 3, 70);
        chat.setHorizontalAlignment(SwingConstants.CENTER);
        chat.setVerticalAlignment(SwingConstants.CENTER);
        chat.setForeground(new Color(0 ,0 ,153));
        chat.setFont(new Font ("Times new Roman" , Font.ITALIC , 18));
        chat.setBorder(BorderFactory.createMatteBorder(5,5,5,5, new Color(0 ,0 ,153)));
        this.add(chat);

    }

    private void defineButton(){

        button = new JButton[2];

        for (int i=0 ; i<2 ;i++) {
            button[i] = new JButton();
            button[i].setVerticalTextPosition(SwingConstants.CENTER);
            button[i].setHorizontalTextPosition(SwingConstants.CENTER);
            button[i].setSize(1300 * 2 / 13, 700 / 9);
            button[i].setFont(new Font("Times New Roman", Font.BOLD, 24));
            button[i].setForeground(Color.WHITE);
            button[i].setOpaque(false);
            button[i].setContentAreaFilled(false);
            button[i].setBorderPainted(false);
            button[i].setBorder(null);
        }
        ImageIcon undoImage = new ImageIcon((new ImageIcon("src/main/resources/Buttons/btn_coral.png").getImage().getScaledInstance(1300 * 2 / 13, 700 / 9, Image.SCALE_DEFAULT)));
        button[0].setIcon(undoImage);
        button[0].setText("UNDO");
        button[0].setLocation(50,650);
        this.add(button[0]);
        ImageIcon exitImage = new ImageIcon((new ImageIcon("src/main/resources/Buttons/btn_blue.png").getImage().getScaledInstance(1300 * 2 / 13, 700 / 9, Image.SCALE_DEFAULT)));
        button[1].setIcon(exitImage);
        button[1].setText("EXIT");
        button[1].setLocation(1100,650);
        this.add(button[1]);

    }

    private void definePlayerContainer(){

        playerContainer = new Container[3];
        godPic = new JLabel[3];
        playerName = new JLabel[3];

        for (int i=0 ; i<3 ; i++){
            playerContainer[i] = new Container();
            playerContainer[i].setSize(200,300);
            playerContainer[i].setLayout(new GridBagLayout());
            playerName[i] = new JLabel();
            playerName[i].setSize(200,500);
            playerName[i].setHorizontalAlignment(SwingConstants.CENTER);
            playerName[i].setVerticalAlignment(SwingConstants.CENTER);
            playerName[i].setFont(new Font ("Times new Roman" , Font.BOLD , 36));
            //playerName[i].setText("Meri");
            godPic[i] = new JLabel();
            godPic[i].setHorizontalAlignment(SwingConstants.CENTER);
            godPic[i].setVerticalAlignment(SwingConstants.CENTER);
            godPic[i].setPreferredSize(new Dimension(1300*3/26,700*3/9));

            gbc.anchor = 10;
            gbc.gridx = 0;
            gbc.gridy = 0;
            playerContainer[i].add(playerName[i] , gbc);
            gbc.gridy = 1;
            playerContainer[i].add(godPic[i] , gbc);

        }

        playerName[0].setForeground(new Color(204,0 , 0));
        playerName[1].setForeground(Color.GREEN);
        playerName[2].setForeground(Color.BLUE);

        /*ImageIcon godImage1 = new ImageIcon((new ImageIcon("src/main/resources/GodCards/apollo.png").getImage().getScaledInstance(1300*3/26,700*3/9,Image.SCALE_DEFAULT)));
        godPic[0].setIcon(godImage1);
        ImageIcon godImage2 = new ImageIcon((new ImageIcon("src/main/resources/GodCards/pan.png").getImage().getScaledInstance(1300*3/26,700*3/9,Image.SCALE_DEFAULT)));
        godPic[1].setIcon(godImage2);
        ImageIcon godImage3 = new ImageIcon((new ImageIcon("src/main/resources/GodCards/artemis.png").getImage().getScaledInstance(1300*3/26,700*3/9,Image.SCALE_DEFAULT)));
        godPic[2].setIcon(godImage3);*/

        playerContainer[0].setLocation(50,50);
        this.add(playerContainer[0]);
        playerContainer[1].setLocation(1100,50);
        this.add(playerContainer[1]);
        playerContainer[2].setLocation(1100,350);
        this.add(playerContainer[2]);

    }

    private void defineWorkerContainer(){

        workerContainer = new Container();
        workerContainer.setLayout(new GridBagLayout());
        workerContainer.setSize(200,700*2/9);
        workerImages = new ImageIcon[2];


        workers = new JLabel[2];

        for(int i=0 ; i<2 ; i++) {

            workers[i] = new JLabel();
            workers[i].setHorizontalAlignment(SwingConstants.CENTER);
            workers[i].setVerticalAlignment(SwingConstants.CENTER);
            workers[i].setPreferredSize(new Dimension(1300/13,700*2/9));
            workerImages[i] =  new ImageIcon((new ImageIcon("src/main/resources/Workers/red_female_worker.png").getImage().getScaledInstance(1300/13,700*2/9,Image.SCALE_DEFAULT)));
            workers[i].setIcon(workerImages[i]);
            gbc.gridx=i;
            gbc.gridy=0;
            workerContainer.add(workers[i],gbc);
        }

        workerContainer.setLocation(50 , 400);
        this.add(workerContainer);

    }

    private void defineLevelImages(){

        levelContainer = new Container();
        levelContainer.setLayout(new GridBagLayout());
        levelContainer.setSize(120,400);

        levels = new JLabel[4];
        levelImages = new ImageIcon[4];

        levels[0] = new JLabel("First");
        levels[1] = new JLabel("Second");
        levels[2] = new JLabel("Third");
        levels[3] = new JLabel("Dome");


        levelImages[0] = new ImageIcon((new ImageIcon("src/main/resources/Levels/"+ Level.getByValue(1)+".png").getImage().getScaledInstance(235/2,215/2,Image.SCALE_DEFAULT)));
        levelImages[1] = new ImageIcon((new ImageIcon("src/main/resources/Levels/"+ Level.getByValue(2)+".png").getImage().getScaledInstance(200/2,170/2,Image.SCALE_DEFAULT)));
        levelImages[2] = new ImageIcon((new ImageIcon("src/main/resources/Levels/"+ Level.getByValue(3)+".png").getImage().getScaledInstance(200/2,110/2,Image.SCALE_DEFAULT)));
        levelImages[3] = new ImageIcon((new ImageIcon("src/main/resources/Levels/"+ Level.getByValue(4)+".png").getImage().getScaledInstance(120/2,85/2,Image.SCALE_DEFAULT)));

        for(int i=0 ; i<4 ; i++) {


            levels[i].setIcon(levelImages[i]);
            levels[i].setHorizontalAlignment(SwingConstants.CENTER);
            levels[i].setVerticalAlignment(SwingConstants.CENTER);
            levels[i].setHorizontalTextPosition(SwingConstants.CENTER);
            levels[i].setVerticalTextPosition(SwingConstants.BOTTOM);
            levels[i].setSize(levels[i].getIcon().getIconWidth(),levels[i].getIcon().getIconHeight());

        }



        gbc.insets = new Insets(5,0,5,0);

        for(int i=0 ; i<4 ; i++) {

            gbc.gridx = 0;
            gbc.gridy = 4-i;
            levelContainer.add(levels[i] , gbc);
        }
        levelContainer.setLocation(260 , 300);
        this.add(levelContainer);

    }

    private void defineDecision(){
        decisionContainer = new Container();
        decisionContainer.setSize(865*2/3 , 30);
        decisionContainer.setLayout(new GridLayout(1,2));
        yes = new JButton("YES");
        yes.setSize(100,20);
        yes.setForeground(new Color(0,102, 0));
        yes.setFont(new Font ("Times new Roman" , Font.ITALIC , 18));
        yes.setOpaque(false);
        yes.setContentAreaFilled(false);
        yes.setBorder(BorderFactory.createMatteBorder(5,5,5,5, new Color(0,102, 0)));

        no = new JButton("NO");
        no.setSize(100,20);
        no.setForeground(new Color(153,0,0));
        no.setFont(new Font ("Times new Roman" , Font.ITALIC , 18));
        no.setOpaque(false);
        no.setContentAreaFilled(false);
        no.setBorder(BorderFactory.createMatteBorder(5,5,5,5, new Color(153,0,0)));

        decision = new JLabel();
        decision.setText("Enter Y for Yes or N for No !!");
        decision.setLayout(new BorderLayout());
        decision.setLocation(395,650);
        decision.setSize(865 * 2 / 3, 70);
        decision.setHorizontalAlignment(SwingConstants.CENTER);
        decision.setVerticalAlignment(SwingConstants.NORTH);
        decision.setForeground(Color.BLUE);
        decision.setFont(new Font ("Times new Roman" , Font.ITALIC , 18));
        decision.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.BLUE));
        decisionContainer.add(yes);
        decisionContainer.add(no);
        decision.add(decisionContainer , BorderLayout.PAGE_END);
        decision.setVisible(false);
        this.add(decision);
    }

    public void setChat(String command){
        chat.setText(command);
    }

    public void setWorkerBorder(int nr ){
        workers[nr].setBorder(BorderFactory.createLineBorder(Color.RED , 2));
    }
    public ImageIcon getWorkerImages(int nr){
        return workerImages[nr];
    }

    public BoardContainer getBoardContainer() {
        return boardContainer;
    }

    public void setGodPic(String god , int index) {
        ImageIcon godImage = new ImageIcon((new ImageIcon("src/main/resources/GodCards/"+god.toLowerCase()+".png").getImage().getScaledInstance(1300*3/30,700*3/10,Image.SCALE_DEFAULT)));
        godPic[index].setIcon(godImage);
        godPic[index].setVisible(true);
    }
    public void setPlayerName(String name , int index){
        playerName[index].setText(name);
    }


    public void setLevelImages(int nr ){
        levels[nr].setBorder(BorderFactory.createLineBorder(Color.RED , 2));
    }

    public JLabel getLevel(int nr){
        return levels[nr];
}

    public void makeDecision(String string){
        chat.setVisible(false);
        decision.setVisible(true);
        decision.setText(string);
    }

    public JButton getYes() {
        return yes;
    }

    public JButton getNo() {
        return no;
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
