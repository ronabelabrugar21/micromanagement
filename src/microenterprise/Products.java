package microenterprise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Products {

    public void createTable(Connection connection) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS products (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255) NOT NULL," +
                "price DECIMAL(10, 2) NOT NULL," +
                "stock INT NOT NULL," +
                "code VARCHAR(20) NOT NULL UNIQUE," +
                "date_added VARCHAR(10) NOT NULL," +
                "details VARCHAR(500) NULL)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

