package views;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import components.TopNavBar;
import database.DatabaseConnection;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnregistrementArticle extends JFrame {

    private JButton boutonValider;
    private JTextField libel, prix, quantiteSeuil, designationCat;

    private final Connection connection;

    public EnregistrementArticle() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        this.connection = connection;
        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        libel = new JTextField(30); // Increased input size
        prix = new JTextField(30);
        quantiteSeuil = new JTextField(30);
        designationCat = new JTextField(30);
        boutonValider = new JButton("Valider");

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Enregistrement d'un Nouvel Article", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 10, 5); // Added margin-bottom

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel libelleLabel = new JLabel("Libellé:");
        libelleLabel.setFont(labelFont);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(libelleLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        libel.setFont(inputFont);
        panel.add(libel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel prixLabel = new JLabel("Prix:");
        prixLabel.setFont(labelFont);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(prixLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        prix.setFont(inputFont);
        panel.add(prix, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel quantiteSeuilLabel = new JLabel("Quantité seuil:");
        quantiteSeuilLabel.setFont(labelFont);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(quantiteSeuilLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        quantiteSeuil.setFont(inputFont);
        panel.add(quantiteSeuil, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel designationCatLabel = new JLabel("Désignation Catégorie:");
        designationCatLabel.setFont(labelFont);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(designationCatLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        designationCat.setFont(inputFont);
        panel.add(designationCat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        boutonValider.setFont(inputFont);
        panel.add(boutonValider, gbc);

        this.add(panel);
        this.setTitle("Enregistrement d'Article");
        this.setSize(600, 500); // Adjusted size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        boutonValider.addActionListener(e -> insertIntoDatabase());
    }

    private void insertIntoDatabase() {
        try {
            // Validate input data
            if (libel.getText().isEmpty() || prix.getText().isEmpty() || quantiteSeuil.getText().isEmpty()
                    || designationCat.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate numeric input
            try {
                Double.parseDouble(prix.getText());
                Integer.parseInt(quantiteSeuil.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Les champs 'Prix' et 'Quantité seuil' doivent être des nombres valides.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO article (libel, prix, quantiteEnStock, dateDeCrea, quantiteSeuil, designationCat) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, libel.getText());
            preparedStatement.setDouble(2, Double.parseDouble(prix.getText()));
            preparedStatement.setInt(3, 0);
            java.util.Date currentDate = new java.util.Date();
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(currentDate.getTime()));
            preparedStatement.setInt(5, Integer.parseInt(quantiteSeuil.getText()));
            preparedStatement.setString(6, designationCat.getText());

            int rowsInserted = preparedStatement.executeUpdate();

            String categorieSql = "INSERT INTO categorie (designation) VALUES (?)";
            PreparedStatement prepCatStatement = connection.prepareStatement(categorieSql);
            prepCatStatement.setString(1, designationCat.getText());

            int catRows = prepCatStatement.executeUpdate();

            if (rowsInserted > 0 && catRows > 0) {
                JOptionPane.showMessageDialog(this, "L'enregistrement a été inséré avec succès !", "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion de l'enregistrement.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }

            preparedStatement.close();
            prepCatStatement.close();
            this.setVisible(false);

            new PageSucces();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnregistrementArticle());
    }
}
