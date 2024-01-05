package components;

import javax.swing.*;
import database.DatabaseConnection;
import views.EnregistrementArticle;
import views.ListeApprovisionnements;
import views.ListeArticles;
import views.ListeArticlesSousSeuilAppro;
import views.ListeArticlesVendus;
import views.ListeCategories;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopNavBar extends JMenuBar {

    private JFrame parentFrame;

    public TopNavBar(JFrame parentFrame) {

        this.parentFrame = parentFrame;

        // Menu "Articles"
        JMenu menuArticles = new JMenu("Articles");
        menuArticles.setForeground(Color.WHITE);
        JMenuItem enregistrementItem = new JMenuItem("Enregistrer un article");
        JMenuItem listeArticlesItem = new JMenuItem("Liste des articles");
        JMenuItem categorieArticlesItem = new JMenuItem("Catégories d'articles");
        JMenuItem seuilApproItem = new JMenuItem("Articles sous seuil d'approvisionnement");

        enregistrementItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EnregistrementArticle();
                parentFrame.dispose();
            }
        });

        listeArticlesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListeArticles();
                parentFrame.dispose();
            }
        });

        categorieArticlesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListeCategories();
                parentFrame.dispose();
            }
        });

        seuilApproItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListeArticlesSousSeuilAppro();
                parentFrame.dispose();
            }
        });

        menuArticles.add(enregistrementItem);
        menuArticles.add(listeArticlesItem);
        menuArticles.add(categorieArticlesItem);
        menuArticles.add(seuilApproItem);

        // Menu "Vente"
        JMenu menuVente = new JMenu("Vente");
        menuVente.setForeground(Color.WHITE);

        JMenuItem vendreItem = new JMenuItem("Vendre un article");
        JMenuItem listeVendusItem = new JMenuItem("Liste des articles vendus");

        vendreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListeArticles();
                parentFrame.dispose();
            }
        });

        listeVendusItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListeArticlesVendus();
                parentFrame.dispose();
            }
        });

        menuVente.add(vendreItem);
        menuVente.add(listeVendusItem);

        // Menu "Approvisionnement"
        JMenu menuApprovisionnement = new JMenu("Approvisionnement");
        menuApprovisionnement.setForeground(Color.WHITE);
        JMenuItem listeApproItem = new JMenuItem("Liste des approvisionnements");

        listeApproItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListeApprovisionnements();
                parentFrame.dispose();
            }
        });

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
            JFrame frame = new JFrame("Your Application Name");
            new TopNavBar(frame);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
