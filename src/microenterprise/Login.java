package microenterprise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    // Database connection parameters
    private String url = "jdbc:mysql://localhost:3306/microenterprise";
    private String user = "root";
    private String password = "";
    
    public static final int SUCCESS = 0;
    public static final int INVALID_USERNAME = 1;
    public static final int INVALID_PASSWORD = 2;
    public static final int USER_NOT_FOUND = 3;
    public static final int USERNAME_AND_PASSWORD_REQUIRED = 4; // New constant

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "loginid INT PRIMARY KEY AUTO_INCREMENT," +
                "username VARCHAR(40) NOT NULL," +
                "password VARCHAR(40) NOT NULL)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate(String username, String password) {
        try (Connection connection = DriverManager.getConnection(url, user, this.password)) {
            String selectSQL = "SELECT * FROM logindb WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   public int authenticates(String username, String inputPassword) {
    // Check for null or empty username and password
    boolean isUsernameEmpty = username == null || username.trim().isEmpty();
    boolean isPasswordEmpty = inputPassword == null || inputPassword.trim().isEmpty();

    if (isUsernameEmpty && isPasswordEmpty) {
        return USERNAME_AND_PASSWORD_REQUIRED;
    } else if (isUsernameEmpty) {
        return INVALID_USERNAME;
    } else if (isPasswordEmpty) {
        return INVALID_PASSWORD;
    }

    try (Connection connection = DriverManager.getConnection(url, user, this.password)) {
        String userExistsQuery = "SELECT password FROM logindb WHERE username = ?";
        try (PreparedStatement userExistsStmt = connection.prepareStatement(userExistsQuery)) {
            userExistsStmt.setString(1, username);
            ResultSet userExistsResult = userExistsStmt.executeQuery();
            if (!userExistsResult.next()) {
                return USER_NOT_FOUND;
            } else {
                String storedPassword = userExistsResult.getString("password");
                if (storedPassword.equals(inputPassword)) {
                    return SUCCESS;
                } else {
                    return INVALID_PASSWORD;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return USER_NOT_FOUND; // Fallback case if something goes wrong
}

}
