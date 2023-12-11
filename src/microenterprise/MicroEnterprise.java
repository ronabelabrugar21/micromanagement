package microenterprise;

import java.util.Scanner;

public class MicroEnterprise {

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
