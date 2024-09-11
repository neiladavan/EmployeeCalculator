package org.example;

import org.example.data.EmployeeDB;
import org.example.models.Employee;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Main class to run the employee work management program.
 * This class handles the creation of employees, collecting their work entries,
 * and displaying all employee data.
 *
 * @author Neil Adavan
 * @version 1.0
 * @since 2024-09
 */
public class Main {
    private static final double MAX_HOURS_PER_DAY = 16.0;
    private static final double MIN_HOURS_PER_DAY = 1.0;
    private static final double MAX_PAY_RATE_PER_HOUR = 100.0;
    private static final double MIN_PAY_RATE_PER_HOUR = 1.0;

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * The main method serves as the entry point for the program.
     * It handles the main program loop where employees are created, work entries are added,
     * and all employees are displayed after data entry is complete.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        while (true) {
            // Create a new org.example.models.Employee object
            Employee employee = createEmployee();

            // Collect work entries for the employee
            collectWorkEntries(employee);

            // Add employee to the list
            //employees.add(employee);
            EmployeeDB.addEmployee(employee);

            // Ask if they want to add another employee
            if (!Utilities.shouldContinue("Do you want to add another employee? (Y/N)")) {
                break;
            }
        }

        // Display all employee data
        EmployeeDB.displayAllEmployees();
        scanner.close();
    }

    /**
     * Prompts the user to input an employee's name and pay rate per hour.
     * Validates the input values and creates a new {@link org.example.models.Employee} object.
     *
     * @return the newly created Employee object
     */
    // Method to create a new org.example.models.Employee object
    private static Employee createEmployee() {
        System.out.println("Enter employee name:");
        String employeeName = scanner.nextLine();

        // Validate employee name
        while (!Validation.isValidName(employeeName)) {
            System.out.println("Invalid employee name! Only letters, spaces, hyphens, and apostrophes are allowed.");
            employeeName = scanner.nextLine();
        }

        // Validate pay rate per hour
        double payRate = Utilities.getValidDoubleInput(
                "Enter pay rate per hour (numeric values only):",
                MIN_PAY_RATE_PER_HOUR,
                MAX_PAY_RATE_PER_HOUR
        );

        return new Employee(employeeName, payRate);
    }

    /**
     * Collects work entries (date and hours worked) for a given employee.
     * Repeatedly prompts the user for valid work dates and hours worked, then adds the work entry to the employee.
     *
     * @param employee the Employee object to which work entries will be added
     */
    // Method to collect work entries for an employee
    private static void collectWorkEntries(Employee employee) {
        while (true) {
            // Get a valid work date
            LocalDate workDate = Utilities.getValidDate(employee.getWorkEntries());

            // Get a valid number of hours worked
            double hoursWorked = Utilities.getValidDoubleInput(
                    "Enter hours worked (numeric values only):",
                    MIN_HOURS_PER_DAY,
                    MAX_HOURS_PER_DAY
            );

            // Add the work entry to the employee
            employee.addWorkEntry(workDate, hoursWorked);

            // Ask if they want to add another work entry
            if (!Utilities.shouldContinue("Do you want to add another entry? (Y/N)")) {
                break;
            }
        }
    }
}