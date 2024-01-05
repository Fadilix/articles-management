package views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import components.TopNavBar;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import database.DatabaseConnection;

public class ListeCategories extends JFrame {

    private JTable tableCategories;

    public ListeCategories(Connection connection) {
        // Configuration de la fenêtre
        this.setTitle("Liste des Catégories d'articles");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

           TopNavBar topNavBar = new TopNavBar(this, connection);
        this.setJMenuBar(topNavBar);

        // Création d'un modèle de tableau et définition des noms de colonnes
        DefaultTableModel modeleTableau = new DefaultTableModel();
        modeleTableau.addColumn("Catégories");

        // Récupération des données depuis la base de données et ajout au modèle de tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT designationCat FROM article");

            while (resultSet.next()) {
                Object[] ligneDonnees = { resultSet.getString("designationCat") };
                modeleTableau.addRow(ligneDonnees);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du JTable avec le modèle de tableau rempli
        tableCategories = new JTable(modeleTableau);
        tableCategories.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size
        tableCategories.setRowHeight(30);

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableCategories);

        // Ajout du JScrollPane à la fenêtre avec un titre border
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Catégories");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 20)); // Set border title font size
        scrollPane.setBorder(titledBorder);
        this.add(scrollPane);

        // Définition des propriétés de la fenêtre
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection existingConnection = databaseConnection.getConnection();
        new ListeCategories(existingConnection);
    }
}
