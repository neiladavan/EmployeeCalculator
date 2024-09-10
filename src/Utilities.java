import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utilities {
    // list of Alberta Holidays
    static final ArrayList<LocalDate> HOLIDAYS = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<WorkEntry> workEntries = new ArrayList<>();

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

    public static LocalDate getValidDate() {
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
}
