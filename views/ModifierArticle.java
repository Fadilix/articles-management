package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import components.TopNavBar;
import database.DatabaseConnection;

public class ModifierArticle extends JFrame implements ActionListener {

    private JLabel label;
    private JButton boutonValider;
    private JTextField libel, prix, quantiteStock, quantiteSeuil, designationCat;

    private final int idArticle;

    public ModifierArticle(int idArticle) {
        this.idArticle = idArticle;

        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        label = new JLabel("Modification de l'article avec ID : " + idArticle);
        this.setSize(800, 600);

        libel = new JTextField(20);
        prix = new JTextField(20);
        quantiteStock = new JTextField(20);
        quantiteSeuil = new JTextField(20);
        designationCat = new JTextField(20);
        boutonValider = new JButton("Valider");

        // Remplir les champs avec les valeurs de la base de données
        remplirChamps();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Ajouter une marge au panneau
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel libelleLabel = new JLabel("Libellé :");
        libelleLabel.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(libelleLabel, gbc);
        gbc.gridx++;
        libel.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(libel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel prixLabel = new JLabel("Prix :");
        prixLabel.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(prixLabel, gbc);
        gbc.gridx++;
        prix.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(prix, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel quantiteStockLabel = new JLabel("Quantité en stock :");
        quantiteStockLabel.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(quantiteStockLabel, gbc);
        gbc.gridx++;
        quantiteStock.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(quantiteStock, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel quantiteSeuilLabel = new JLabel("Quantité seuil :");
        quantiteSeuilLabel.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(quantiteSeuilLabel, gbc);
        gbc.gridx++;
        quantiteSeuil.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(quantiteSeuil, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel designationCatLabel = new JLabel("Désignation Catégorie :");
        designationCatLabel.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(designationCatLabel, gbc);
        gbc.gridx++;
        designationCat.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(designationCat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        boutonValider.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(boutonValider, gbc);

        boutonValider.addActionListener(this);

        this.add(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void remplirChamps() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            String query = "SELECT libel, prix, quantiteEnStock, quantiteSeuil, designationCat FROM article WHERE idArticle = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idArticle);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                libel.setText(resultSet.getString("libel"));
                prix.setText(String.valueOf(resultSet.getDouble("prix")));
                quantiteStock.setText(String.valueOf(resultSet.getInt("quantiteEnStock")));
                quantiteSeuil.setText(String.valueOf(resultSet.getInt("quantiteSeuil")));
                designationCat.setText(resultSet.getString("designationCat"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonValider) {
            if (validateInput()) {
                try {
                    DatabaseConnection databaseConnection = new DatabaseConnection();
                    Connection connection = databaseConnection.getConnection();
                    String sql = "UPDATE article SET libel = ?, prix = ?, quantiteEnStock = ?, quantiteSeuil = ?, designationCat = ? WHERE idArticle = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, libel.getText());
                    preparedStatement.setDouble(2, Double.parseDouble(prix.getText()));
                    preparedStatement.setInt(3, Integer.parseInt(quantiteStock.getText()));
                    preparedStatement.setInt(4, Integer.parseInt(quantiteSeuil.getText()));
                    preparedStatement.setString(5, designationCat.getText());
                    preparedStatement.setInt(6, idArticle);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Mise à jour réussie !");
                        JOptionPane.showMessageDialog(this, "Mise à jour réussie !");
                    } else {
                        System.out.println("La mise à jour a échoué.");
                    }
                    new ListeArticles();
                    this.dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private boolean validateInput() {
        // Valider les champs de saisie
        if (!isDouble(prix.getText()) || !isInteger(quantiteStock.getText()) || !isInteger(quantiteSeuil.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez saisir des valeurs valides pour le prix, la quantité en stock et la quantité seuil.",
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Valider que le prix et la quantité ne sont pas inférieurs à 1
        double prixValue = Double.parseDouble(prix.getText());
        int quantiteStockValue = Integer.parseInt(quantiteStock.getText());
        int quantiteSeuilValue = Integer.parseInt(quantiteSeuil.getText());

        if (prixValue < 1 || quantiteStockValue < 1 || quantiteSeuilValue < 1) {
            JOptionPane.showMessageDialog(this, "Le prix et la quantité en stock doivent être supérieurs ou égaux à 1.",
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Logique de validation supplémentaire peut être ajoutée ici...

        return true;
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        // Exemple d'utilisation
        new ModifierArticle(1);
    }
}
