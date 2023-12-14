package microenterprise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete {
    private static final String url = "jdbc:mysql://localhost:3306/microenterprise";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the product to delete: ");
        String productName = scanner.nextLine();

        System.out.print("Enter the code of the product to delete: ");
        String productCode = scanner.nextLine();

        if (deleteProduct(productName, productCode)) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found or deletion failed.");
        }
    }

    private static boolean deleteProduct(String name, String code) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String deleteQuery = "DELETE FROM products WHERE name = ? AND code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, code);
                
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
