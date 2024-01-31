package views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import components.TopNavBar;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import database.DatabaseConnection;

public class ListeCategories extends JFrame {

    private JTable tableCategories;

    public ListeCategories() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        // Configuration de la fenêtre
        this.setTitle("Liste des Catégories d'articles");
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        try {
            // Load the custom font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "C:\\Users\\MSI Stealth\\Documents\\Coding\\Java\\vente_articles\\fonts\\YourCustomFont.ttf"));

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
        tableCategories.setFont(new Font("YourCustomFont", Font.PLAIN, 16)); // Set font size
        tableCategories.setRowHeight(30);
        tableCategories.setFont(new Font("Manrope", Font.PLAIN, 16)); // Set font size

        // Styles for the table
        tableCategories.setBackground(Color.WHITE);
        tableCategories.setSelectionBackground(new Color(0, 102, 204));
        tableCategories.setSelectionForeground(Color.WHITE);

        JTableHeader tableHeader = tableCategories.getTableHeader();
        tableHeader.setFont(new Font("YourCustomFont", Font.BOLD, 16));
        tableHeader.setBackground(new Color(0, 102, 204));
        tableHeader.setForeground(Color.WHITE);

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableCategories);

        // Ajout du JScrollPane à la fenêtre avec un titre border
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Catégories");
        titledBorder.setTitleFont(new Font("YourCustomFont", Font.BOLD, 20)); // Set border title font size
        scrollPane.setBorder(titledBorder);
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
        new ListeCategories();
    }
}
