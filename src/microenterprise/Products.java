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

    public void addProduct(Connection connection, String name, double price, int stock, String code, String dateAdded, String details) {
        String insertProductSQL = "INSERT INTO products (name, price, stock, code, date_added, details) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertProductSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, stock);
            preparedStatement.setString(4, code);
            preparedStatement.setString(5, dateAdded);
            preparedStatement.setString(6, details);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> viewProducts(Connection connection, String searchName, String searchCode) {
        List<String> result = new ArrayList<>();

        String selectProductsSQL = "SELECT * FROM products WHERE (name LIKE ? OR ? IS NULL) AND (code = ? OR ? IS NULL)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectProductsSQL)) {
            preparedStatement.setString(1, "%" + searchName + "%");
            preparedStatement.setString(2, searchName);
            preparedStatement.setString(3, searchCode);
            preparedStatement.setString(4, searchCode);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String productName = resultSet.getString("name");
                    double productPrice = resultSet.getDouble("price");
                    int productStock = resultSet.getInt("stock");
                    String productCode = resultSet.getString("code");
                    String productDateAdded = resultSet.getString("date_added");

                    result.add(String.format("[%s, $%.2f, %d Pcs., %s, %s]", productName, productPrice, productStock, productCode, productDateAdded));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
