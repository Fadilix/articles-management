package views;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import components.TopNavBar;
import database.DatabaseConnection;

public class ListeArticlesVendus extends JFrame {

    private JTable tableArticlesVendus;

    public ListeArticlesVendus() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        // Configuration de la fenêtre
        this.setTitle("Liste des Articles Vendus");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

           TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        // Création d'un modèle de tableau et définition des noms de colonnes
        DefaultTableModel modeleTableau = new DefaultTableModel();
        modeleTableau.addColumn("ID Article");
        modeleTableau.addColumn("Libellé");
        modeleTableau.addColumn("Prix");
        modeleTableau.addColumn("Date de Vente");
        modeleTableau.addColumn("Catégorie");
        modeleTableau.addColumn("Quantité Vendue");
        modeleTableau.addColumn("Prix Total");
        modeleTableau.addColumn("Fournisseur");

        // Récupération des données depuis la base de données et ajout au modèle de tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM articlevendu");

            while (resultSet.next()) {
                Object[] ligneDonnees = {
                        resultSet.getInt("idArticle"),
                        resultSet.getString("libel"),
                        resultSet.getDouble("prix"),
                        resultSet.getString("dateDeVente"),
                        resultSet.getString("designationCat"),
                        resultSet.getInt("quantiteVendu"),
                        resultSet.getDouble("prixTotal"),
                        resultSet.getString("fournisseur")
                };
                modeleTableau.addRow(ligneDonnees);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du JTable avec le modèle de tableau rempli
        tableArticlesVendus = new JTable(modeleTableau);

        // Définir la taille de la police pour les éléments du tableau
        tableArticlesVendus.setFont(new Font("Arial", Font.PLAIN, 20));

        // Définir la hauteur des lignes pour augmenter la taille des éléments
        tableArticlesVendus.setRowHeight(30);

        // Ajouter un titre bordé à la fenêtre
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Liste des Articles Vendus");
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableArticlesVendus);

        // Ajout du JScrollPane à la fenêtre
        this.add(scrollPane);

        // Définition des propriétés de la fenêtre
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListeArticlesVendus());
    }
}
