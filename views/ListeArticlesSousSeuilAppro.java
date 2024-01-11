package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import components.TopNavBar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import database.DatabaseConnection;

public class ListeArticlesSousSeuilAppro extends JFrame {

    private JTable tableArticlesSousSeuil;

    public ListeArticlesSousSeuilAppro() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        this.setTitle("Liste des Articles sous le seuil d'approvisionnement");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        // Création d'un modèle de tableau et définir les noms de colonnes
        DefaultTableModel modeleTableau = new DefaultTableModel();
        modeleTableau.addColumn("ID");
        modeleTableau.addColumn("Libellé");
        modeleTableau.addColumn("Prix");
        modeleTableau.addColumn("Quantité en Stock");
        modeleTableau.addColumn("Seuil d'approvisionnement");

        // Récupération des données depuis la base de données et ajout au modèle de tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idArticle, libel, prix, quantiteEnStock, quantiteSeuil FROM article WHERE quantiteEnStock < quantiteSeuil");

            while (resultSet.next()) {
                Object[] ligneDonnees = {
                        resultSet.getInt("idArticle"),
                        resultSet.getString("libel"),
                        resultSet.getDouble("prix"),
                        resultSet.getInt("quantiteEnStock"),
                        resultSet.getInt("quantiteSeuil"),
                };
                modeleTableau.addRow(ligneDonnees);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du JTable avec le modèle de tableau rempli
        tableArticlesSousSeuil = new JTable(modeleTableau);

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableArticlesSousSeuil);

        // Ajout du JScrollPane à la fenêtre
        this.add(scrollPane);

        // Définition des propriétés de la fenêtre
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ListeArticlesSousSeuilAppro();
    }
}
