package views;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import components.TopNavBar;
import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VendreArticle extends JFrame implements ActionListener {

    private JTextField quantiteField;
    private JTextField clientField;
    private JButton vendreButton;
    private int idArticle;

    public VendreArticle(int idArticle) {
        this.idArticle = idArticle;

        this.setTitle("Vendre Article");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        // Création des composants
        quantiteField = new JTextField(10);
        clientField = new JTextField(10);
        vendreButton = new JButton("Vendre");

        // Ajout des écouteurs d'événements
        vendreButton.addActionListener(this);

        // Création du formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2));
        formPanel.add(new JLabel("Quantité :"));
        formPanel.add(quantiteField);
        formPanel.add(new JLabel("Client :"));
        formPanel.add(clientField);
        formPanel.add(new JLabel("")); // espace vide
        formPanel.add(vendreButton);

        // Ajout du formulaire à la fenêtre
        this.add(formPanel);

        // Définition des propriétés de la fenêtre
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vendreButton) {
            vendreArticle();
        }
    }

    public boolean estDisponible(int quantiteAvendre) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        try {
            String quantiteEnStockSQL = "SELECT quantiteEnStock FROM Article WHERE idArticle = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(quantiteEnStockSQL)) {
                preparedStatement.setInt(1, idArticle);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    int quantiteEnStock = rs.getInt("quantiteEnStock");
                    return quantiteEnStock >= quantiteAvendre;
                } else {
                    throw new SQLException("Article not found for id: " + idArticle);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void vendreArticle() {
        try {
            int quantiteVendue = Integer.parseInt(quantiteField.getText());
            String client = clientField.getText();
            double prixUnitaire = getPrixUnitaire(idArticle);
            double prixTotal = quantiteVendue * prixUnitaire;

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            if (estDisponible(quantiteVendue)) {

                // Insertion des données dans la table ArticleVendu
                String insertQuery = "INSERT INTO ArticleVendu (libel, prix, dateDeVente, designationCat, quantiteVendu, prixTotal, client) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, getLibelle(idArticle));
                    preparedStatement.setDouble(2, prixUnitaire);
                    preparedStatement.setDate(3, getCurrentDate());
                    preparedStatement.setString(4, getDesignationCategorie(idArticle));
                    preparedStatement.setInt(5, quantiteVendue);
                    preparedStatement.setDouble(6, prixTotal);
                    preparedStatement.setString(7, client);

                    preparedStatement.executeUpdate();
                }

                // Mise à jour de la quantité en stock dans la table Article
                String updateQuery = "UPDATE Article SET quantiteEnStock = quantiteEnStock - ? WHERE idArticle = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, quantiteVendue);
                    preparedStatement.setInt(2, idArticle);

                    preparedStatement.executeUpdate();
                }

                // Génération du reçu PDF
                generateReceipt(idArticle, getLibelle(idArticle), getDesignationCategorie(idArticle), quantiteVendue,
                        prixTotal);

                // Affichage d'un message de succès
                JOptionPane.showMessageDialog(this, "L'article a été vendu avec succès. Le reçu a été généré.",
                        "Vente réussie", JOptionPane.INFORMATION_MESSAGE);

                // Fermeture de la fenêtre après la vente
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "La quantite en stock n'est pas suffisante");
            }

        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la vente de l'article.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateReceipt(int idArticle, String libelle, String designationCat, int quantiteVendue,
            double prixTotal) {
        try {
            // Création d'un nouveau Document
            Document document = new Document();
            // Spécification du chemin où vous souhaitez enregistrer le reçu PDF
            String pdfPath = "pdf/receipt_" + (Math.round(Math.random()) * 1000) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));

            // Ouverture du document
            document.open();

            // Ajout du contenu au document (vous pouvez personnaliser cela en fonction de
            // vos besoins)
            Paragraph title = new Paragraph("Reçu pour la vente d'article");
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setFont(new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 40,
                    com.itextpdf.text.Font.BOLD));
            document.add(title);

            document.add(Chunk.NEWLINE); // Espacement
            document.add(createReceiptLine("Client", String.valueOf("")));
            document.add(createReceiptLine("ID de l'article", String.valueOf(idArticle)));
            document.add(createInterLine());
            document.add(createReceiptLine("Libellé", libelle));
            document.add(createInterLine());
            document.add(createReceiptLine("Catégorie", designationCat));
            document.add(createInterLine());

            document.add(createReceiptLine("Quantité vendue", String.valueOf(quantiteVendue)));
            document.add(createInterLine());

            document.add(createReceiptLine("Prix total", "$" + prixTotal));

            // Fermeture du document
            document.close();

            JOptionPane.showMessageDialog(this, "Le reçu a été généré avec succès.", "Reçu généré",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la génération du reçu.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Paragraph createInterLine() {
        Paragraph line = new Paragraph();
        line.add(new Chunk("----------------------------------------------------------",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12,
                        com.itextpdf.text.Font.BOLD)));
        return line;
    }

    private Paragraph createReceiptLine(String label, String value) {
        Paragraph line = new Paragraph();
        line.add(new Chunk(label + ": ", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12,
                com.itextpdf.text.Font.BOLD)));
        line.add(new Chunk(value, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12)));
        return line;
    }

    private String getLibelle(int idArticle) throws SQLException {
        String libelle = null;
        String sql = "SELECT libel FROM Article WHERE idArticle = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idArticle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                libelle = resultSet.getString("libel");
            }
        }

        return libelle;
    }

    private double getPrixUnitaire(int idArticle) throws SQLException {
        String sql = "SELECT prix FROM Article WHERE idArticle = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idArticle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("prix");
            } else {
                throw new SQLException("Article non trouvé : " + idArticle);
            }
        }
    }

    private String getDesignationCategorie(int idArticle) throws SQLException {
        String sql = "SELECT designationCat FROM Article WHERE idArticle = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idArticle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("designationCat");
            } else {
                throw new SQLException("Article not found for id: " + idArticle);
            }
        }
    }

    private java.sql.Date getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        return java.sql.Date.valueOf(dateStr);
    }

    public static void main(String[] args) {
        new VendreArticle(2);
    }
}