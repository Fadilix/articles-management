package views;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import components.TopNavBar;
import database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        setTitle("User Login");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Adding the top navbar
        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        // Styling
        Font labelFont = new Font("Manrope", Font.PLAIN, 24);
        Font titleFont = new Font("Manrope", Font.BOLD, 28);
        Font inputFont = new Font("Manrope", Font.PLAIN, 20);
        UIManager.put("OptionPane.messageFont", new Font("Manrope", Font.PLAIN, 20));

        // Form Panel with Border and Background Color
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240)); // Light Gray Background
        formPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(0, 102, 204), 2, true), "Connection", TitledBorder.CENTER, TitledBorder.TOP, titleFont, new Color(0, 102, 204)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        usernameField = new JTextField(20);
        usernameField.setFont(inputFont);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1, true)); // Subtle Border
        usernameField.setBackground(new Color(255, 255, 255)); // White Background

        passwordField = new JPasswordField(20);
        passwordField.setFont(inputFont);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1, true)); // Subtle Border
        passwordField.setBackground(new Color(255, 255, 255)); // White Background

        loginButton = new JButton("Login");
        loginButton.setFont(inputFont);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding

        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        usernameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);

        add(formPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            loginUser();
        }
    }

    private void loginUser() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom d'utilisateur et le mot de passe sont requis", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = new DatabaseConnection().getConnection()) {
            String selectQuery = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Connexion réussie", "Welcome",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                    new WelcomePage(username);
                } else {
                    JOptionPane.showMessageDialog(this, "Nom d'utiliasteur ou mot de passe erroné", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error accessing the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
