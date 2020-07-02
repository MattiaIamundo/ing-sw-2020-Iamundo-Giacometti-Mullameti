package it.polimi.ingsw.ps51.view.Gui;


import it.polimi.ingsw.ps51.events.events_for_server.*;
import it.polimi.ingsw.ps51.events.events_for_server.Build;
import it.polimi.ingsw.ps51.exceptions.OutOfMapException;
import it.polimi.ingsw.ps51.model.*;
import it.polimi.ingsw.ps51.model.gods.Gods;
import it.polimi.ingsw.ps51.view.Supporter;
import org.javatuples.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MapPanel extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();

    BoardContainer boardContainer;
    Image background;

    private JLabel chat ;
    private JLabel decision;
    private JButton yes;
    private JButton no;
    Container decisionContainer;
    Container[] playerContainer;
    private JLabel[] godPic;
    private JLabel[] playerName;
    private Container workerContainer;
    private ImageIcon[] workerImages;
    private JLabel[] workers;
    private Container levelContainer;
    private JLabel[] levels;
    ImageIcon[] levelImages;
    private JTextPane textDecisio;
    private JTextPane textChat;
    private JButton yesUndo;
    private JButton noUndo;
    private Container undoContainer;
    private Container westContainer;
    private Container pContainer;
    private Container levelWorkerContainer;
    private Container container;
    public MapPanel(Image background) {

        this.background=background;
        this.setLayout(new GridLayout(1,2));
        westContainer=new Container();
        westContainer.setPreferredSize(new Dimension(500,700));
        westContainer.setLayout(new GridBagLayout());
        container=new Container();
        container.setPreferredSize(new Dimension(700,700));
        container.setLayout(new GridBagLayout());

        boardContainer = new BoardContainer();
        boardContainer.setSize(700, 700);
        this.add(boardContainer);
        definePlayerContainer();
        undoContainer = new Container();
        undoContainer.setLayout(new GridBagLayout());

        undoContainer.setVisible(true);

        yesUndo = new JButton();
        noUndo = new JButton();
        defineButtons();
        yesUndo.setVisible(true);
        yesUndo.setEnabled(false);
        gbc.gridy=1;
        gbc.gridx=0;
        undoContainer.add(yesUndo , gbc);
        noUndo.setVisible(true);
        noUndo.setEnabled(false);
        gbc.gridy=1;
        gbc.gridx=1;
        gbc.insets = new Insets(0,30,0,30);
        undoContainer.add(noUndo , gbc);
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets=new Insets(50,0,50,0);
        westContainer.add(undoContainer,gbc);
        defineLevelImages();


        defineChatDecisionLabel();
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0,20,0,0);
        container.add(westContainer , gbc);
        this.add(container);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0,this.getWidth(),this.getHeight(), this);
    }



    private void defineLevelImages(){

        levelWorkerContainer = new Container();
        levelWorkerContainer.setLayout(new GridBagLayout());
        levelWorkerContainer.setSize(200,700);
        levelContainer = new Container();
        levelContainer.setLayout(new GridBagLayout());
        levelContainer.setSize(120,400);

        levels = new JLabel[4];
        levelImages = new ImageIcon[4];

        levels[0] = new JLabel("First");
        levels[1] = new JLabel("Second");
        levels[2] = new JLabel("Third");
        levels[3] = new JLabel("Dome");


        try {
            BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/Levels/first.png"));
            levelImages[0] = new ImageIcon(new ImageIcon(bufferedImage1).getImage().getScaledInstance(235/3, 215/3, Image.SCALE_DEFAULT));
            bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/Levels/second.png"));
            levelImages[1] = new ImageIcon((new ImageIcon(bufferedImage1).getImage().getScaledInstance(200/3,170/3,Image.SCALE_DEFAULT)));
            bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/Levels/third.png"));
            levelImages[2] = new ImageIcon((new ImageIcon(bufferedImage1).getImage().getScaledInstance(200/3,110/3,Image.SCALE_DEFAULT)));
            bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/Levels/dome.png"));
            levelImages[3] = new ImageIcon((new ImageIcon(bufferedImage1).getImage().getScaledInstance(120/3,85/3,Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        workerContainer = new Container();
        workerContainer.setLayout(new GridBagLayout());
        workerContainer.setSize(200,700*2/9);
        workerImages = new ImageIcon[2];


        workers = new JLabel[2];

        for(int i=0 ; i<2 ; i++) {

            workers[i] = new JLabel();
            workers[i].setHorizontalAlignment(SwingConstants.CENTER);
            workers[i].setVerticalAlignment(SwingConstants.CENTER);
            workers[i].setPreferredSize(new Dimension(70,125));
            gbc.gridx=0;
            gbc.gridy=i;
            workerContainer.add(workers[i],gbc);
        }


        gbc.gridx = 0;
        gbc.gridy = 0;
        levelWorkerContainer.add(workerContainer,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        levelWorkerContainer.add(levelContainer,gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        container.add(levelWorkerContainer,gbc);

    }
    private void definePlayerContainer(){
        pContainer = new Container();
        pContainer.setLayout(new GridLayout(1,3,10,10));
        pContainer.setPreferredSize(new Dimension(400,250));
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
            playerName[i].setFont(new Font ("Times new Roman" , Font.BOLD , 24));
            //playerName[i].setText("Meri");
            godPic[i] = new JLabel();
            godPic[i].setHorizontalAlignment(SwingConstants.CENTER);
            godPic[i].setVerticalAlignment(SwingConstants.CENTER);
            godPic[i].setPreferredSize(new Dimension(115,220));

            gbc.anchor = 10;
            gbc.gridx = 0;
            gbc.gridy = 0;
            playerContainer[i].add(playerName[i] , gbc);
            gbc.gridy = 1;
            playerContainer[i].add(godPic[i] , gbc);

        }


        /*ImageIcon godImage1 = new ImageIcon((new ImageIcon("src/main/resources/GodCards/apollo.png").getImage().getScaledInstance(115,200,Image.SCALE_DEFAULT)));
        godPic[0].setIcon(godImage1);
        ImageIcon godImage2 = new ImageIcon((new ImageIcon("src/main/resources/GodCards/pan.png").getImage().getScaledInstance(115,200,Image.SCALE_DEFAULT)));
        godPic[1].setIcon(godImage2);
        ImageIcon godImage3 = new ImageIcon((new ImageIcon("src/main/resources/GodCards/artemis.png").getImage().getScaledInstance(115,200,Image.SCALE_DEFAULT)));
        godPic[2].setIcon(godImage3);*/


        pContainer.add(playerContainer[0]);

        pContainer.add(playerContainer[1]);

        pContainer.add(playerContainer[2]);
        gbc.gridx=1;
        gbc.gridy=0;
        westContainer.add(pContainer,gbc);
    }


    private void defineButtons(){
        yesUndo = new JButton("OK");
        try {
            BufferedImage green = ImageIO.read(getClass().getResourceAsStream("/Buttons/btn_green.png"));
            ImageIcon greenButton = new ImageIcon(new ImageIcon(green).getImage().getScaledInstance(150,70,Image.SCALE_DEFAULT));
            yesUndo.setIcon(greenButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
        yesUndo.setSize(150,70);
        yesUndo.setHorizontalTextPosition(JButton.CENTER);
        yesUndo.setVerticalTextPosition(JButton.CENTER);
        yesUndo.setFont(new Font("Times New Roman", Font.BOLD, 24));
        yesUndo.setForeground(Color.WHITE);
        yesUndo.setOpaque(false);
        yesUndo.setContentAreaFilled(false);
        yesUndo.setBorderPainted(false);
        yesUndo.setBorder(null);
        noUndo = new JButton("UNDO");
        try {
            BufferedImage red = ImageIO.read(getClass().getResourceAsStream("/Buttons/btn_coral.png"));
            ImageIcon redButton = new ImageIcon(new ImageIcon(red).getImage().getScaledInstance(150,70,Image.SCALE_DEFAULT));
            noUndo.setIcon(redButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
        noUndo.setSize(150,70);
        noUndo.setHorizontalTextPosition(JButton.CENTER);
        noUndo.setVerticalTextPosition(JButton.CENTER);
        noUndo.setFont(new Font("Times New Roman", Font.BOLD, 24));
        noUndo.setForeground(Color.WHITE);
        noUndo.setOpaque(false);
        noUndo.setContentAreaFilled(false);
        noUndo.setBorderPainted(false);
        noUndo.setBorder(null);
    }
    private void defineTextPane(JTextPane textPane){
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(attributes,18);
        StyleConstants.setForeground(attributes,Color.WHITE);
        textPane.setParagraphAttributes(attributes, true);
        textPane.setEditable(false);
        textPane.setOpaque(false);


    }
    private void defineChatDecisionLabel(){
        chat = new JLabel();
        decision = new JLabel();
        chat.setSize(400,100);
        try {
            BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/bluechat.png"));
            ImageIcon chatImage = new ImageIcon(new ImageIcon(bufferedImage1).getImage().getScaledInstance(400, 100, Image.SCALE_DEFAULT));

            chat.setIcon(chatImage);
            decision.setIcon(chatImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textChat = new JTextPane();
        defineTextPane(textChat);

        chat.setHorizontalAlignment(SwingConstants.CENTER);
        chat.setVerticalAlignment(SwingConstants.CENTER);
        chat.setHorizontalTextPosition(SwingConstants.CENTER);
        chat.setVerticalTextPosition(SwingConstants.CENTER);
        chat.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.WHITE));
        chat.setLayout(new BorderLayout());
        chat.add(textChat , BorderLayout.CENTER);
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
        textDecisio = new JTextPane();
        defineTextPane(textDecisio);
        decision.setLayout(new BorderLayout());
        decision.setSize(400, 100);
        decision.setHorizontalAlignment(SwingConstants.CENTER);
        decision.setVerticalAlignment(SwingConstants.NORTH);
        decision.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.WHITE));
        decisionContainer.add(yes);
        decisionContainer.add(no);
        decision.add(textDecisio,BorderLayout.PAGE_START);
        decision.add(decisionContainer , BorderLayout.PAGE_END);
        decision.setVisible(false);

        gbc.gridx=1;
        gbc.gridy=2;
        westContainer.add(chat,gbc);
        westContainer.add(decision,gbc);
    }


    public void setChat(String command){
        textChat.setText(command);
    }

    public void setWorkerBorder(int nr ){
        workers[nr].setBorder(BorderFactory.createLineBorder(Color.RED , 2));
    }
    public ImageIcon getWorkerImages(String color){
        ImageIcon imageIcon = null;
        try {
            BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/Workers/" + color.toLowerCase() + ".png"));
            imageIcon = new ImageIcon((new ImageIcon(bufferedImage1).getImage().getScaledInstance(70, 125, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageIcon;
    }

    public Container getWorkerContainer() {
        return workerContainer;
    }

    public Container getLevelContainer() {
        return levelContainer;
    }

    public void setWorkerImages(String color ){
        for(int i=0 ; i<2 ;i++) {
            try {
                BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/Workers/" + color.toLowerCase() + ".png"));
                workerImages[i] = new ImageIcon((new ImageIcon(bufferedImage1).getImage().getScaledInstance(70, 125, Image.SCALE_DEFAULT)));
                workers[i].setIcon(workerImages[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BoardContainer getBoardContainer() {
        return boardContainer;
    }

    public void setGodPic(String god , int index) {
        try {
            BufferedImage bufferedImage1 = ImageIO.read(getClass().getResourceAsStream("/GodCards/"+god.toLowerCase()+".png"));
            ImageIcon godImage = new ImageIcon((new ImageIcon(bufferedImage1).getImage().getScaledInstance(115,220,Image.SCALE_DEFAULT)));
            godPic[index].setIcon(godImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        godPic[index].setVisible(true);
    }
    public void setPlayerName(String name , int index , String color){
        playerName[index].setText(name.toUpperCase());
        switch (color){
            case "RED" :
                playerName[index].setForeground(Color.RED);
                break;
            case "BLUE" :
                playerName[index].setForeground(Color.BLUE);
                break;
            case "WHITE" :
                playerName[index].setForeground(Color.WHITE);
                break;
        }

    }


    public void setLevelImages(int nr ){
        levels[nr].setBorder(BorderFactory.createLineBorder(Color.RED , 2));
    }

    public JLabel getLevel(int nr){
        return levels[nr];
    }

    public void makeDecision(String string){
        chat.setVisible(false);
        yes.setEnabled(true);
        no.setEnabled(true);
        decision.setVisible(true);
        textDecisio.setText(string);
    }

    public JButton getYes() {
        return yes;
    }

    public JButton getNo() {
        return no;
    }

    /*public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Start");
        BufferedImage myImage = ImageIO.read(new File("src/main/resources/newBackground.png"));
        frame.setContentPane(new MapPanel(myImage));
        frame.pack();
        frame.setSize(1400,700);
        frame.setVisible(true);

    }*/

    /*public JLabel getUndoContainer() {
        System.out.println("get");
        return this.undo;
    }*/
    public JButton getYesUndo() {
        return yesUndo;
    }

    public JButton getNoUndo() {
        return noUndo;
    }

}
