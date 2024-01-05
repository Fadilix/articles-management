package components;

import javax.swing.*;
import database.DatabaseConnection;
import views.EnregistrementArticle;
import views.ListeApprovisionnements;
import views.ListeArticles;
import views.ListeArticlesSousSeuilAppro;
import views.ListeArticlesVendus;
import views.ListeCategories;

import java.sql.Connection;

public class TopNavBar extends JMenuBar {

    private JFrame parentFrame;

    public TopNavBar(JFrame parentFrame, Connection connection) {
        this.parentFrame = parentFrame;

        // Menu "Articles"
        JMenu menuArticles = new JMenu("Articles");
        JMenuItem enregistrementItem = new JMenuItem("Enregistrer un article");
        JMenuItem listeArticlesItem = new JMenuItem("Liste des articles");
        JMenuItem categorieArticlesItem = new JMenuItem("Catégories d'articles");
        JMenuItem seuilApproItem = new JMenuItem("Articles sous seuil d'approvisionnement");

        enregistrementItem.addActionListener(e -> {
            new EnregistrementArticle(connection);
            parentFrame.dispose();
        });

        listeArticlesItem.addActionListener(e -> {
            new ListeArticles(connection);
            parentFrame.dispose();
        });

        categorieArticlesItem.addActionListener(e -> {
            new ListeCategories(connection);
            parentFrame.dispose();
        });

        seuilApproItem.addActionListener(e -> {
            new ListeArticlesSousSeuilAppro(connection);
            parentFrame.dispose();
        });

        menuArticles.add(enregistrementItem);
        menuArticles.add(listeArticlesItem);
        menuArticles.add(categorieArticlesItem);
        menuArticles.add(seuilApproItem);

        // Menu "Vente"
        JMenu menuVente = new JMenu("Vente");
        JMenuItem vendreItem = new JMenuItem("Vendre un article");
        JMenuItem listeVendusItem = new JMenuItem("Liste des articles vendus");

        vendreItem.addActionListener(e -> {
            new ListeArticles(connection);
            parentFrame.dispose();
        });

        listeVendusItem.addActionListener(e -> {
            new ListeArticlesVendus(connection);
            parentFrame.dispose();
        });

        menuVente.add(vendreItem);
        menuVente.add(listeVendusItem);

        // Menu "Approvisionnement"
        JMenu menuApprovisionnement = new JMenu("Approvisionnement");
        JMenuItem listeApproItem = new JMenuItem("Liste des approvisionnements");

        listeApproItem.addActionListener(e -> {
            new ListeApprovisionnements(connection);
            parentFrame.dispose();
        });

        menuApprovisionnement.add(listeApproItem);

        // Adding menus to the bar directly
        this.add(menuArticles);
        this.add(menuVente);
        this.add(menuApprovisionnement);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection existingConnection = databaseConnection.getConnection();
            JFrame frame = new JFrame("Your Application Name");
            new TopNavBar(frame, existingConnection);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
