package views;

import components.TopNavBar;
import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JPasswordField confirmPasswordField;

    public Register() {
        setTitle("User Registration");
        setSize(1920, 1080);
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
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2, true), "Inscription", TitledBorder.CENTER, TitledBorder.TOP, titleFont, new Color(0, 102, 204)));

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

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(inputFont);
        confirmPasswordField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1, true)); // Subtle Border
        confirmPasswordField.setBackground(new Color(255, 255, 255)); // White Background

        registerButton = new JButton("Enregistrer");
        registerButton.setFont(inputFont);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(0, 102, 204));
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding

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

        JLabel confirmPasswordLabel = new JLabel("Confirmer le mot de passe:");
        confirmPasswordLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(registerButton, gbc);

        add(formPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            registerUser();
        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        char[] confirmPasswordChars = confirmPasswordField.getPassword();
        String password = new String(passwordChars);
        String confirmPassword = new String(confirmPasswordChars);

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont requis", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = new DatabaseConnection().getConnection()) {
            String insertQuery = "INSERT INTO user (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Vous êtes enregistré avec succès", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la création du compte", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Register());
    }
}
