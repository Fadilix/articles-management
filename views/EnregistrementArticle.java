package views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnregistrementArticle extends JFrame {

    private JButton boutonValider;
    private JTextField libel, prix, quantiteStock, dateCreation, quantiteSeuil;
    private JTextField designationCat;

    private final Connection connection;

    public EnregistrementArticle(Connection connection) {
        this.connection = connection;

        libel = new JTextField(20);
        prix = new JTextField(20);
        quantiteStock = new JTextField(20);
        dateCreation = new JTextField(20);
        quantiteSeuil = new JTextField(20);
        designationCat = new JTextField(20);
        boutonValider = new JButton("Valider");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

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
        panel.add(new JLabel("Date de création :"), gbc);
        gbc.gridx++;
        panel.add(dateCreation, gbc);

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

        this.add(panel);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        boutonValider.addActionListener(e -> insertIntoDatabase());
    }

    private void insertIntoDatabase() {
        try {
            // Utilisation de la base de données existente
            String sql = "INSERT INTO article (libel, prix, quantiteEnStock, dateDeCrea, quantiteSeuil,  designationCat) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, libel.getText());
            preparedStatement.setDouble(2, Double.parseDouble(prix.getText()));
            preparedStatement.setInt(3, Integer.parseInt(quantiteStock.getText()));
            java.util.Date currentDate = new java.util.Date();
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(currentDate.getTime()));
            preparedStatement.setInt(5, Integer.parseInt(quantiteSeuil.getText()));
            preparedStatement.setString(6, designationCat.getText());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("L'enregistrement a été inséré avec succès !");
            } else {
                System.out.println("Erreur lors de l'insertion de l'enregistrement.");
            }

            preparedStatement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}