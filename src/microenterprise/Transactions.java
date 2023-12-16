package microenterprise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;




public class Transactions {


    public void displayTransactionHistory(Connection connection) {
    String selectProductsSQL = "SELECT name, stock, code, date_added FROM products";
    try (PreparedStatement preparedStatement = connection.prepareStatement(selectProductsSQL)) {
        ResultSet resultSet = preparedStatement.executeQuery();

         System.out.println("Add Product Transactions");
        System.out.println("name\t\tquantity\tcode\t\tdate_added");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int stock = resultSet.getInt("stock");
            String code = resultSet.getString("code");
            String dateAdded = resultSet.getString("date_added");


            System.out.printf("%s\t\t%d\t\t%s\t\t%s%n", name, stock, code, dateAdded);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    // Method to view transaction history
    public void viewTransactionHistory(Connection connection) {
        // Assuming the table name is 'transactions'
        String query = "SELECT * FROM transactions";

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            System.out.println("\nTRANSACTION HISTORY:");
            while (resultSet.next()) {
                String purchaseDate = resultSet.getString("purchase_date");
                String purchasedItems = resultSet.getString("purchased_items");
                int purchasedQuantity = resultSet.getInt("purchased_quantity");
                double totalPrice = resultSet.getDouble("total_price");

                System.out.println("Purchase Date: " + purchaseDate);
                System.out.println("Purchased Items: " + purchasedItems);
                System.out.println("Purchased Quantity: " + purchasedQuantity);
                System.out.println("Total Price: " + totalPrice);
                System.out.println();
      
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}
