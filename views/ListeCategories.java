package views;

import javax.swing.*;
// import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import database.DatabaseConnection;

public class ListeCategories extends JFrame {

    private JTable articlesTable;

    public ListeCategories(Connection connection) {
        // Set up the frame
        this.setTitle("Liste des Categories d'article");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a table model and set column names
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Categories");

        // Fetch data from the database and add it to the table model
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT DISTINCT designationCat FROM article");

            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getString("designationCat"),
                };
                tableModel.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the JTable with the populated table model
        articlesTable = new JTable(tableModel);

        // Add the table to a JScrollPane to enable scrolling
        JScrollPane scrollPane = new JScrollPane(articlesTable);

        // Add the scroll pane to the frame
        this.add(scrollPane);

        // Set frame properties
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection existingConnection = databaseConnection.getConnection();
        new ListeCategories(existingConnection);
    }
}
