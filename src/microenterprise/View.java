package microenterprise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {

    private final Scanner scanner;

    public View(Scanner scanner) {
        this.scanner = scanner;
    }

    public void viewProducts(Connection connection) {
        List<String> productList = viewProducts(connection, "", "");

        System.out.println("\nSearch the product to view:");
        System.out.print("Name: ");
        String searchName = scanner.nextLine();
        System.out.print("Code: ");
        String searchCode = scanner.nextLine();

        productList = viewProducts(connection, searchName, searchCode);

        System.out.println("\nFiltered Products:");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println((i + 1) + ". " + productList.get(i));
        }
    }

    private List<String> viewProducts(Connection connection, String searchName, String searchCode) {
        List<String> result = new ArrayList<>(); 

        String selectProductsSQL = "SELECT * FROM products WHERE (name LIKE '%" + searchName + "%' OR '" + 
                searchName + "' IS NULL) AND (code = '" + searchCode + "' OR '" + searchCode + "' IS NULL)";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectProductsSQL)) {
            while (resultSet.next()) {
                String productName = resultSet.getString("name");
                double productPrice = resultSet.getDouble("price");
                int productStock = resultSet.getInt("stock");
                String productCode = resultSet.getString("code");
                String productDateAdded = resultSet.getString("date_added");

                result.add(String.format("[%s, $%.2f, %d Pcs., %s, %s]", productName, productPrice, productStock, productCode, productDateAdded));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
