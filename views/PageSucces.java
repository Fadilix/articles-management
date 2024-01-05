package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import components.TopNavBar;

public class PageSucces extends JFrame implements ActionListener {
    private JLabel successMessage;
    private JButton viewArticlesButton;
    private JButton nouvArticleButton;

    public PageSucces() {
        successMessage = new JLabel("L'article a été enregistré avec succès");
        successMessage.setHorizontalAlignment(JLabel.CENTER);

        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        Font messageFont = new Font("Arial", Font.PLAIN, 18);
        successMessage.setFont(messageFont);

        viewArticlesButton = new JButton("Voir la liste des articles");
        viewArticlesButton.addActionListener(this);

        nouvArticleButton = new JButton("Enregistrer nouvel article");
        nouvArticleButton.addActionListener(this);

        // Set up frame with FlowLayout
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Adjust as needed

        this.add(successMessage);
        this.add(viewArticlesButton);
        this.add(nouvArticleButton);

        this.setSize(500, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewArticlesButton) {
            // Redirect to the article list page

            new ListeArticles();
            this.dispose(); // Close the current success page
        }
    }

    public static void main(String[] args) {
        new PageSucces();
    }
}
