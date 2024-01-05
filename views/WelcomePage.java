package views;

import javax.swing.*;
import components.TopNavBar;
import database.DatabaseConnection;
import java.awt.*;
import java.sql.Connection;

public class WelcomePage extends JFrame {

    private String username;
    private JLabel welcomeLabel;

    public WelcomePage(String username) {
        this.username = username;

        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        this.setTitle("Welcome");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating components
        welcomeLabel = new JLabel("Welcome, " + username + "!");

        // Increase the font size for the welcome message
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));

        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.CENTER);

        // Adding the label to the content pane
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(welcomeLabel, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {

        new WelcomePage("JohnDoe");
    }
}
