import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final double MAX_HOURS_PER_DAY = 16.0;
    private static final double MIN_HOURS_PER_DAY = 1.0;
    private static final double MAX_PAY_RATE_PER_HOUR = 100.0;
    private static final double MIN_PAY_RATE_PER_HOUR = 1.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employees = new ArrayList<>();

        while (true) {
            // Get Employee Details
            System.out.println("Enter employee name:");
            String employeeName = scanner.nextLine();
            while (!Validation.isValidName(employeeName)){
                System.out.println("Invalid employee name! Only letters, spaces, hyphens, and apostrophes are allowed.");
                employeeName = scanner.nextLine();
            }

            // Get pay rate per hour
            double payRate = Utilities.getValidDoubleInput(MIN_PAY_RATE_PER_HOUR, MAX_PAY_RATE_PER_HOUR);

            // Create a new Employee object
            Employee employee = new Employee(employeeName, payRate);

            // Collect work entries for the employee
            while (true) {
                LocalDate workDate = null;
                boolean validDate = false;

                // Get work date
                while (!validDate) {
                    System.out.println("Enter Date (YYYY-MM-DD):");
                    String dateInput = scanner.nextLine();

                    try {
                        workDate = LocalDate.parse(dateInput);
                        validDate = true; // Assume date validation is correct
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format! Please enter the date in YYYY-MM-DD format.");
                    }
                }

                // Get hours worked
                double hoursWorked = Utilities.getValidDoubleInput(MIN_HOURS_PER_DAY, MAX_HOURS_PER_DAY);

                // Add the work entry to the employee
                employee.addWorkEntry(workDate, hoursWorked);

                // Ask if they want to add another work entry
                System.out.println("Do you want to add another entry? (Y/N)");
                String answer = scanner.nextLine();
                if (!answer.equalsIgnoreCase("y")) {
                    break;
                }
            }

            // Add employee to the list
            employees.add(employee);

            // Ask if they want to add another employee
            System.out.println("Do you want to add another employee? (Y/N)");
            String anotherEmployee = scanner.nextLine();
            if (!anotherEmployee.equalsIgnoreCase("y")) {
                break;
            }
        }

        // Display all employee data
        for (Employee emp : employees) {
            System.out.println(emp);
        }

        scanner.close();
    }
}