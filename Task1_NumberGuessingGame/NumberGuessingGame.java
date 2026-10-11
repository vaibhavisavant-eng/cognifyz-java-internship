import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        boolean playAgain = true;

        System.out.println("========================================");
        System.out.println("        NUMBER GUESSING GAME");
        System.out.println("========================================");

        while (playAgain) {

            // Generate a new secret number for every game
            int secretNumber = random.nextInt(100) + 1;

            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println();
            System.out.println("I have selected a secret number between 1 and 100.");
            System.out.println("Try to guess the number!");
            System.out.println();
            System.out.println("RULES:");
            System.out.println("• Enter a whole number between 1 and 100.");
            System.out.println("• I will tell you if your guess is too high or too low.");
            System.out.println("• Keep guessing until you find the correct number.");
            System.out.println();

            // Game loop
            while (!guessedCorrectly) {

                System.out.print("Enter your guess: ");

                String input = sc.nextLine().trim();

                // Check for empty input
                if (input.isEmpty()) {

                    System.out.println(
                        "Input cannot be empty. Please enter a number between 1 and 100."
                    );

                    continue;
                }

                int guess;

                // Convert input to integer
                try {

                    guess = Integer.parseInt(input);

                } catch (NumberFormatException e) {

                    System.out.println(
                        "Invalid input! Please enter a whole number."
                    );

                    continue;
                }

                // Check range
                if (guess < 1 || guess > 100) {

                    System.out.println(
                        "Number out of range! Please enter a number between 1 and 100."
                    );

                    continue;
                }

                // Count only valid guesses
                attempts++;

                // Compare guess with secret number
                if (guess < secretNumber) {

                    System.out.println("Too low! Try again.");

                } else if (guess > secretNumber) {

                    System.out.println("Too high! Try again.");

                } else {

                    guessedCorrectly = true;

                    System.out.println();
                    System.out.println("🎉 Congratulations! You guessed it!");
                    System.out.println(
                        "You found the number in "
                        + attempts
                        + " valid attempt(s)."
                    );
                }
            }

            // Ask whether the user wants another game
            while (true) {

                System.out.print("\nDo you want to play again? (yes/no): ");

                String answer = sc.nextLine().trim().toLowerCase();

                if (answer.equals("yes") || answer.equals("y")) {

                    playAgain = true;
                    break;

                } else if (answer.equals("no") || answer.equals("n")) {

                    playAgain = false;
                    break;

                } else {

                    System.out.println(
                        "Invalid choice! Please enter yes or no."
                    );
                }
            }
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println("        Thanks for playing!");
        System.out.println("========================================");

        sc.close();
    }
}