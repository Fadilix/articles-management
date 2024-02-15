package views;

import components.TopNavBar;
import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModifierArticle extends JFrame implements ActionListener {

    private JLabel label;
    private JButton boutonValider;
    private JTextField libel, prix, quantiteSeuil;
    private JComboBox<String> designationCat;

    private final int idArticle;

    public ModifierArticle(int idArticle) {
        this.idArticle = idArticle;

        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        label = new JLabel("Modification de l'article avec ID : " + idArticle);
        this.setSize(800, 600);

        libel = new JTextField(20);
        prix = new JTextField(20);
        quantiteSeuil = new JTextField(20);
        designationCat = new JComboBox<>(getCategories());
        boutonValider = new JButton("Valider");

        // Remplir les champs avec les valeurs de la base de données
        remplirChamps();

        JPanel panel = createPanel();
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Ajouter une marge au panneau
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        addLabelAndTextField(panel, "Libellé :", libel, gbc);

        gbc.gridy++;
        addLabelAndTextField(panel, "Prix :", prix, gbc);

        gbc.gridy++;
        addLabelAndTextField(panel, "Quantité seuil :", quantiteSeuil, gbc);

        gbc.gridy++;
        addLabelAndComboBox(panel, "Désignation Catégorie :", designationCat, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        boutonValider.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(boutonValider, gbc);

        boutonValider.addActionListener(this);

        return panel;
    }

    private void addLabelAndTextField(JPanel panel, String labelText, JTextField textField, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(label, gbc);

        gbc.gridx++;
        textField.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(textField, gbc);

        gbc.gridx = 0;
    }

    private void addLabelAndComboBox(JPanel panel, String labelText, JComboBox<String> comboBox,
            GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(label, gbc);

        gbc.gridx++;
        comboBox.setFont(new Font("Manrope", Font.PLAIN, 16));
        panel.add(comboBox, gbc);

        gbc.gridx = 0;
    }

    private void addNewCategory() {
        String newCategory = JOptionPane.showInputDialog(this, "Entrez le nouveau nom de la catégorie :");
        if (newCategory != null && !newCategory.trim().isEmpty()) {
            try {
                Connection connection = new DatabaseConnection().getConnection();
                String insertCategorySql = "INSERT INTO categorie (designation) VALUES (?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertCategorySql)) {
                    preparedStatement.setString(1, newCategory);
                    preparedStatement.executeUpdate();
                }

                refreshCategoryDropdown();
            } catch (SQLException ex) {
                handleSQLException("Erreur lors de l'ajout de la nouvelle catégorie.", ex);
            }
        }
    }

    private void refreshCategoryDropdown() {
        String[] categories = getCategories();
        designationCat.setModel(new DefaultComboBoxModel<>(categories));
    }

    private String[] getCategories() {
        try {
            Connection connection = new DatabaseConnection().getConnection();
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
            handleSQLException("Erreur lors de la récupération des catégories.", e);
            return new String[0];
        }
    }

    private void remplirChamps() {
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String query = "SELECT libel, prix, quantiteSeuil, designationCat FROM article WHERE idArticle = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idArticle);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    libel.setText(resultSet.getString("libel"));
                    prix.setText(String.valueOf(resultSet.getDouble("prix")));
                    quantiteSeuil.setText(String.valueOf(resultSet.getInt("quantiteSeuil")));
                    designationCat.setSelectedItem(resultSet.getString("designationCat"));
                }
            }
        } catch (SQLException e) {
            handleSQLException("Erreur lors de la récupération des données de l'article.", e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonValider) {
            if (validateInput()) {
                try {
                    Connection connection = new DatabaseConnection().getConnection();
                    String sql = "UPDATE article SET libel = ?, prix = ?, quantiteSeuil = ?, designationCat = ? WHERE idArticle = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, libel.getText());
                        preparedStatement.setDouble(2, Double.parseDouble(prix.getText()));
                        preparedStatement.setInt(3, Integer.parseInt(quantiteSeuil.getText()));
                        preparedStatement.setString(4, (String) designationCat.getSelectedItem());
                        preparedStatement.setInt(5, idArticle);

                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Mise à jour réussie !");
                            JOptionPane.showMessageDialog(this, "Mise à jour réussie !");
                        } else {
                            System.out.println("La mise à jour a échoué.");
                        }

                        new ListeArticles();
                        this.dispose();
                    }
                } catch (SQLException ex) {
                    handleSQLException("Erreur lors de la mise à jour de l'article.", ex);
                }
            }
        }
    }

    private boolean validateInput() {
        try {
            double parsedPrix = Double.parseDouble(prix.getText());
            int parsedQuantiteSeuil = Integer.parseInt(quantiteSeuil.getText());

            if (parsedPrix < 1 || parsedQuantiteSeuil < 1) {
                JOptionPane.showMessageDialog(this,
                        "Le prix et la quantité en stock doivent être supérieurs ou égaux à 1.",
                        "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez saisir des valeurs valides pour le prix, la quantité en stock et la quantité seuil.",
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void handleSQLException(String message, SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, message + "\nErreur SQL: " + ex.getMessage(), "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        // Exemple
        new ModifierArticle(1);
    }
}