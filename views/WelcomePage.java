package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame implements ActionListener {

    private String username;
    private JLabel welcomeLabel;
    private JButton sellButton;
    private JButton viewSoldItemsButton;
    private JButton viewSupplyHistoryButton;
    private JButton logoutButton;

    public WelcomePage(String username) {
        this.username = username;

        this.setTitle("Welcome");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating components
        welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        sellButton = new JButton("Sell Item");
        viewSoldItemsButton = new JButton("View Sold Items");
        viewSupplyHistoryButton = new JButton("View Supply History");
        logoutButton = new JButton("Logout");

        // Adding action listeners to the buttons
        sellButton.addActionListener(this);
        viewSoldItemsButton.addActionListener(this);
        viewSupplyHistoryButton.addActionListener(this);
        logoutButton.addActionListener(this);

        // Creating navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(1, 4));
        navPanel.add(sellButton);
        navPanel.add(viewSoldItemsButton);
        navPanel.add(viewSupplyHistoryButton);
        navPanel.add(logoutButton);

        // Creating main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(navPanel, BorderLayout.NORTH);
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.CENTER);

        // Adding main panel to the frame
        this.add(mainPanel);

        // Setting frame properties
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sellButton) {
            // Handle sell button action
            JOptionPane.showMessageDialog(this, "Redirect to sell item page.");
        } else if (e.getSource() == viewSoldItemsButton) {
            // Handle view sold items button action
            JOptionPane.showMessageDialog(this, "Redirect to view sold items page.");
        } else if (e.getSource() == viewSupplyHistoryButton) {
            // Handle view supply history button action
            JOptionPane.showMessageDialog(this, "Redirect to view supply history page.");
        } else if (e.getSource() == logoutButton) {
            // Handle logout button action
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                // Perform logout action
                this.dispose(); // Close the current page
                new Login(); // Open the login page
            }
        }
    }

    public static void main(String[] args) {
        new WelcomePage("JohnDoe");
    }
}
