package it.polimi.ingsw.ps51.view.Gui;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class LogInPanel extends JPanel {


    private JLabel nicknameError;
    private JButton submitButton;
    private BufferedImage image;
    private Container container;
    private boolean nicknameErr;
    private JLabel welcome;
    private JLabel enterNickname;
    private JTextField nickname;


    public LogInPanel() {

        this.setLayout(new BorderLayout());
        container = new Container();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 10);

        ImageIcon welcomeImage = new ImageIcon((new ImageIcon("src/main/resources/welcome.png").getImage().getScaledInstance(520*2/3,585*2/3,Image.SCALE_DEFAULT)));

        welcome = new JLabel(welcomeImage);
        add(welcome , BorderLayout.NORTH);

        enterNickname = new JLabel();
        enterNickname.setText("Enter Nickname :");
        enterNickname.setFont(new Font("Times New Roman", Font.BOLD, 36));
        enterNickname.setForeground(new Color(102,0,153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(enterNickname , gbc);

        nickname = new JTextField(15);
        nickname.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        nickname.setForeground(new Color(102,0,153));

        gbc.gridx = 1;
        container.add(nickname , gbc);

        nicknameError = new JLabel("Please enter a Nickname !!");
        nicknameError.setVisible(false);
        nicknameError.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        nicknameError.setForeground(new Color(102,0,153));
        gbc.gridy = 1;
        container.add(nicknameError , gbc);

        ImageIcon purpleButton = new ImageIcon((new ImageIcon("src/main/resources/Buttons/btn_purple.png").getImage().getScaledInstance(415*2/3,105*2/3,Image.SCALE_DEFAULT)));
        submitButton = new JButton("Submit");
        submitButton.setIcon(purpleButton);
        submitButton.setSize(415*2/3,105*2/3);
        submitButton.setHorizontalTextPosition(JButton.CENTER);
        submitButton.setVerticalTextPosition(JButton.CENTER);
        submitButton.setFont(new Font("Times New Roman", Font.BOLD, 24));
        submitButton.setForeground(Color.WHITE);
        submitButton.setOpaque(false);
        submitButton.setContentAreaFilled(false);
        submitButton.setBorderPainted(false);
        submitButton.setBorder(null);

        add(submitButton , BorderLayout.SOUTH);

        add(container , BorderLayout.CENTER);


    }

    public JButton getSubmitButton() {
        return submitButton;
    }


    public String getNickname() {
        return nickname.getText();
    }


    public void setNicknameErr(String err) {

        nicknameError.setText(err);
        nicknameError.setVisible(true);
    }

    public void setNicknameError(Boolean err) {
        nicknameError.setVisible(err);
        nicknameErr = !err;
    }


    public boolean getNicknameErr() {
        return nicknameErr;
    }

  /*  public static void main(String[] args) {
        JFrame frame = new JFrame("Start");
        LogInPanel logInPanel = new LogInPanel();
        frame.add(logInPanel);
        frame.setSize(635, 635);
        frame.setResizable(false);
        frame.setVisible(true);

    }*/


}

