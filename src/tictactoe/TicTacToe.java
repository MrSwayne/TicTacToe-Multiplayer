package tictactoe;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

import network.ServerSocket;

import java.awt.*;
import java.awt.event.* ;
/**
 *
 * @author Adam
 */

public class TicTacToe extends JFrame implements ActionListener {  
    private JTextField userField;
    private JPasswordField passwordField;
    private JPanel container = new JPanel();
    private final int N_ROWS = 3;
    private final int N_PLAYERS = 2;
    private Thread[] threads = new Thread[N_PLAYERS + 1];
    
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.run();
    }
    
    
    public TicTacToe() {
                this.setVisible(true);
                this.setTitle("TicTacToe");
    }
    
    public void run() {
        showLoginPanel();
    }
    
    private void showLoginPanel() {
        container = new JPanel(new GridLayout(3,1));
        
        JPanel user = new JPanel();
        JLabel userLabel = new JLabel("Username: ", JLabel.CENTER);
        userField = new JTextField("", 10);
        
        user.add(userLabel);
        user.add(userField);
        user.setVisible(true);
        
        JPanel password = new JPanel();
        JLabel passwordLabel = new JLabel("Password: ", JLabel.CENTER);
        passwordField = new JPasswordField("", 10);
        password.setVisible(true);
        password.add(passwordLabel);
        password.add(passwordField);
        
        
        JPanel control = new JPanel();
        control.setVisible(true);
        JButton button = new JButton("Login");
        button.setActionCommand("login");
        button.addActionListener(this);
        control.add(button);
        
        container.add(user);
        container.add(password);
        container.add(control);
        container.setVisible(true);
        this.add(container);
        this.pack();
    }
    
    private void loadGame() {
        container = new JPanel(new GridLayout(1,1));
        
        
        ServerSocket server;
        
        
        JPanel gamep = new JPanel(new GridLayout(3,3));
        
        for(int i = 0;i < N_ROWS;i++) {
            for(int j = 0;j < N_ROWS;j++) {
                JButton btn = new JButton("");
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                btn.setActionCommand("Button " + i + " " + j);
                gamep.add(btn,i,j);
            }
        }
        
        JPanel controlp = new JPanel(new GridLayout(4,0));
        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");
        JPanel scorePanel = new JPanel();
        JLabel scoreTLabel = new JLabel("Score: ", JLabel.CENTER);
        JLabel scoreLabel = new JLabel("0", JLabel.CENTER);
        scorePanel.add(scoreTLabel);
        scorePanel.add(scoreLabel);
        
        JPanel turnPanel = new JPanel();
        JLabel turnTLabel = new JLabel("Turn: ");
        JLabel turnLabel = new JLabel("N/A");
        turnPanel.add(turnTLabel);
        turnPanel.add(turnLabel);
        
        controlp.add(turnPanel,3,0);
        controlp.add(startButton,0,0);
        controlp.add(quitButton,1,0);
        controlp.add(scorePanel,2,0);

        container.add(gamep, 0, 0);
        container.add(controlp,0,1);
        this.add(container);
        this.pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if(str.equalsIgnoreCase("login")) {
            this.remove(container);
            loadGame();
        } if(str.startsWith("Button")) {
        }
    }
}
