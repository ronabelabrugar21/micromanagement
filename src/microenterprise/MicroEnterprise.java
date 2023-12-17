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
                    
                    Add addProducts = new Add(scanner);
                    View viewProducts = new View(scanner);

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
                        scanner.nextLine(); 

                        switch (choice) {
                            case 1:
                                //Add Products
                                addProducts.addProduct(connection);
                                System.out.println("\nProduct added successfully.");
                                break;

                            case 2:
                                // Delete Products
                                System.out.println("Delete Products");
                                Delete delete = new Delete();

                                // Get details of the product to delete
                                System.out.print("Enter the name of the product to delete: ");
                                String productNameToDelete = scanner.nextLine();
                                System.out.print("Enter the code of the product to delete: ");
                                String productCodeToDelete = scanner.nextLine();

                                // Perform deletion
                                boolean isDeleted = delete.deleteProduct(productNameToDelete, productCodeToDelete);

                                if (isDeleted) {
                                    System.out.println("Product was Deleted successfully.");
                                } else {
                                    System.out.println("Product not found or deletion failed.");
                                }
                                break;


                           case 3:
                                System.out.println("\nMicro Enterprise Management System");
                                System.out.println("[Seller] Updating Products.");

                                // Get details of the product to update
                                System.out.print("\nEnter the Product ID to update: ");
                                int productIdToUpdate = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline character

                                String updatedProductName = "";
                                while (updatedProductName.trim().isEmpty()) {
                                    System.out.print("Enter updated Name of the product: ");
                                    updatedProductName = scanner.nextLine().trim();
                                    if (updatedProductName.isEmpty()) {
                                        System.out.println("Product name cannot be empty. Please enter a valid Product name.");
                                    }
                                }

                                String updatedProductCode = "";
                                while (updatedProductCode.trim().isEmpty()) {
                                    System.out.print("Enter updated Code of the product: ");
                                    updatedProductCode = scanner.nextLine().trim();
                                    if (updatedProductCode.isEmpty()) {
                                        System.out.println("Product code cannot be empty. Please enter a valid Product code.");
                                    }
                                }

                                // Perform update using the Update class method
                                boolean isUpdated = Update.updateProduct(productIdToUpdate, updatedProductName, updatedProductCode);

                                if (isUpdated) {
                                    System.out.println("Product update successful!");
                                } else {
                                    System.out.println("Product update failed. No matching records found.");
                                }
                                break;

                            case 4:
                                // View certain product
                                viewProducts.viewProducts(connection);
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