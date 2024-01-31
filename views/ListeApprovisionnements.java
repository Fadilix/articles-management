package views;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
        this.setSize(1920, 1080);
        // Quittez l'application lorsque la fenêtre est fermée
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        try {
            // Load the custom font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(
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

        // Set table style
        tableApprovisionnements.setBackground(Color.WHITE);
        tableApprovisionnements.setSelectionBackground(new Color(0, 102, 204));
        tableApprovisionnements.setSelectionForeground(Color.WHITE);

        // Set font family and size
        Font customTableFont = new Font("Manrope", Font.PLAIN, 16);
        tableApprovisionnements.setFont(customTableFont);

        // Set header style
        JTableHeader tableHeader = tableApprovisionnements.getTableHeader();
        tableHeader.setFont(new Font("Manrope", Font.BOLD, 16));
        tableHeader.setBackground(new Color(44, 62, 80)); // Header Background color
        tableHeader.setForeground(Color.WHITE);

        // Set alternating row colors
        tableApprovisionnements.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        // Increase row height
        tableApprovisionnements.setRowHeight(40);

        // Ajout du tableau à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(tableApprovisionnements);

        // Ajout du JScrollPane à la fenêtre
        this.add(scrollPane);

        // Définition des propriétés de la fenêtre
        this.pack();
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
        SwingUtilities.invokeLater(() -> new ListeApprovisionnements());
    }
}
