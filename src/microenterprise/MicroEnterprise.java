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

                    // Create the products table if it doesn't exist
                    products.createTable(connection);

                    while (true) {
                        // Display menu
                        System.out.println("\nMicro Enterprise Management System");
                        System.out.println("[Seller] Choose your option number.");
                        System.out.println("[1] Add Products");
                        System.out.println("[2] Delete Products");
                        System.out.println("[3] Update Products");
                        System.out.println("[4] View Products");
                        System.out.println("[5] History Transactions");
                        System.out.println("[6] Contacts");
                        System.out.println("[0] Exit");
                        System.out.print("\nEnter Here: ");

                        // Get user choice
                        int choice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        // Perform the selected action
                        switch (choice) {
                            case 1:
                                // Add Products
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

                                // Add product
                                products.addProduct(connection, name, price, stock, code, dateAdded, details);

                                // Added successfully
                                System.out.println("\nProduct added successfully.");
                                break;

                            case 2:
                                // Delete Products (Implement as needed)
                                System.out.println("Delete Products functionality not implemented yet.");
                                break;

                            case 3:
                                // Update Products (Implement as needed)
                                System.out.println("Update Products functionality not implemented yet.");
                                break;

                            case 4:
                                // View certain product
                                List<String> productList = products.viewProducts(connection, "", "");
                                
                                //still don't have view all products code here. soon.

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
                            case 5:
                                // History Transactions
                                System.out.println("History Transactions");
                                break;

                            case 6:
                                // Contacts
                                System.out.println("Contacts");
                                break;

                            case 0:
                                // Exit
                                System.out.println("Exited");
                                System.exit(0);

                            default:
                                System.out.println("Invalid choice. Please enter a valid option.");
                                break;
                        }
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
