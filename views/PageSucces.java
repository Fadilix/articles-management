package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import components.TopNavBar;

public class PageSucces extends JFrame implements ActionListener {
    private JLabel successMessage;
    private JButton viewArticlesButton;
    private JButton nouvArticleButton;

    public PageSucces() {
        // Create a panel with BoxLayout to center components vertically
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Centered success message
        successMessage = new JLabel("L'article a été enregistré avec succès");
        successMessage.setHorizontalAlignment(JLabel.CENTER);
        successMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Top navigation bar
        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);
        this.setTitle("Succès");

        Font messageFont = new Font("Arial", Font.PLAIN, 18);
        successMessage.setFont(messageFont);

        // Centered buttons
        viewArticlesButton = new JButton("Voir la liste des articles");
        viewArticlesButton.addActionListener(this);
        viewArticlesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        nouvArticleButton = new JButton("Enregistrer nouvel article");
        nouvArticleButton.addActionListener(this);
        nouvArticleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel
        mainPanel.add(Box.createVerticalGlue()); // Top glue
        mainPanel.add(successMessage);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        mainPanel.add(viewArticlesButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        mainPanel.add(nouvArticleButton);
        mainPanel.add(Box.createVerticalGlue()); // Bottom glue

        // Set up frame with BorderLayout and add the main panel
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);

        this.setSize(1920, 1080); // Adjust as needed
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        if(e.getSource() == nouvArticleButton) {
            new EnregistrementArticle();
            this.dispose(); // Close the current page
        }
    }

    public static void main(String[] args) {
        new PageSucces();
    }
}
