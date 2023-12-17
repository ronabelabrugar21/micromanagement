package microenterprise;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Add {

    private Scanner scanner;

    public Add(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addProduct(Connection connection) {
        System.out.println("\nMicro Enterprise Management System");
        System.out.println("[Seller] Adding Products. \nPlease enter NA if Not Applicable");

        System.out.print("\nName: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Code: ");
        String code = scanner.nextLine();
        System.out.print("Date: ");
        String dateAdded = scanner.nextLine();
        System.out.print("Details: ");
        String details = scanner.nextLine();

        addProduct(connection, name, price, stock, code, dateAdded, details);
    }

    public void addProduct(Connection connection, String name, double price, int stock, String code, String dateAdded, String details) {
        String insertProductSQL = "INSERT INTO products (name, price, stock, code, date_added, details) VALUES " +
                "('" + name + "', " + price + ", " + stock + ", '" + code + "', '" + dateAdded + "', '" + details + "')";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertProductSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
