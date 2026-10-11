import java.util.Scanner;

public class TemperatureConverter {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("========================================");
        System.out.println("        TEMPERATURE CONVERTER");
        System.out.println("========================================");

        while (running) {

            displayMenu();

            int choice = readInteger(
                    "Enter your choice: ", 1, 7);

            switch (choice) {

                case 1:
                    celsiusToFahrenheit();
                    break;

                case 2:
                    fahrenheitToCelsius();
                    break;

                case 3:
                    celsiusToKelvin();
                    break;

                case 4:
                    kelvinToCelsius();
                    break;

                case 5:
                    fahrenheitToKelvin();
                    break;

                case 6:
                    kelvinToFahrenheit();
                    break;

                case 7:
                    running = false;
                    System.out.println();
                    System.out.println(
                            "Thank you for using Temperature Converter!");
                    break;
            }
        }

        sc.close();
    }

    // Display menu
    static void displayMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              CONVERSION MENU");
        System.out.println("========================================");

        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.println("3. Celsius to Kelvin");
        System.out.println("4. Kelvin to Celsius");
        System.out.println("5. Fahrenheit to Kelvin");
        System.out.println("6. Kelvin to Fahrenheit");
        System.out.println("7. Exit");

        System.out.println("========================================");
    }

    // Celsius to Fahrenheit
    static void celsiusToFahrenheit() {

        System.out.println("\n--- Celsius to Fahrenheit ---");

        double celsius = readTemperature();

        if (celsius < -273.15) {

            System.out.println(
                    "Invalid temperature! "
                    + "The temperature cannot be below absolute zero.");

            return;
        }

        double fahrenheit =
                (celsius * 9 / 5) + 32;

        System.out.printf(
                "%.2f deg C = %.2f deg F%n",
                celsius,
                fahrenheit);
    }

    // Fahrenheit to Celsius
    static void fahrenheitToCelsius() {

        System.out.println("\n--- Fahrenheit to Celsius ---");

        double fahrenheit = readTemperature();

        if (fahrenheit < -459.67) {

            System.out.println(
                    "Invalid temperature! "
                    + "The temperature cannot be below absolute zero.");

            return;
        }

        double celsius =
                (fahrenheit - 32) * 5 / 9;

        System.out.printf(
                "%.2f deg F = %.2f deg C%n",
                fahrenheit,
                celsius);
    }

    // Celsius to Kelvin
    static void celsiusToKelvin() {

        System.out.println("\n--- Celsius to Kelvin ---");

        double celsius = readTemperature();

        double kelvin =
                celsius + 273.15;

        if (kelvin < 0) {

            System.out.println(
                    "Invalid temperature! "
                    + "The temperature cannot be below absolute zero.");

            return;
        }

        System.out.printf(
                "%.2f deg C = %.2f K%n",
                celsius,
                kelvin);
    }

    // Kelvin to Celsius
    static void kelvinToCelsius() {

        System.out.println("\n--- Kelvin to Celsius ---");

        double kelvin = readKelvin();

        double celsius =
                kelvin - 273.15;

        System.out.printf(
                "%.2f K = %.2f deg C%n",
                kelvin,
                celsius);
    }

    // Fahrenheit to Kelvin
    static void fahrenheitToKelvin() {

        System.out.println("\n--- Fahrenheit to Kelvin ---");

        double fahrenheit = readTemperature();

        double kelvin =
                (fahrenheit - 32) * 5 / 9 + 273.15;

        if (kelvin < 0) {

            System.out.println(
                    "Invalid temperature! "
                    + "The temperature cannot be below absolute zero.");

            return;
        }

        System.out.printf(
                "%.2f deg F = %.2f K%n",
                fahrenheit,
                kelvin);
    }

    // Kelvin to Fahrenheit
    static void kelvinToFahrenheit() {

        System.out.println("\n--- Kelvin to Fahrenheit ---");

        double kelvin = readKelvin();

        double fahrenheit =
                (kelvin - 273.15) * 9 / 5 + 32;

        System.out.printf(
                "%.2f K = %.2f deg F%n",
                kelvin,
                fahrenheit);
    }

    // Read normal temperature
    static double readTemperature() {

        while (true) {

            System.out.print("Enter temperature: ");

            String input = sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Input cannot be empty.");

                continue;
            }

            try {

                double temperature =
                        Double.parseDouble(input);

                if (Double.isNaN(temperature)
                        || Double.isInfinite(temperature)) {

                    System.out.println(
                            "Invalid temperature. "
                            + "Please enter a valid number.");

                    continue;
                }

                return temperature;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! "
                        + "Please enter a valid temperature.");
            }
        }
    }

    // Read Kelvin temperature
    static double readKelvin() {

        while (true) {

            System.out.print("Enter temperature in Kelvin: ");

            String input = sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Input cannot be empty.");

                continue;
            }

            try {

                double kelvin =
                        Double.parseDouble(input);

                if (Double.isNaN(kelvin)
                        || Double.isInfinite(kelvin)) {

                    System.out.println(
                            "Invalid temperature. "
                            + "Please enter a valid number.");

                    continue;
                }

                if (kelvin < 0) {

                    System.out.println(
                            "Invalid Kelvin value! "
                            + "Kelvin cannot be less than 0.");

                    continue;
                }

                return kelvin;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! "
                        + "Please enter a valid number.");
            }
        }
    }

    // Read integer menu choice
    static int readInteger(
            String message,
            int min,
            int max) {

        while (true) {

            System.out.print(message);

            String input =
                    sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Input cannot be empty.");

                continue;
            }

            try {

                int value =
                        Integer.parseInt(input);

                if (value < min || value > max) {

                    System.out.println(
                            "Please enter a number between "
                            + min + " and " + max + ".");

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! "
                        + "Please enter a whole number.");
            }
        }
    }
}