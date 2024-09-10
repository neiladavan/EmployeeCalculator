/*
* Description: JAVA application that collects work hours entries for an employee over multiple days and calculates employee pay for the period.
* Author: Neil
* Date: 09-2024
* */

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Scanner;

public class MainBackup {
    // Constants
    private static final double OVERTIME_THRESHOLD = 7.5;
    private static final double MAX_HOURS_PER_DAY = 16.0;
    private static final double OVERTIME_RATE = 1.5;

    // list of Alberta Holidays
    static final ArrayList<LocalDate> HOLIDAYS = new ArrayList<>();

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get Employee Details
        System.out.println("Enter employee name:");
        String employeeName = scanner.nextLine();
        while (!Validation.isValidName(employeeName)){
            System.out.println("Invalid employee name!");
            employeeName = scanner.nextLine();
        }

        // Get pay rate per hour
        System.out.println("Enter pay rate per hours:");
        double payRate = scanner.nextDouble();
        while (!Validation.isValidPayRate(payRate)){
            System.out.println("Invalid pay rate!");
            payRate = scanner.nextDouble();
        }

        scanner.nextLine(); // Consume newline if it has errors

        Employee employee = new Employee(employeeName, payRate);
        ArrayList<WorkEntry> workEntries = new ArrayList<>();
        double totalPay = 0.0;

        // Get all work entries
        while (true){
            System.out.println("Enter Date (YYYY-MM-DD):)");
            LocalDate workDate = LocalDate.parse(scanner.nextLine());
            while (!Validation.isValidDate(workDate, workEntries)){
                System.out.println("Invalid or duplicate date. Enter a valid date:");
                workDate = LocalDate.parse(scanner.nextLine());
            }

            System.out.println("Enter hours worked:");
            double hoursWorked = scanner.nextDouble();
            while (!Validation.isValidHours(hoursWorked)){
                System.out.println("Invalid hours. Enter hours between 1 and " + MAX_HOURS_PER_DAY );
                hoursWorked = scanner.nextDouble();
            }

            scanner.nextLine(); // Consume newline if it has errors

            // Calculate Pay
            double dailyPay = calculateDailyPay(workDate, hoursWorked, employee.getHourlyRate());
            totalPay += dailyPay;

            // Add work entry
            workEntries.add(new WorkEntry(workDate, hoursWorked, dailyPay));

            // Ask user if there are more entries
            System.out.println("Do you want to add another entry? (Y/N)");
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase("y")){
                break;
            }
        }

        // Set work entries and total pay in Employee object
        employee.setWorkEntries(workEntries);
        employee.setTotalPay(totalPay);

        // Display detailed list of work entries and total pay using toString()
        System.out.println(employee);

        /*// Display detailed list of work entries and total pay
        System.out.printf("\nDetailed pay summary for %s:\n", employee.getName());
        System.out.printf("%-12s %-10s %-10s %-12s\n", "Date", "Weekend/Holiday", "Hours Worked", "Pay ($)");
        for (WorkEntry entry : workEntries) {
            String indicator = entry.isHolidayOrWeekend(HOLIDAYS);
            System.out.printf("%-12s %-10s %-10.2f %-12.2f\n", entry.getWorkDate(), indicator, entry.getHoursWorked(), entry.getDailyPay());
        }

        // Display total pay
        System.out.printf("\nTotal pay for %s: $%.2f\n", employee.getName(), totalPay);*/
        scanner.close();
    }

    private static double calculateDailyPay(LocalDate workDate, double hoursWorked, double hourlyRate) {
        double pay = 0.0;
        double overtimeHours = 0.0;

        // Check if it's holiday or weekend
        boolean isHolidayWeekend = Validation.isHolidayOrWeekend(HOLIDAYS, workDate);

        // Pay "time and a half" for hours over 7.5 or if it's a weekend/holiday
        if (hoursWorked > OVERTIME_THRESHOLD){
            overtimeHours = hoursWorked - OVERTIME_THRESHOLD;
            hoursWorked = OVERTIME_THRESHOLD;
        }

        if (isHolidayWeekend){
            pay = (hoursWorked + overtimeHours) * hourlyRate * OVERTIME_RATE;
        }
        else {
            pay = hoursWorked * hourlyRate;
            pay += overtimeHours * hourlyRate * OVERTIME_RATE; // Overtime hours at time and a half
        }

        return pay;
    }
}