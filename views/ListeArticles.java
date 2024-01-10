package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import components.TopNavBar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseConnection;

public class ListeArticles extends JFrame implements ActionListener {

    private JTable tableArticles;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JTextField searchField;
    private JButton searchButton;
    private JLabel totalArticlesLabel;
    private JButton seuilApprovisionnementButton;
    private JButton approvisionnerButton;
    private JButton vendreArticleButton;
    // private final Connection existingConnection;

    public ListeArticles() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connexion = databaseConnection.getConnection();

        // Paramètres de l'écran
        this.setTitle("Liste des Articles en Stock");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // navbar
        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

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
        tableArticles.setRowHeight(40);

        // Augmenter la taille de police pour le tableau
        tableArticles.setFont(new Font("Arial", Font.PLAIN, 25));

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableArticles);

        // Ajout des boutons Modifier et Supprimer
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        // Augmenter la taille de police pour les boutons
        modifierButton.setFont(new Font("Arial", Font.BOLD, 16));
        supprimerButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Ajout des champs de recherche et du bouton de recherche
        searchField = new JTextField(20);
        searchButton = new JButton("Rechercher");

        // vendre article
        vendreArticleButton = new JButton("Vendre article");

        // Augmenter la taille de police pour le champ de recherche et le bouton
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));

        // bouton pour afficher la liste des produits sous le seuil approvisionnement
        seuilApprovisionnementButton = new JButton("Articles < d'approvisionnement");

        // bouton d'approvisionnement
        approvisionnerButton = new JButton("Approvisionner");

        // Augmenter la taille de police pour les boutons
        seuilApprovisionnementButton.setFont(new Font("Arial", Font.BOLD, 16));
        approvisionnerButton.setFont(new Font("Arial", Font.BOLD, 16));
        vendreArticleButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Ajout des écouteurs d'événements pour les boutons
        modifierButton.addActionListener(this);
        supprimerButton.addActionListener(this);
        searchButton.addActionListener(this);
        seuilApprovisionnementButton.addActionListener(this);
        approvisionnerButton.addActionListener(this);
        vendreArticleButton.addActionListener(this);

        // Ajout des composants à la fenêtre
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(new JLabel("Rechercher :"));
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);
        buttonPanel.add(seuilApprovisionnementButton);
        buttonPanel.add(approvisionnerButton);
        buttonPanel.add(vendreArticleButton);

        // Create the label for displaying total articles
        totalArticlesLabel = new JLabel("Total Articles: " + getTotalArticles(connexion));

        // Augmenter la taille de police pour le label
        totalArticlesLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Add the label to the button panel
        buttonPanel.add(totalArticlesLabel);

        // Ajout du JScrollPane et du panel de boutons à la fenêtre
        this.add(buttonPanel, "South");
        this.add(scrollPane);

        // Définition des propriétés de la fenêtre
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private int getTotalArticles(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as total FROM article");

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifierButton) {
            int selectedRow = tableArticles.getSelectedRow();

            if (selectedRow >= 0) {
                int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
                this.setVisible(false);
                new ModifierArticle(idArticle);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne pour la modification.");
            }
        }

        if (e.getSource() == supprimerButton) {
            int selectedRow = tableArticles.getSelectedRow();

            if (selectedRow >= 0) {
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Êtes-vous sûr de vouloir supprimer cet article ?", "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);

                        // Supprimer l'article de la base de données
                        deleteArticle(idArticle);

                        // Mettre à jour le modèle du tableau après la suppression
                        DefaultTableModel model = (DefaultTableModel) tableArticles.getModel();
                        model.removeRow(selectedRow);

                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        Connection connection = databaseConnection.getConnection();
                        // Mettre à jour le label du total d'articles
                        totalArticlesLabel.setText("Total Articles: " + getTotalArticles(connection));

                        JOptionPane.showMessageDialog(this, "L'article a été supprimé avec succès.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'article.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne pour la suppression.");
            }
        }

        // Méthode pour search
        if (e.getSource() == searchButton) {
            searchArticles();
        }

        if (e.getSource() == seuilApprovisionnementButton) {
            new ListeArticlesSousSeuilAppro();
        }

        if (e.getSource() == approvisionnerButton) {
            int selectedRow = tableArticles.getSelectedRow();
            if (selectedRow >= 0) {
                int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
                new ApprovisionnerArticle(idArticle);
                this.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(this, "Veuillez selectionner un produit pour l'approvionnement !");
            }
        }

        if (e.getSource() == vendreArticleButton) {

            int selectedRow = tableArticles.getSelectedRow();
            if (selectedRow >= 0) {

                int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
                new VendreArticle(idArticle);
                setVisible(false);

            } else {
                JOptionPane.showMessageDialog(this, "Veuillez choisir le produit à vendre !");
            }
        }
    }

    private void deleteArticle(int idArticle) {
        try {
            String sql = "DELETE FROM article WHERE idArticle = ?";
            try (Connection connection = new DatabaseConnection().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idArticle);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchArticles() {
        try {
            String searchTerm = searchField.getText();
            DefaultTableModel model = (DefaultTableModel) tableArticles.getModel();
            model.setRowCount(0);

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            // Recherche des données depuis la base de données
            String query = "SELECT idArticle, libel, prix, quantiteEnStock, designationCat FROM article WHERE libel LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + searchTerm + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Object[] rowData = {
                            resultSet.getInt("idArticle"),
                            resultSet.getString("libel"),
                            resultSet.getDouble("prix"),
                            resultSet.getInt("quantiteEnStock"),
                            resultSet.getString("designationCat"),
                    };
                    model.addRow(rowData);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche d'articles.");
        }
    }

    public static void main(String[] args) {
        new ListeArticles();
    }
}
