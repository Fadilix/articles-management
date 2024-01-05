package views;

import database.DatabaseConnection;

import javax.swing.*;
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Styling
        Font labelFont = new Font("Arial", Font.PLAIN, 24);
        Font inputFont = new Font("Arial", Font.PLAIN, 20);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 20));

        usernameField = new JTextField(20);
        usernameField.setFont(inputFont);
        passwordField = new JPasswordField(20);
        passwordField.setFont(inputFont);
        loginButton = new JButton("Login");
        loginButton.setFont(inputFont);

        loginButton.addActionListener(this);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
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
            JOptionPane.showMessageDialog(this, "Username and password are required.", "Error",
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
                    JOptionPane.showMessageDialog(this, "Login successful.", "Welcome",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                    new WelcomePage(username);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error",
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
