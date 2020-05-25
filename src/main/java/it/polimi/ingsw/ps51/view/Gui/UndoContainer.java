package it.polimi.ingsw.ps51.view.Gui;

import javax.swing.*;
import java.awt.*;

public class UndoContainer extends Container {

    private JLabel text;
    private JButton yes;
    private JButton no;
    private Container buttonContainer;

    public UndoContainer(){

        this.setSize(300 , 100);
        this.setLayout(new BorderLayout());
        buttonContainer = new Container();
        buttonContainer.setSize(865*2/3,30);
        buttonContainer.setLayout(new GridLayout(1,2));
        yes = new JButton("YES");
        yes.setBorder(BorderFactory.createMatteBorder(5,5,5,5, new Color(0,102, 0)));
        defineButtons(yes);
        no = new JButton("NO");
        no.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.RED));

        defineButtons(no);
        text = new JLabel();
        text.setText("Do you confirm your choice ?");
        text.setLayout(new BorderLayout());
        text.setLocation(395,650);
        text.setSize(865 * 2 / 3, 70);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.NORTH);
        text.setForeground(Color.BLUE);
        text.setFont(new Font ("Times new Roman" , Font.ITALIC , 18));
        text.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.BLUE));
        buttonContainer.add(yes);
        buttonContainer.add(no);
        this.add(buttonContainer , BorderLayout.PAGE_END);

        this.add(text);
    }
    
    private void defineButtons(JButton button){
        button.setSize(100,20);
        button.setForeground(new Color(0,102, 0));
        button.setFont(new Font ("Times new Roman" , Font.ITALIC , 18));
        button.setOpaque(false);
        button.setContentAreaFilled(false);

    }
}
