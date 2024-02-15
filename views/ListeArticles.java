package views;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import components.TopNavBar;
import database.DatabaseConnection;

public class ListeArticles extends JFrame implements ActionListener {

    private JTable tableArticles;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JTextField searchField;
    private JButton searchButton;
    private JButton seuilApprovisionnementButton;
    private JButton approvisionnerButton;
    private JButton vendreArticleButton;

    private JPanel contentPanel;

    public ListeArticles() {
        // Database connection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connexion = databaseConnection.getConnection();

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
            System.out.println(e);
        }

        // Frame settings
        this.setTitle("Liste des Articles en Stock");
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Navbar
        TopNavBar topNavBar = new TopNavBar(this);
        this.setJMenuBar(topNavBar);

        // Content panel
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(63, 81, 181)); // Background color
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BorderLayout());

        // Table model and column names
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Libellé");
        tableModel.addColumn("Prix");
        tableModel.addColumn("Quantité en stock");
        tableModel.addColumn("Quantité seuil");
        tableModel.addColumn("Désignation catégorie");

        // Retrieve data from the database
        try {
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement
                    .executeQuery(
                            "SELECT idArticle, libel, prix, quantiteEnStock, quantiteSeuil, designationCat FROM article");

            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getInt("idArticle"),
                        resultSet.getString("libel"),
                        resultSet.getDouble("prix"),
                        resultSet.getInt("quantiteEnStock"),
                        resultSet.getInt("quantiteSeuil"),
                        resultSet.getString("designationCat"),
                };
                tableModel.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table with styling
        tableArticles = new JTable(tableModel);
        tableArticles.setRowHeight(40);
        tableArticles.setFont(new FontUIResource("Manrope", Font.PLAIN, 16)); // Set custom font

        JTableHeader tableHeader = tableArticles.getTableHeader();
        tableHeader.setBackground(new Color(44, 62, 80)); // Header Background color
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new FontUIResource("Manrope", Font.BOLD, 16)); // Set custom font

        tableArticles.setGridColor(new Color(189, 195, 199)); // Grid color

        tableArticles.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final Color color1 = new Color(224, 224, 224);
            final Color color2 = new Color(255, 255, 255);

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    int quantiteEnStock = (int) table.getModel().getValueAt(row, 3);
                    int quantiteSeuil = (int) table.getModel().getValueAt(row, 4);

                    // Check if the stock quantity is below the threshold
                    if (quantiteEnStock < quantiteSeuil) {
                        comp.setBackground(new Color(255, 204, 204)); // Set background color to light red
                    } else {
                        comp.setBackground(row % 2 == 0 ? color1 : color2);
                    }
                }
                return comp;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableArticles);

        // Buttons
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        modifierButton.setFont(new FontUIResource("Manrope", Font.BOLD, 16)); // Set custom font
        supprimerButton.setFont(new FontUIResource("Manrope", Font.BOLD, 16)); // Set custom font

        searchField = new JTextField("Entrer l'élément à rechercher...", 20);
        searchButton = new JButton("Rechercher");

        vendreArticleButton = new JButton("Vendre article");

        searchField.setFont(new FontUIResource("Manrope", Font.PLAIN, 16)); // Set custom font
        searchField.setBorder(BorderFactory.createCompoundBorder(
                searchField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))); // Add padding
        searchButton.setFont(new FontUIResource("Manrope", Font.BOLD, 16)); // Set custom font

        seuilApprovisionnementButton = new JButton("Articles sous seuil d'approvisionnement");
        approvisionnerButton = new JButton("Approvisionner");

        seuilApprovisionnementButton.setFont(new FontUIResource("Manrope", Font.BOLD, 16)); // Set custom font
        approvisionnerButton.setFont(new FontUIResource("Manrope", Font.BOLD, 16)); // Set custom font
        vendreArticleButton.setFont(new FontUIResource("Manrope", Font.BOLD, 16)); // Set custom font

        // Button listeners
        modifierButton.addActionListener(this);
        supprimerButton.addActionListener(this);
        searchButton.addActionListener(this);
        seuilApprovisionnementButton.addActionListener(this);
        approvisionnerButton.addActionListener(this);
        vendreArticleButton.addActionListener(this);

        // Total articles label
        JLabel totalArticlesLabel = new JLabel("Total Articles: " + getTotalArticles(connexion));
        totalArticlesLabel.setFont(new FontUIResource("Manrope", Font.BOLD, 16)); // Set custom font

        // Sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        sidebarPanel.setBackground(new Color(33, 33, 33));

        totalArticlesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalArticlesLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        totalArticlesLabel.setForeground(Color.WHITE);
        sidebarPanel.add(totalArticlesLabel);

        searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchField.setMaximumSize(new Dimension(180, 30));
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.GRAY);
        searchField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchFieldMouseClicked(evt);
            }
        });
        sidebarPanel.add(searchField);

        searchButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchButton.setMaximumSize(new Dimension(180, 40));
        searchButton.setBackground(new Color(33, 33, 33));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebarPanel.add(searchButton);

        JButton infoButton = new JButton("Aide!");
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Message d'information pour le tutoriel
                String tutorialMessage = "Bienvenue dans le tutoriel!\n\n"
                        + "1. Sur la gauche, le sidebar offre diverses fonctionnalités pour faciliter la gestion des articles.\n\n"
                        + "2. Avant d'effectuer des actions telles que la suppression, la modification, l'approvisionnement ou la \nvente, assurez-vous de sélectionner l'élément souhaité en cliquant sur une ligne du tableau. Ensuite, appuyez sur le bouton correspondant.\n\n"
                        + "3. Utilisez le champ de saisie pour rechercher un article. Saisissez le nom de l'article désiré et appuyez\nsur le bouton 'Rechercher'.\n\n"
                        + "4. Lorsqu'une ligne est signalée en rouge, cela indique qu'il est nécessaire d'approvisionner l'article en\nquestion. Cela peut être effectué en sélectionnant l'article et en utilisant l'option d'approvisionnement dans le sidebar.\n\n"
                        + "5. Explorez également les fonctionnalités supplémentaires offertes par la barre de navigation (navbar).";

                // Afficher une boîte de dialogue avec le message d'information
                JOptionPane.showMessageDialog(ListeArticles.this, tutorialMessage, "Tutorial",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        addSidebarButton(sidebarPanel, "Rechercher", searchButton, SwingConstants.LEFT);
        addSidebarButton(sidebarPanel, "Modifier", modifierButton, SwingConstants.LEFT);
        addSidebarButton(sidebarPanel, "Supprimer", supprimerButton, SwingConstants.LEFT);
        addSidebarButton(sidebarPanel, "Articles sous seuil", seuilApprovisionnementButton, SwingConstants.LEFT);
        addSidebarButton(sidebarPanel, "Approvisionner", approvisionnerButton, SwingConstants.LEFT);
        addSidebarButton(sidebarPanel, "Vendre article", vendreArticleButton, SwingConstants.LEFT);
        addSidebarButton(sidebarPanel, "Aide !", infoButton, SwingConstants.LEFT);

        contentPanel.add(sidebarPanel, BorderLayout.WEST);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(contentPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addSidebarButton(JPanel panel, String buttonText, JButton button, int alignment) {
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setBackground(new Color(33, 33, 33));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setHorizontalAlignment(alignment); // Set text alignment
        panel.add(button);
    }

    private int getTotalArticles(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as total FROM article");

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handling button actions
        if (e.getSource() == modifierButton) {
            int selectedRow = tableArticles.getSelectedRow();

            if (selectedRow >= 0) {
                int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
                this.setVisible(false);
                new ModifierArticle(idArticle);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne pour la modification.");
            }
        }

        if (e.getSource() == supprimerButton) {
            int selectedRow = tableArticles.getSelectedRow();

            if (selectedRow >= 0) {
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Êtes-vous sûr de vouloir supprimer cet article ?", "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);

                        // Delete the article from the database
                        deleteArticle(idArticle);

                        // Update the table model after deletion
                        DefaultTableModel model = (DefaultTableModel) tableArticles.getModel();
                        model.removeRow(selectedRow);

                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        Connection connection = databaseConnection.getConnection();
                        // Update the total articles label
                        JLabel totalArticlesLabel = (JLabel) ((Box.Filler) contentPanel.getComponent(0))
                                .getComponent(0);
                        totalArticlesLabel.setText("Total Articles: " + getTotalArticles(connection));

                        JOptionPane.showMessageDialog(this, "L'article a été supprimé avec succès.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "L'article a été supprimé avec succès.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne pour la suppression.");
            }
        }

        if (e.getSource() == searchButton) {
            searchArticles();
        }

        if (e.getSource() == seuilApprovisionnementButton) {
            new ListeArticlesSousSeuilAppro();
            this.dispose();
        }

        if (e.getSource() == approvisionnerButton) {
            int selectedRow = tableArticles.getSelectedRow();
            if (selectedRow >= 0) {
                int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
                new ApprovisionnerArticle(idArticle);
                this.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit pour l'approvisionnement !");
            }
        }

        if (e.getSource() == vendreArticleButton) {
            int selectedRow = tableArticles.getSelectedRow();
            if (selectedRow >= 0) {
                int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
                new VendreArticle(idArticle);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez choisir le produit à vendre !");
            }
        }
    }

    private void deleteArticle(int idArticle) {
        try {
            String sql = "DELETE FROM article WHERE idArticle = ?";
            try (Connection connection = new DatabaseConnection().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idArticle);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchArticles() {
        try {
            String searchTerm = searchField.getText();
            DefaultTableModel model = (DefaultTableModel) tableArticles.getModel();
            model.setRowCount(0);

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            String query = "SELECT idArticle, libel, prix, quantiteEnStock, quantiteSeuil, designationCat FROM article WHERE libel LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + searchTerm + "%");
                ResultSet resultSet = preparedStatement.executeQuery();


                if(!resultSet.isBeforeFirst()){
                    JOptionPane.showMessageDialog(null, "Cet article n'existe pas");
                }
                while (resultSet.next()) {
                    Object[] rowData = {
                            resultSet.getInt("idArticle"),
                            resultSet.getString("libel"),
                            resultSet.getDouble("prix"),
                            resultSet.getInt("quantiteEnStock"),
                            resultSet.getInt("quantiteSeuil"),
                            resultSet.getString("designationCat"),
                    };

                  
                    model.addRow(rowData);
                }
            }
        } catch (

        SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche d'articles.");
        }
    }

    private void searchFieldMouseClicked(java.awt.event.MouseEvent evt) {
        if (searchField.getText().equals("Entrer l'élément à rechercher...")) {
            searchField.setText("");
            searchField.setForeground(Color.BLACK);
        }
    }

    public static void main(String[] args) {
        new ListeArticles();
    }

    // Method to set the font for all Swing components
    private static void setUIFont(FontUIResource f) {
        UIManager.put("Button.font", f);
        UIManager.put("ToggleButton.font", f);
        UIManager.put("RadioButton.font", f);
        UIManager.put("CheckBox.font", f);
        UIManager.put("ColorChooser.font", f);
        UIManager.put("ToggleButton.font", f);
        UIManager.put("ComboBox.font", f);
        UIManager.put("ComboBoxItem.font", f);
        UIManager.put("InternalFrame.titleFont", f);
        UIManager.put("Label.font", f);
        UIManager.put("List.font", f);
        UIManager.put("MenuBar.font", f);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        UIManager.put("RadioButtonMenuItem.font", f);
        UIManager.put("CheckBoxMenuItem.font", f);
        UIManager.put("PopupMenu.font", f);
        UIManager.put("OptionPane.font", f);
        UIManager.put("Panel.font", f);
        UIManager.put("ProgressBar.font", f);
        UIManager.put("ScrollPane.font", f);
        UIManager.put("Viewport.font", f);
        UIManager.put("TabbedPane.font", f);
        UIManager.put("Table.font", f);
        UIManager.put("TableHeader.font", f);
        UIManager.put("TextField.font", f);
        UIManager.put("PasswordField.font", f);
        UIManager.put("TextArea.font", f);
        UIManager.put("TextPane.font", f);
        UIManager.put("EditorPane.font", f);
        UIManager.put("TitledBorder.font", f);
        UIManager.put("ToolBar.font", f);
        UIManager.put("ToolTip.font", f);
        UIManager.put("Tree.font", f);
        UIManager.put("FormattedTextField.font", f);
        UIManager.put("Spinner.font", f);
        UIManager.put("TabbedPane.smallFont", f);
        UIManager.put("TabbedPane.boldFont", f);
    }
}
