import java.util.Scanner;

public class NumberPatternGenerator {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("========================================");
        System.out.println("       NUMBER PATTERN GENERATOR");
        System.out.println("========================================");

        while (running) {

            // Display menu
            System.out.println();
            System.out.println("Choose a pattern:");
            System.out.println("1. Increasing Number Pattern");
            System.out.println("2. Repeated Number Pattern");
            System.out.println("3. Number Pyramid");
            System.out.println("4. Exit");

            System.out.print("\nEnter your choice: ");

            String choiceInput = sc.nextLine().trim();

            int choice;

            // Validate menu input
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {

                System.out.println(
                    "Invalid input! Please enter a number from 1 to 4."
                );

                continue;
            }

            // Exit option
            if (choice == 4) {

                running = false;

                System.out.println();
                System.out.println(
                    "Thank you for using the Pattern Generator!"
                );

                continue;
            }

            // Validate menu choice
            if (choice < 1 || choice > 4) {

                System.out.println(
                    "Invalid choice! Please select an option from 1 to 4."
                );

                continue;
            }

            // Ask for number of rows
            System.out.print(
                "Enter the number of rows (1-20): "
            );

            String rowsInput = sc.nextLine().trim();

            int rows;

            // Validate rows input
            try {

                rows = Integer.parseInt(rowsInput);

            } catch (NumberFormatException e) {

                System.out.println(
                    "Invalid input! Please enter a whole number."
                );

                continue;
            }

            // Validate row range
            if (rows < 1 || rows > 20) {

                System.out.println(
                    "Invalid number of rows! " +
                    "Please enter a value from 1 to 20."
                );

                continue;
            }

            System.out.println();
            System.out.println("Generated Pattern:");
            System.out.println("----------------------------------------");

            // Generate selected pattern
            switch (choice) {

                case 1:
                    increasingPattern(rows);
                    break;

                case 2:
                    repeatedPattern(rows);
                    break;

                case 3:
                    numberPyramid(rows);
                    break;
            }

            System.out.println("----------------------------------------");
        }

        sc.close();
    }

    // Pattern 1:
    // 1
    // 12
    // 123
    // 1234
    // 12345
    static void increasingPattern(int rows) {

        for (int i = 1; i <= rows; i++) {

            for (int j = 1; j <= i; j++) {

                System.out.print(j);
            }

            System.out.println();
        }
    }

    // Pattern 2:
    // 1
    // 22
    // 333
    // 4444
    // 55555
    static void repeatedPattern(int rows) {

        for (int i = 1; i <= rows; i++) {

            for (int j = 1; j <= i; j++) {

                System.out.print(i);
            }

            System.out.println();
        }
    }

    // Pattern 3:
    //     1
    //    123
    //   12345
    //  1234567
    // 123456789
    static void numberPyramid(int rows) {

        for (int i = 1; i <= rows; i++) {

            // Print spaces
            for (int space = 1; space <= rows - i; space++) {

                System.out.print(" ");
            }

            // Print numbers
            for (int j = 1; j <= (2 * i - 1); j++) {

                System.out.print(j % 10);
            }

            System.out.println();
        }
    }
}