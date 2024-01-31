package views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import components.TopNavBar;
import database.DatabaseConnection;

public class ListeArticlesVendus extends JFrame {

    private JTable tableArticlesVendus;

    public ListeArticlesVendus() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        // Configuration de la fenêtre
        this.setTitle("Liste des Articles Vendus");
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        try {
            // Load the custom font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "C:\\Users\\MSI Stealth\\Documents\\Coding\\Java\\vente_articles\\fonts\\Manrope-Regular.ttf"));

            // Register the custom font with the UIManager
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            // Apply the custom font to the whole frame
            setUIFont(new FontUIResource(customFont.deriveFont(Font.PLAIN, 16)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création d'un modèle de tableau et définition des noms de colonnes
        DefaultTableModel modeleTableau = new DefaultTableModel();
        modeleTableau.addColumn("ID Article");
        modeleTableau.addColumn("Libellé");
        modeleTableau.addColumn("Prix");
        modeleTableau.addColumn("Date de Vente");
        modeleTableau.addColumn("Catégorie");
        modeleTableau.addColumn("Quantité Vendue");
        modeleTableau.addColumn("Prix Total");
        modeleTableau.addColumn("Client");

        // Récupération des données depuis la base de données et ajout au modèle de tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM articlevendu");

            while (resultSet.next()) {
                Object[] ligneDonnees = {
                        resultSet.getInt("idArticle"),
                        resultSet.getString("libel"),
                        resultSet.getDouble("prix"),
                        resultSet.getString("dateDeVente"),
                        resultSet.getString("designationCat"),
                        resultSet.getInt("quantiteVendu"),
                        resultSet.getDouble("prixTotal"),
                        resultSet.getString("client")
                };
                modeleTableau.addRow(ligneDonnees);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du JTable avec le modèle de tableau rempli
        tableArticlesVendus = new JTable(modeleTableau);

        // Styles for the table
        tableArticlesVendus.setBackground(Color.WHITE);
        tableArticlesVendus.setSelectionBackground(new Color(0, 102, 204));
        tableArticlesVendus.setSelectionForeground(Color.WHITE);

        // Définition de la taille de la police pour les éléments du tableau
        Font customTableFont = new Font("Manrope", Font.PLAIN, 16);
        tableArticlesVendus.setFont(customTableFont);

        // Set alternate row colors
        tableArticlesVendus.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    rendererComponent.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                }
                return rendererComponent;
            }
        });

        JTableHeader tableHeader = tableArticlesVendus.getTableHeader();
        tableHeader.setFont(new Font("Manrope", Font.BOLD, 16));
        tableHeader.setBackground(new Color(0, 102, 204));
        tableHeader.setForeground(Color.WHITE);

        // Définition de la hauteur des lignes pour augmenter la taille des éléments
        tableArticlesVendus.setRowHeight(30);

        // Ajout d'un titre bordé à la fenêtre
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Liste des Articles Vendus");
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableArticlesVendus);

        // Ajout du JScrollPane à la fenêtre
        this.add(scrollPane);

        // Définition des propriétés de la fenêtre
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListeArticlesVendus());
    }
}
