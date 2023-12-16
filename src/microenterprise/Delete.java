package microenterprise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/microenterprise";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public boolean deleteProduct(String name, String code) {
        String deleteQuery = "DELETE FROM products WHERE name = ? AND code = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, code);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
