
package microenterprise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {

    private static final String url = "jdbc:mysql://localhost:3306/microenterprise";
    private static final String user = "root";
    private static final String password = "";

    public static boolean updateProduct(int productId, String productName, String productCode) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE products SET name = ?, code = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, productCode);
            preparedStatement.setInt(3, productId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
