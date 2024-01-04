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
    JLabel msg;
    JButton button;

    public PageSucces() {
        button = new JButton("liste des articles");
        button.setBounds(100, 150, 100, 100);
        button.addActionListener(this);
        msg = new JLabel("L'article a été enregistré avec succès");

        // Centrer le message
        // msg.setHorizontalAlignment(JLabel.CENTER);
        // msg.setVerticalAlignment(JLabel.CENTER);
        msg.setBounds(100, 100, 100, 50);

        // Creation du font
        Font font = new Font("Arial", Font.PLAIN, 16);
        msg.setFont(font);

        // paramètres de l'écran
        this.add(msg);
        this.add(button);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {

            // Redirection vers la pages de liste des articles
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection existingConnection = databaseConnection.getConnection();

            new ListeArticles(existingConnection);
        }
    }

    public static void main(String[] args) {
        new PageSucces();
    }
}
