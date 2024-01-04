package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import database.DatabaseConnection;

public class ListeArticles extends JFrame implements ActionListener {

    private JTable tableArticles;
    private JButton modifierButton;
    private JButton supprimerButton;

    public ListeArticles(Connection connexion) {
        // Paramètres de l'écran
        this.setTitle("Liste des Articles en Stock");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création d'un modèle de tableau et définir les noms de colonnes
        DefaultTableModel modeleTableau = new DefaultTableModel();
        modeleTableau.addColumn("ID");
        modeleTableau.addColumn("Libellé");
        modeleTableau.addColumn("Prix");
        modeleTableau.addColumn("Quantité en Stock");
        modeleTableau.addColumn("Désignation catégorie");

        // Récupération des données depuis la base de données et ajout au modèle de
        // tableau
        try {
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT idArticle, libel, prix, quantiteEnStock, designationCat FROM article");

            while (resultSet.next()) {
                Object[] ligneDonnees = {
                        resultSet.getInt("idArticle"),
                        resultSet.getString("libel"),
                        resultSet.getDouble("prix"),
                        resultSet.getInt("quantiteEnStock"),
                        resultSet.getString("designationCat"),
                };
                modeleTableau.addRow(ligneDonnees);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du JTable avec le modèle de tableau rempli
        tableArticles = new JTable(modeleTableau);

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableArticles);

        // Ajout des boutons Modifier et Supprimer
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        // Ajout des écouteurs d'événements pour les boutons
        modifierButton.addActionListener(this);

        supprimerButton.addActionListener(this);

        // Ajout des composants à la fenêtre
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);

        this.add(buttonPanel, "South");
        this.add(scrollPane);

        // Définition des propriétés de la fenêtre
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection existingConnection = databaseConnection.getConnection();
        new ListeArticles(existingConnection);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifierButton) {
            int selectedRow = tableArticles.getSelectedRow();

            int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
            this.setVisible(false);
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection existingConnection = databaseConnection.getConnection();
            new ModifierArticle(idArticle, existingConnection);
        }

        if (e.getSource() == supprimerButton) {

        }

    }
}
