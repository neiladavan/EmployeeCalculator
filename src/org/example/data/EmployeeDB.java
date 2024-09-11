package org.example.data;

import org.example.models.Employee;

import java.util.ArrayList;

/**
 * This class acts as a simple database for storing and managing {@link Employee} objects.
 * It uses a static list to store employee data and provides methods to add employees
 * and display all employees.
 *
 * @author Neil Adavan
 * @version 1.0
 * @since 2024-09
 */
public class EmployeeDB {

    /**
     * A static list that stores {@link Employee} objects.
     * This list simulates an in-memory database of employees.
     */
    // Static list to store employees
    private static final ArrayList<Employee> employees = new ArrayList<>();

    /**
     * Adds an employee to the employees list.
     *
     * @param employee the {@link Employee} object to add to the list
     */
    // Method to add an employee to the list
    public static void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Displays all the employees currently stored in the employees list.
     * This method iterates over the list and prints out each employee's information.
     */
    // Method to display all employees
    public static void displayAllEmployees() {
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }
}
