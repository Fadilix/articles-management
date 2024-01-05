package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import database.DatabaseConnection;

public class PageSucces extends JFrame implements ActionListener {
    private JLabel successMessage;
    private JButton viewArticlesButton;

    public PageSucces() {
        successMessage = new JLabel("L'article a été enregistré avec succès");
        successMessage.setBounds(100, 50, 300, 50);
        successMessage.setHorizontalAlignment(JLabel.CENTER);

        Font messageFont = new Font("Arial", Font.PLAIN, 18);
        successMessage.setFont(messageFont);

        viewArticlesButton = new JButton("Voir la liste des articles");
        viewArticlesButton.setBounds(150, 120, 200, 40);
        viewArticlesButton.addActionListener(this);

        // Set up frame
        this.add(successMessage);
        this.add(viewArticlesButton);
        this.setLayout(null);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewArticlesButton) {
            // Redirect to the article list page
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection existingConnection = databaseConnection.getConnection();
            new ListeArticles(existingConnection);
            this.dispose(); // Close the current success page
        }
    }

    public static void main(String[] args) {
        new PageSucces();
    }
}
