package microenterprise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Update {

    private static final String url = "jdbc:mysql://localhost:3306/microenterprise";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Product ID to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); 
        
        String productName = "";
        while (productName.trim().isEmpty()) {
            System.out.print("Enter updated Name of the product: ");
            productName = scanner.nextLine().trim();
            if (productName.isEmpty()) {
                System.out.println("Product name cannot be empty. Please enter a valid Product name.");
            }
        }

        String productCode = "";
        while (productCode.trim().isEmpty()) {
            System.out.print("Enter updated Code of the product: ");
            productCode = scanner.nextLine().trim();
            if (productCode.isEmpty()) {
                System.out.println("Product code cannot be empty. Please enter a valid Product code.");
            }
        }

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String sql = "UPDATE products SET name = ?, code = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, productCode);
            preparedStatement.setInt(3, productId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product update successful!");
            } else {
                System.out.println("Product update failed. No matching records found.");
            }
            
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           
            scanner.close();
        }
    }
}
