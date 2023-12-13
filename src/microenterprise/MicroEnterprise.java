package microenterprise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MicroEnterprise {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/microenterprise";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public static void main(String[] args) {
        System.out.println("Welcome to Micro Enterprise Management System");

        // Create an instance of the Login class
        Login login = new Login();

        // Simulate a user logging in
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Call the login method and handle the result
        int loginStatus = login.authenticates(username, password);

        switch (loginStatus) {
            case Login.SUCCESS:
                System.out.println("Login successful!");

                try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                    Products products = new Products();

                    products.createTable(connection);
                    System.out.println("\nMicro Enterprise Management System");
                    System.out.println("[Seller] Adding Products. \nPlease enter NA if Not Applicable");

                    System.out.print("Name: ");
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

                    // Add product
                    products.addProduct(connection, name, price, stock, code, dateAdded, details);

                    // Added successfully
                    System.out.println("\nProduct added successfully.");

                    // View prduct
                    System.out.println("\nMicro Enterprise Management System");
                    System.out.println("[Seller] View Products.");

                    // View certain product
                    List<String> productList = products.viewProducts(connection, "", "");

                    // Display the product
                    System.out.println("\nProducts:");
                    for (int i = 0; i < productList.size(); i++) {
                        System.out.println((i + 1) + ". " + productList.get(i));
                    }

                    System.out.println("\nSearch the product to view:");
                    System.out.print("Name: ");
                    String searchName = scanner.nextLine();
                    System.out.print("Code: ");
                    String searchCode = scanner.nextLine();

                    productList = products.viewProducts(connection, searchName, searchCode);

                    System.out.println("\nFiltered Products:");
                    for (int i = 0; i < productList.size(); i++) {
                        System.out.println((i + 1) + ". " + productList.get(i));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case Login.INVALID_USERNAME:
                System.out.println("Incorrect Username");
                break;
            case Login.INVALID_PASSWORD:
                System.out.println("Incorrect Password");
                break;
            case Login.USER_NOT_FOUND:
                System.out.println("Username not found");
                break;
            case Login.USERNAME_AND_PASSWORD_REQUIRED:
                System.out.println("Username and Password are required");
                break;
            default:
                System.out.println("Login failed due to an unknown error.");
                break;
        }
    }
}
