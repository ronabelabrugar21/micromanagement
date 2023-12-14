
package java_activities;

import java.util.Scanner;

public class Micro_Payment {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total cost: $");
        double totalCost = scanner.nextDouble();

        System.out.print("Enter payment amount: $");
        double paymentAmount = scanner.nextDouble();

        if (paymentAmount < totalCost) {
            System.out.println("Insufficient payment. Transaction canceled.");
        } else {
            double change = paymentAmount - totalCost;
            System.out.println("Payment successful!");
            System.out.println("Change: $" + change);
        }

        scanner.close();
    }
}



