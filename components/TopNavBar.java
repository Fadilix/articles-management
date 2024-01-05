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

    public TopNavBar(Connection connection) {
        // Menu "Articles"
        JMenu menuArticles = new JMenu("Articles");
        JMenuItem enregistrementItem = new JMenuItem("Enregistrer un article");
        JMenuItem listeArticlesItem = new JMenuItem("Liste des articles");
        JMenuItem categorieArticlesItem = new JMenuItem("Catégories d'articles");
        JMenuItem seuilApproItem = new JMenuItem("Articles sous seuil d'approvisionnement");

        enregistrementItem.addActionListener(e -> {new EnregistrementArticle(connection));
        listeArticlesItem.addActionListener(e -> new ListeArticles(connection));
        categorieArticlesItem.addActionListener(e -> new ListeCategories(connection));
        seuilApproItem.addActionListener(e -> new ListeArticlesSousSeuilAppro(connection));

        menuArticles.add(enregistrementItem);
        menuArticles.add(listeArticlesItem);
        menuArticles.add(categorieArticlesItem);
        menuArticles.add(seuilApproItem);

        // Menu "Vente" 
        JMenu menuVente = new JMenu("Vente");
        JMenuItem vendreItem = new JMenuItem("Vendre un article");
        JMenuItem listeVendusItem = new JMenuItem("Liste des articles vendus");

        vendreItem.addActionListener(e -> new ListeArticles(connection));
        listeVendusItem.addActionListener(e -> new ListeArticlesVendus(connection));

        menuVente.add(vendreItem);
        menuVente.add(listeVendusItem);

        // Menu "Approvisionnement"
        JMenu menuApprovisionnement = new JMenu("Approvisionnement");
        JMenuItem listeApproItem = new JMenuItem("Liste des approvisionnements");

        listeApproItem.addActionListener(e -> new ListeApprovisionnements(connection));

        menuApprovisionnement.add(listeApproItem);

        // Adding menus to the bar directly
        this.add(menuArticles);
        this.add(menuVente);
        this.add(menuApprovisionnement);


        // the background 
        this.setBackground(new Color(47, 38, 38)); // #2F2626

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection existingConnection = databaseConnection.getConnection();
            new TopNavBar(existingConnection);
        });
    }
}
