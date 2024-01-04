import java.sql.Connection;

import database.DatabaseConnection;
import views.EnregistrementArticle;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection existingConnection = databaseConnection.getConnection();

        new EnregistrementArticle(existingConnection);
    }
}