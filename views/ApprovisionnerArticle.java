package views;

import javax.swing.*;

import components.TopNavBar;
import database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ApprovisionnerArticle extends JFrame implements ActionListener {

    private int idArticle;
    private Connection existingConnection;

    private JTextField quantiteField;
    private JButton approvisionnerButton;

    public ApprovisionnerArticle(int idArticle, Connection existingConnection) {
        this.idArticle = idArticle;
        this.existingConnection = existingConnection;

        // Paramètres de l'écran
        this.setTitle("Approvisionner Article");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());


        // Création des composants
        JLabel quantiteLabel = new JLabel("Quantité à approvisionner:");
        quantiteField = new JTextField(10);

        approvisionnerButton = new JButton("Approvisionner");

        // Ajoute des ActionListener
        approvisionnerButton.addActionListener(this);

        // Creattion de la div pour les composants
        JPanel panel = new JPanel();
        panel.add(quantiteLabel);
        panel.add(quantiteField);
        panel.add(approvisionnerButton);

        // Ajout du panel
        this.add(panel, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private String getArticleName(int articleId) {
    // Fetch article name from the database based on articleId
    String articleName = "";
    String query = "SELECT libel FROM article WHERE idArticle = ?";
    try (PreparedStatement preparedStatement = existingConnection.prepareStatement(query)) {
        preparedStatement.setInt(1, articleId);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                articleName = resultSet.getString("libel");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return articleName;
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == approvisionnerButton) {
            try {
                int quantiteApprovisionnee = Integer.parseInt(quantiteField.getText());

                // Update the stock quantity in the article table
                String updateQuery = "UPDATE article SET quantiteEnStock = quantiteEnStock + ? WHERE idArticle = ?";
                try (PreparedStatement preparedStatement = existingConnection.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, quantiteApprovisionnee);
                    preparedStatement.setInt(2, idArticle);
                    preparedStatement.executeUpdate();
                }

                

                // Insert supply information into the approvisionnement table
                String insertQuery = "INSERT INTO approvisionnement (articleAppro, dateAppro, quantiteAppro) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = existingConnection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, getArticleName(idArticle));
                    preparedStatement.setDate(2, new java.sql.Date(new Date().getTime()));
                    preparedStatement.setInt(3, quantiteApprovisionnee);
                    preparedStatement.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Article approvisionné avec succès.");
                this.dispose();
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection existingConnection = databaseConnection.getConnection();
                new ListeArticles(existingConnection);

            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'approvisionnement de l'article.");
            }
        }
    }

    public static void main(String[] args) {
        // Test
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection existingConnection = databaseConnection.getConnection();
        new ApprovisionnerArticle(1, existingConnection);
    }
}
