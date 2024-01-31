package views;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.FontFormatException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import components.TopNavBar;
import database.DatabaseConnection;

public class ListeArticlesSousSeuilAppro extends JFrame {

    private JTable tableArticlesSousSeuil;

    public ListeArticlesSousSeuilAppro() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        this.setTitle("Liste des Articles sous le seuil d'approvisionnement");
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

            // Apply the custom font to all Swing components
            setUIFont(new FontUIResource(customFont.deriveFont(Font.PLAIN, 16)));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Font file not found.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | FontFormatException e) {
            JOptionPane.showMessageDialog(this, "Error loading font.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Création d'un modèle de tableau et définir les noms de colonnes
        DefaultTableModel modeleTableau = new DefaultTableModel();
        modeleTableau.addColumn("ID");
        modeleTableau.addColumn("Libellé");
        modeleTableau.addColumn("Prix");
        modeleTableau.addColumn("Quantité en Stock");
        modeleTableau.addColumn("Seuil d'approvisionnement");

        // Récupération des données depuis la base de données et ajout au modèle de
        // tableau
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT idArticle, libel, prix, quantiteEnStock, quantiteSeuil FROM article WHERE quantiteEnStock < quantiteSeuil");

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

        // Styling the table
        tableArticlesSousSeuil.setRowHeight(40);
        tableArticlesSousSeuil.setFont(new Font("Manrope", Font.PLAIN, 16));

        JTableHeader tableHeader = tableArticlesSousSeuil.getTableHeader();
        tableHeader.setBackground(new Color(44, 62, 80)); // Header Background color
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Manrope", Font.BOLD, 16));

        tableArticlesSousSeuil.setGridColor(new Color(189, 195, 199)); // Grid color

        tableArticlesSousSeuil.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final Color color1 = new Color(224, 224, 224);
            final Color color2 = new Color(255, 255, 255);

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    comp.setBackground(row % 2 == 0 ? color1 : color2);
                }
                return comp;
            }
        });

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

    // Method to set the font for all Swing components
    private static void setUIFont(FontUIResource fontUIResource) {
        UIManager.put("Button.font", fontUIResource);
        UIManager.put("ToggleButton.font", fontUIResource);
        UIManager.put("RadioButton.font", fontUIResource);
        UIManager.put("CheckBox.font", fontUIResource);
        UIManager.put("ColorChooser.font", fontUIResource);
        UIManager.put("ComboBox.font", fontUIResource);
        UIManager.put("Label.font", fontUIResource);
        UIManager.put("List.font", fontUIResource);
        UIManager.put("MenuBar.font", fontUIResource);
        UIManager.put("MenuItem.font", fontUIResource);
        UIManager.put("RadioButtonMenuItem.font", fontUIResource);
        UIManager.put("CheckBoxMenuItem.font", fontUIResource);
        UIManager.put("Menu.font", fontUIResource);
        UIManager.put("PopupMenu.font", fontUIResource);
        UIManager.put("OptionPane.font", fontUIResource);
        UIManager.put("Panel.font", fontUIResource);
        UIManager.put("ProgressBar.font", fontUIResource);
        UIManager.put("ScrollPane.font", fontUIResource);
        UIManager.put("Viewport.font", fontUIResource);
        UIManager.put("TabbedPane.font", fontUIResource);
        UIManager.put("Table.font", fontUIResource);
        UIManager.put("TableHeader.font", fontUIResource);
        UIManager.put("TextField.font", fontUIResource);
        UIManager.put("PasswordField.font", fontUIResource);
        UIManager.put("TextArea.font", fontUIResource);
        UIManager.put("TextPane.font", fontUIResource);
        UIManager.put("EditorPane.font", fontUIResource);
        UIManager.put("TitledBorder.font", fontUIResource);
        UIManager.put("ToolBar.font", fontUIResource);
        UIManager.put("ToolTip.font", fontUIResource);
        UIManager.put("Tree.font", fontUIResource);
    }
}
