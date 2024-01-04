package views;

import javax.swing.*;

import database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VendreArticle extends JFrame implements ActionListener {

    private JTextField quantiteField;
    private JTextField fournisseurField;
    private JButton vendreButton;
    private int idArticle;
    private Connection connection;

    public VendreArticle(int idArticle, Connection connection) {
        this.idArticle = idArticle;
        this.connection = connection;

        this.setTitle("Vendre Article");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création des composants
        quantiteField = new JTextField(10);
        fournisseurField = new JTextField(10);
        vendreButton = new JButton("Vendre");

        // Ajout des écouteurs d'événements
        vendreButton.addActionListener(this);

        // Création du formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2));
        formPanel.add(new JLabel("Quantité :"));
        formPanel.add(quantiteField);
        formPanel.add(new JLabel("Fournisseur :"));
        formPanel.add(fournisseurField);
        formPanel.add(new JLabel("")); // espace vide
        formPanel.add(vendreButton);

        // Ajout du formulaire à la fenêtre
        this.add(formPanel);

        // Définition des propriétés de la fenêtre
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vendreButton) {
            vendreArticle();
        }
    }

    private void vendreArticle() {
        try {
            int quantiteVendue = Integer.parseInt(quantiteField.getText());
            String fournisseur = fournisseurField.getText();
            double prixUnitaire = getPrixUnitaire(idArticle);
            double prixTotal = quantiteVendue * prixUnitaire;

            // Insertion des données dans la table ArticleVendu
            String insertQuery = "INSERT INTO ArticleVendu (libel, prix, dateDeVente, designationCat, quantiteVendu, prixTotal, fournisseur) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, getLibelle(idArticle));
                preparedStatement.setDouble(2, prixUnitaire);
                preparedStatement.setDate(3, getCurrentDate());
                preparedStatement.setString(4, getDesignationCategorie(idArticle));
                preparedStatement.setInt(5, quantiteVendue);
                preparedStatement.setDouble(6, prixTotal);
                preparedStatement.setString(7, fournisseur);

                preparedStatement.executeUpdate();
            }

            // Mise à jour de la quantité en stock dans la table Article
            String updateQuery = "UPDATE Article SET quantiteEnStock = quantiteEnStock - ? WHERE idArticle = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, quantiteVendue);
                preparedStatement.setInt(2, idArticle);

                preparedStatement.executeUpdate();
            }

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(this, "L'article a été vendu avec succès.");

            // Fermeture de la fenêtre après la vente
            this.dispose();

        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la vente de l'article.");
        }
    }

    private String getLibelle(int idArticle) throws SQLException {
        String libelle = null;
        String sql = "SELECT libel FROM Article WHERE idArticle = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idArticle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                libelle = resultSet.getString("libel");
            }
        }

        return libelle;
    }

    private double getPrixUnitaire(int idArticle) throws SQLException {
        String sql = "SELECT prix FROM Article WHERE idArticle = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idArticle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("prix");
            } else {
                throw new SQLException("Article not found for id: " + idArticle);
            }
        }
    }

    private String getDesignationCategorie(int idArticle) throws SQLException {
        String sql = "SELECT designationCat FROM Article WHERE idArticle = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idArticle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("designationCat");
            } else {
                throw new SQLException("Article not found for id: " + idArticle);
            }
        }
    }

    private java.sql.Date getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        return java.sql.Date.valueOf(dateStr);
    }

    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection existingConnection = connection.getConnection();
        new VendreArticle(1, existingConnection);
    }
}