package org.example.data;

import org.example.models.Employee;

import java.util.ArrayList;

public class EmployeeDB {
    // Static list to store employees
    private static final ArrayList<Employee> employees = new ArrayList<>();

    // Method to add an employee to the list
    public static void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Method to display all employees
    public static void displayAllEmployees() {
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }
}
