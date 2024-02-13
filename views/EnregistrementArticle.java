package views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import components.TopNavBar;
import database.DatabaseConnection;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnregistrementArticle extends JFrame {
    private JButton boutonValider;
    private JButton addCategoryButton;
    private JTextField libel, prix, quantiteSeuil;
    private JComboBox<String> categorieDropdown;

    public EnregistrementArticle() {
        libel = new JTextField(30);
        prix = new JTextField(30);
        quantiteSeuil = new JTextField(30);
        boutonValider = new JButton("Valider");
        addCategoryButton = new JButton("Ajouter une Catégorie");
        categorieDropdown = new JComboBox<>(getCategories());
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Enregistrement d'un Nouvel Article", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Manrope", Font.BOLD, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);

        Font labelFont = new Font("Manrope", Font.BOLD, 16);
        Font inputFont = new Font("Manrope", Font.PLAIN, 14);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel libelleLabel = new JLabel("Libellé:");
        libelleLabel.setFont(labelFont);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(libelleLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        libel.setFont(inputFont);
        panel.add(libel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel prixLabel = new JLabel("Prix:");
        prixLabel.setFont(labelFont);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(prixLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        prix.setFont(inputFont);
        panel.add(prix, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel quantiteSeuilLabel = new JLabel("Quantité seuil:");
        quantiteSeuilLabel.setFont(labelFont);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(quantiteSeuilLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        quantiteSeuil.setFont(inputFont);
        panel.add(quantiteSeuil, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel designationCatLabel = new JLabel("Désignation Catégorie:");
        designationCatLabel.setFont(labelFont);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(designationCatLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(categorieDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addCategoryButton.setFont(inputFont);
        addCategoryButton.setBackground(new Color(52, 152, 219)); // Background color
        addCategoryButton.setForeground(Color.WHITE); // Text color
        panel.add(addCategoryButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        boutonValider.setFont(inputFont);
        boutonValider.setBackground(new Color(46, 204, 113)); // Background color
        boutonValider.setForeground(Color.WHITE); // Text color
        panel.add(boutonValider, gbc);

        // Set panel background color
        panel.setBackground(new Color(241, 243, 244));

        this.add(panel);
        this.setTitle("Enregistrement d'Article");
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        boutonValider.addActionListener(e -> insertIntoDatabase());
        addCategoryButton.addActionListener(e -> addNewCategory());
    }

    private void addNewCategory() {
        String newCategory = JOptionPane.showInputDialog(this, "Entrez le nouveau nom de la catégorie :");
        if (newCategory != null && !newCategory.trim().isEmpty()) {
            try (Connection connection = new DatabaseConnection().getConnection()) {
                String insertCategorySql = "INSERT INTO categorie (designation) VALUES (?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertCategorySql)) {
                    preparedStatement.setString(1, newCategory);
                    preparedStatement.executeUpdate();
                }

                refreshCategoryDropdown();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la nouvelle catégorie.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshCategoryDropdown() {
        String[] categories = getCategories();
        categorieDropdown.setModel(new DefaultComboBoxModel<>(categories));
    }

    private String[] getCategories() {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String sql = "SELECT designation FROM categorie";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                List<String> categories = new ArrayList<>();
                while (resultSet.next()) {
                    categories.add(resultSet.getString("designation"));
                }

                return categories.toArray(new String[0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private void insertIntoDatabase() {
        try {
            // Validate input data
            if (libel.getText().isEmpty() || prix.getText().isEmpty() || quantiteSeuil.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate numeric input
            try {
                Double.parseDouble(prix.getText());
                Integer.parseInt(quantiteSeuil.getText());
                double prixValue = Double.parseDouble(prix.getText());
                int quantiteSeuilValue = Integer.parseInt(quantiteSeuil.getText());
                if (prixValue < 1 || quantiteSeuilValue < 1) {
                    JOptionPane.showMessageDialog(this, "Le prix et quantite doivent être supérieur ou égaux à 1");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Les champs 'Prix' et 'Quantité seuil' doivent être des nombres valides.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert data into the database
            try (Connection connection = new DatabaseConnection().getConnection()) {
                String sql = "INSERT INTO article (libel, prix, quantiteEnStock, dateDeCrea, quantiteSeuil, designationCat) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, libel.getText());
                    preparedStatement.setDouble(2, Double.parseDouble(prix.getText()));
                    preparedStatement.setInt(3, 0);
                    java.util.Date currentDate = new java.util.Date();
                    preparedStatement.setTimestamp(4, new java.sql.Timestamp(currentDate.getTime()));
                    preparedStatement.setInt(5, Integer.parseInt(quantiteSeuil.getText()));
                    preparedStatement.setString(6, categorieDropdown.getSelectedItem().toString());


                    int rowsInserted = preparedStatement.executeUpdate();
                    new PageSucces();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(this, "L'enregistrement a été inséré avec succès !", "Succès",
                                JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion de l'enregistrement.", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnregistrementArticle());
    }
}