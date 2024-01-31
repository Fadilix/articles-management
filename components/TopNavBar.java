package components;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import views.EnregistrementArticle;
import views.ListeApprovisionnements;
import views.ListeArticles;
import views.ListeArticlesSousSeuilAppro;
import views.ListeArticlesVendus;
import views.ListeCategories;
import views.Login;
import views.Register;

public class TopNavBar extends JMenuBar {

    private JFrame parentFrame;

    public TopNavBar(JFrame parentFrame) {

        this.parentFrame = parentFrame;
        this.setPreferredSize(new Dimension(getPreferredSize().width, 50));

        // Load the custom font
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "C:\\Users\\MSI Stealth\\Documents\\Coding\\Java\\vente_articles\\fonts\\Manrope-Regular.ttf"));

            // Register the custom font with the UIManager
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            // Apply the custom font to all Swing components
            setUIFont(new FontUIResource(customFont.deriveFont(Font.PLAIN, 16)));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        this.setBackground(new Color(36, 41, 46)); // A dark bluish-gray

        JMenu menuAuth = new JMenu("Authentification");
        JMenuItem login = new JMenuItem("Login");
        JMenuItem inscription = new JMenuItem("Inscription");
        JMenuItem deconnection = new JMenuItem("Deconnection");

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                parentFrame.dispose();
            }
        });

        inscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
                parentFrame.dispose();
            }
        });


        deconnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                new Login();
            }
        });

        menuAuth.add(login);
        menuAuth.add(inscription);
        menuAuth.setForeground(Color.WHITE);
        this.add(menuAuth);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Your Application Name");
            new TopNavBar(frame);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Method to set the font for all Swing components
    private static void setUIFont(FontUIResource fontUIResource) {
        UIManager.put("Button.font", fontUIResource);
        UIManager.put("ToggleButton.font", fontUIResource);
        UIManager.put("RadioButton.font", fontUIResource);
        UIManager.put("CheckBox.font", fontUIResource);
        UIManager.put("Menu.font", fontUIResource);
        UIManager.put("MenuItem.font", fontUIResource);
        UIManager.put("MenuBar.font", fontUIResource);
        UIManager.put("PopupMenu.font", fontUIResource);
        UIManager.put("OptionPane.messageFont", fontUIResource);
        // ... (add more components as needed)
    }
}
