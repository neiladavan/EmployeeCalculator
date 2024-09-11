package org.example;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utility class that provides general-purpose methods such as formatting currency, retrieving valid user inputs,
 * and managing holiday dates. It also defines a list of Alberta holidays for date validation purposes.
 *
 * This class is designed to facilitate user interactions and data validation in the application.
 *
 * @author Neil Adavan
 * @version 1.0
 * @since 2024-09
 */
public class Utilities {
    /**
     * List of predefined Alberta holidays.
     */
    public static final ArrayList<LocalDate> HOLIDAYS = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final NumberFormat nf = NumberFormat.getCurrencyInstance();

    /**
     * Formats a given double value as a currency string using the default locale.
     *
     * @param value the double value to format
     * @return the formatted currency string
     */
    public static String formatCurrency(double value) {
        return nf.format(value);
    }

    // Initialize HOLIDAYS
    static {
        int currentYear = LocalDate.now().getYear();
        HOLIDAYS.add(LocalDate.of(currentYear, Month.JANUARY, 1));   // New Year's Day
        HOLIDAYS.add(LocalDate.of(currentYear, Month.FEBRUARY, 19));  // Alberta Family Day
        HOLIDAYS.add(LocalDate.of(currentYear, Month.MARCH, 29));   // Good Friday
        HOLIDAYS.add(LocalDate.of(currentYear, Month.MAY, 20));  // Victoria Day
        HOLIDAYS.add(LocalDate.of(currentYear, Month.JULY, 1));   // Canada Day
        HOLIDAYS.add(LocalDate.of(currentYear, Month.SEPTEMBER, 2));   // Labour Day
        HOLIDAYS.add(LocalDate.of(currentYear, Month.OCTOBER, 14));  // Thanksgiving Day
        HOLIDAYS.add(LocalDate.of(currentYear, Month.NOVEMBER, 11)); // Remembrance Day
        HOLIDAYS.add(LocalDate.of(currentYear, Month.DECEMBER, 25)); // Christmas Day

        //OPTIONAL HOLIDAY
        HOLIDAYS.add(LocalDate.of(currentYear, Month.APRIL, 1)); // Easter Monday
        HOLIDAYS.add(LocalDate.of(currentYear, Month.AUGUST, 5));   // Heritage Day
        HOLIDAYS.add(LocalDate.of(currentYear, Month.SEPTEMBER, 30));   // National Day for Truth and Recognition
        HOLIDAYS.add(LocalDate.of(currentYear, Month.DECEMBER, 26)); // Boxing Day
    }

    /**
     * Prompts the user to enter a valid double value within a specified range.
     * It ensures that the value is between the specified minimum and maximum limits.
     *
     * @param promptMessage the message to display when prompting the user
     * @param minValue the minimum allowable value
     * @param maxValue the maximum allowable value
     * @return the valid double value entered by the user
     */
    public static double getValidDoubleInput(String promptMessage, double minValue, double maxValue) {
        Scanner scanner = new Scanner(System.in);
        double value = 0.0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println(promptMessage);
            try {
                value = Double.parseDouble(scanner.nextLine());
                if (value >= minValue && value <= maxValue) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Enter a value between " + minValue + " and " + maxValue + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
        return value;
    }

    /**
     * Prompts the user to enter a valid date in the YYYY-MM-DD format. The method ensures that the date is not
     * in the future, is within the current year, and is unique among the provided work entries.
     *
     * @param workEntries a list of existing {@link WorkEntry} objects to check for duplicate dates
     * @return the valid {@link LocalDate} entered by the user
     */
    public static LocalDate getValidDate(ArrayList<WorkEntry> workEntries) {
        while (true) {
            System.out.println("Enter Date (YYYY-MM-DD):");
            String dateInput = scanner.nextLine();

            try {
                LocalDate workDate = LocalDate.parse(dateInput);
                if (workDate.isAfter(LocalDate.now())) {
                    System.out.println("Invalid date. Work date cannot be a future date.");
                } else if (!Validation.isValidDate(workDate, workEntries)) {
                    System.out.println("Date already exists or should be current year.");
                } else {
                    return workDate;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter the date in YYYY-MM-DD format.");
            }
        }
    }

    /**
     * Prompts the user with a message and expects a yes/no response. If the user enters 'y', it returns {@code true};
     * otherwise, it returns {@code false}.
     *
     * @param message the message to display to the user
     * @return {@code true} if the user responds with 'y' or 'Y'; {@code false} otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean shouldContinue(String message) {
        System.out.println(message);
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("y");
    }
}
