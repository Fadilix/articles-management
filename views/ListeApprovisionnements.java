package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import components.TopNavBar;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import database.DatabaseConnection;

public class ListeApprovisionnements extends JFrame {

    private JTable tableApprovisionnements;

    public ListeApprovisionnements() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        // Configuration de la fenêtre
        this.setTitle("Liste des Approvisionnements");

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        Dimension screenSize = gd.getDefaultConfiguration().getBounds().getSize();

        this.setSize(screenSize);
        // Maximisez la fenêtre pour la mettre en plein écran
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Quittez l'application lorsque la fenêtre est fermée
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        // Création d'un modèle de tableau et définition des noms de colonnes
        DefaultTableModel modeleTableau = new DefaultTableModel();
        modeleTableau.addColumn("ID Approvisionnement");
        modeleTableau.addColumn("Article Approvisionné");
        modeleTableau.addColumn("Date Approvisionnement");
        modeleTableau.addColumn("Quantité Approvisionnée");

        // Récupération des données depuis la base de données et ajout au modèle de
        // tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Approvisionnement");

            while (resultSet.next()) {
                Object[] ligneDonnees = {
                        resultSet.getInt("idAppro"),
                        resultSet.getString("articleAppro"),
                        resultSet.getString("dateAppro"),
                        resultSet.getInt("quantiteAppro")
                };
                modeleTableau.addRow(ligneDonnees);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du JTable avec le modèle de tableau rempli
        tableApprovisionnements = new JTable(modeleTableau);

        // Augmentation de la hauteur des lignes
        tableApprovisionnements.setRowHeight(30);

        // Augmentation de la taille de la police
        Font tableFont = new Font("Arial", Font.PLAIN, 18);
        tableApprovisionnements.setFont(tableFont);

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableApprovisionnements);

        // Ajout du JScrollPane à la fenêtre
        this.add(scrollPane);

        // Définition des propriétés de la fenêtre
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ListeApprovisionnements();
    }
}
