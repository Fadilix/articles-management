package views;

import javax.swing.*;

import database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == approvisionnerButton) {
            try {
                int quantiteApprovisionnee = Integer.parseInt(quantiteField.getText());

                String updateQuery = "UPDATE article SET quantiteEnStock = quantiteEnStock + ? WHERE idArticle = ?";
                try (PreparedStatement preparedStatement = existingConnection.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, quantiteApprovisionnee);
                    preparedStatement.setInt(2, idArticle);
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
