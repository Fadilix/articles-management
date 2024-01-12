package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.TopNavBar;
import database.DatabaseConnection;

public class ModifierArticle extends JFrame implements ActionListener {

    private JLabel label;
    private JButton boutonValider;
    private JTextField libel, prix, quantiteStock, quantiteSeuil, designationCat;

    private final int idArticle;

    public ModifierArticle(int idArticle) {
        this.idArticle = idArticle;

        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        label = new JLabel("Modification de l'article avec ID : " + idArticle);
        this.setSize(800, 600);

        libel = new JTextField(20);
        prix = new JTextField(20);
        quantiteStock = new JTextField(20);
        quantiteSeuil = new JTextField(20);
        designationCat = new JTextField(20);
        boutonValider = new JButton("Valider");

        // Remplir les champs avec les valeurs de la base de données
        remplirChamps();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Libellé :"), gbc);
        gbc.gridx++;
        panel.add(libel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Prix :"), gbc);
        gbc.gridx++;
        panel.add(prix, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Quantité en stock :"), gbc);
        gbc.gridx++;
        panel.add(quantiteStock, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Quantité seuil :"), gbc);
        gbc.gridx++;
        panel.add(quantiteSeuil, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Désignation Catégorie :"), gbc);
        gbc.gridx++;
        panel.add(designationCat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(boutonValider, gbc);

        boutonValider.addActionListener(this);

        this.add(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void remplirChamps() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            String query = "SELECT libel, prix, quantiteEnStock, quantiteSeuil, designationCat FROM article WHERE idArticle = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idArticle);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                libel.setText(resultSet.getString("libel"));
                prix.setText(String.valueOf(resultSet.getDouble("prix")));
                quantiteStock.setText(String.valueOf(resultSet.getInt("quantiteEnStock")));
                quantiteSeuil.setText(String.valueOf(resultSet.getInt("quantiteSeuil")));
                designationCat.setText(resultSet.getString("designationCat"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonValider) {
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();
                String sql = "UPDATE article SET libel = ?, prix = ?, quantiteEnStock = ?, quantiteSeuil = ?, designationCat = ? WHERE idArticle = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, libel.getText());
                preparedStatement.setDouble(2, Double.parseDouble(prix.getText()));
                preparedStatement.setInt(3, Integer.parseInt(quantiteStock.getText()));
                preparedStatement.setInt(4, Integer.parseInt(quantiteSeuil.getText()));
                preparedStatement.setString(5, designationCat.getText());
                preparedStatement.setInt(6, idArticle);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Mise à jour réussie !");
                } else {
                    System.out.println("La mise à jour a échoué.");
                }

                new ListeArticles();
                this.dispose();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        // Exemple d'utilisation
        new ModifierArticle(1);
    }
}