package views;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import database.DatabaseConnection;

public class ListeCategories extends JFrame {

    private JTable tableCategories;

    public ListeCategories(Connection connexion) {
        // Configuration de la fenêtre
        this.setTitle("Liste des Catégories d'articles");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création d'un modèle de tableau et défnition des noms de colonnes
        DefaultTableModel modeleTableau = new DefaultTableModel();
        modeleTableau.addColumn("Catégories");

        // Récupération des données depuis la base de données et ajout au modèle de tableau
        try {
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT designationCat FROM article");

            while (resultSet.next()) {
                Object[] ligneDonnees = {
                        resultSet.getString("designationCat"),
                };
                modeleTableau.addRow(ligneDonnees);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du JTable avec le modèle de tableau rempli
        tableCategories = new JTable(modeleTableau);

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableCategories);

        // Ajout du JScrollPane à la fenêtre
        this.add(scrollPane);

        // Définition des propriétés de la fenêtre
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection existingConnection = databaseConnection.getConnection();
        new ListeCategories(existingConnection);
    }
}
