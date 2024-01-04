package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/gestion_articles";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Echec de connexion à la base de données", e);
        }
    }

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM article");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("idArticle"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
