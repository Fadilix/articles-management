package database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/gestion_articles";
        String username = "root";
        String password = "";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM article");

            // String query = "INSERT INTO article values ()";

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("idArticle"));
            }

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}