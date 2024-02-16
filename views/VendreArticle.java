package views;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import components.TopNavBar;
import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private JButton annulerButton;
    private int idArticle;

    public VendreArticle(int idArticle) {
        // Constructeur de la classe
        this.idArticle = idArticle;

        this.setTitle("Vente d'Article");
        this.setFont(new Font("Courier", Font.PLAIN, 24));
        this.setPreferredSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        quantiteField = new JTextField(10);
        clientField = new JTextField(10);
        vendreButton = new JButton("Vendre");
        annulerButton = new JButton("Annuler");

        quantiteField.setPreferredSize(new Dimension(200, 30));
        clientField.setPreferredSize(new Dimension(200, 30));

        vendreButton.addActionListener(this);
        annulerButton.addActionListener(this);

        JPanel formPanel = new JPanel(new GridBagLayout());
        Border border = BorderFactory.createLineBorder(new Color(0, 102, 204), 2);
        formPanel.setBorder(BorderFactory.createTitledBorder(border, "Vente d'Article",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Courier", Font.BOLD, 24), Color.BLACK));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Quantité :"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(quantiteField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Client :"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(clientField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(vendreButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        formPanel.add(annulerButton, gbc);

        Font labelFont = new Font("Courier", Font.PLAIN, 20);
        Font fieldFont = new Font("Courier", Font.PLAIN, 20);
        Font buttonFont = new Font("Courier", Font.PLAIN, 20);

        for (Component component : formPanel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setFont(labelFont);
            } else if (component instanceof JTextField) {
                ((JTextField) component).setFont(fieldFont);
            } else if (component instanceof JButton) {
                ((JButton) component).setFont(buttonFont);
            }               

        }

        this.add(formPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Méthode appelée lorsqu'un bouton est cliqué
        if (e.getSource() == vendreButton) {
            vendreArticle();
        } else if (e.getSource() == annulerButton) {
            annulerVente();
        }
    }

    private void annulerVente() {
        // Méthode appelée lorsqu'on clique sur le bouton "Annuler"
        // Ajouter ici la logique pour annuler la vente
        // Par exemple, vous pouvez supprimer l'enregistrement de vente de la base de
        // données
        // et mettre à jour la quantité en stock en conséquence.
        // Après l'annulation, vous voudrez peut-être fermer la fenêtre actuelle.
        this.dispose();
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

            if (quantiteVendue < 1) {
                JOptionPane.showMessageDialog(this, "La quantité doit être positive");
                return;
            }

            String client = clientField.getText();
            double prixUnitaire = getPrixUnitaire(idArticle);
            double prixTotal = quantiteVendue * prixUnitaire;

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            if (estDisponible(quantiteVendue)) {
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

                String updateQuery = "UPDATE Article SET quantiteEnStock = quantiteEnStock - ? WHERE idArticle = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, quantiteVendue);
                    preparedStatement.setInt(2, idArticle);

                    preparedStatement.executeUpdate();
                }

                generateReceipt(idArticle, getLibelle(idArticle), getDesignationCategorie(idArticle), quantiteVendue,
                        prixTotal);

                JOptionPane.showMessageDialog(this, "L'article a été vendu avec succès. Le reçu a été généré.",
                        "Vente réussie", JOptionPane.INFORMATION_MESSAGE);
                try {
                    // kenza

                    // Open the generated PDF in the default PDF viewer
                    Desktop.getDesktop().open(new File("pdf/receipt_" + idArticle + ".pdf"));
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ouverture du PDF");
                }

                new ListeArticles();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "La quantité en stock n'est pas suffisante");
            }

        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide pour la quantité de l'article",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateReceipt(int idArticle, String libelle, String designationCat, int quantiteVendue,
            double prixTotal) {
        try {
            Document document = new Document();
            String pdfPath = "pdf/receipt_" + idArticle + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

            // Add company information at the top
            Paragraph companyInfo = new Paragraph("FaRoZa\nTel: 70 66 12 26");
            companyInfo.setAlignment(Paragraph.ALIGN_LEFT);
            companyInfo.setFont(new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 15,
                    com.itextpdf.text.Font.NORMAL));
            document.add(companyInfo);

            // Add a line to separate company information from the receipt
            document.add(Chunk.NEWLINE);

            // Add the main title
            Paragraph title = new Paragraph("Reçu de vente");
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setFont(new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 28,
                    com.itextpdf.text.Font.BOLD));
            document.add(title);

            document.add(Chunk.NEWLINE);
            document.add(createReceiptLine("Client", String.valueOf(clientField.getText())));
            document.add(createInterLine());
            document.add(createReceiptLine("ID de l'article", String.valueOf(idArticle)));
            document.add(createInterLine());
            document.add(createReceiptLine("Libellé", libelle));
            document.add(createInterLine());
            document.add(createReceiptLine("Catégorie", designationCat));
            document.add(createInterLine());

            document.add(createReceiptLine("Quantité vendue", String.valueOf(quantiteVendue)));
            document.add(createInterLine());

            document.add(createReceiptLine("Prix unitaire", "$" + getPrixUnitaire(idArticle)));
            document.add(createInterLine());

            document.add(createReceiptLine("Prix total", "$" + prixTotal));
            document.add(createInterLine());

            document.add(createReceiptLine("Date de vente", getCurrentTimestamp().toString()));
            document.add(createInterLine());

            document.add(createReceiptLine("Merci pour votre achat!", ""));
            document.add(createInterLine());

            document.close();

            // Open the generated PDF in the default PDF viewer
            Desktop.getDesktop().open(new File(pdfPath));

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
        line.add(new Chunk("\n-------------------------------------------------------\n",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 14,
                        com.itextpdf.text.Font.BOLD)));
        return line;
    }

    private Paragraph createReceiptLine(String label, String value) {
        Paragraph line = new Paragraph();
        line.add(new Chunk(label + ": ", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 14,
                com.itextpdf.text.Font.BOLD)));
        line.add(new Chunk(value, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 14)));
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

    private java.sql.Timestamp getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampStr = sdf.format(new Date());
        return java.sql.Timestamp.valueOf(timestampStr);
    }

    public static void main(String[] args) {
        new VendreArticle(33);
    }
}
