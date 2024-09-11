package org.example.models;

import org.example.Utilities;
import org.example.Validation;
import org.example.WorkEntry;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class represents an employee, including their name, hourly rate, work entries, and total pay.
 * It provides methods for adding work entries, calculating daily pay, and retrieving employee information.
 *
 * @author Neil Adavan
 * @version 1.0
 * @since 2024-09
 */
public class Employee {
    private final String name;
    private final double hourlyRate;
    private ArrayList<WorkEntry> workEntries;
    private double totalPay;

    /**
     * Constructor for creating an {@code Employee} object.
     *
     * @param name the name of the employee
     * @param hourlyRate the hourly pay rate of the employee
     */
    public Employee(String name, double hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.workEntries = new ArrayList<>();
        this.totalPay = 0.0;
    }

    /**
     * Gets the name of the employee.
     *
     * @return the employee's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the hourly rate of the employee.
     *
     * @return the employee's hourly rate
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Adds a work entry to the employee's work log, calculates daily pay, and updates total pay.
     *
     * @param workDate the date of the work entry
     * @param hoursWorked the number of hours worked on the specified date
     */
    public void addWorkEntry(LocalDate workDate, double hoursWorked) {
        double dailyPay = calculateDailyPay(workDate, hoursWorked);  // Calculate daily pay

        WorkEntry newEntry = new WorkEntry(workDate, hoursWorked, dailyPay);  // Create org.example.WorkEntry
        workEntries.add(newEntry);  // Add to workEntries list
        //totalPay += dailyPay;
        setTotalPay(dailyPay); // Update total pay
    }

    /**
     * Gets the list of work entries for the employee.
     *
     * @return a list of {@link WorkEntry} objects representing the employee's work entries
     */
    public ArrayList<WorkEntry> getWorkEntries() {
        return workEntries;
    }

    /**
     * Sets the list of work entries for the employee.
     *
     * @param workEntries the new list of {@link WorkEntry} objects to set
     */
    public void setWorkEntries(ArrayList<WorkEntry> workEntries) {
        this.workEntries = workEntries;
    }

    /**
     * Gets the total pay accumulated by the employee.
     *
     * @return the employee's total pay
     */
    public double getTotalPay() {
        return totalPay;
    }

    /**
     * Updates the total pay by adding the given amount.
     *
     * @param totalPay the amount to add to the current total pay
     */
    public void setTotalPay(double totalPay) {
        this.totalPay += totalPay;
    }

    /**
     * Calculates the daily pay for the employee, including potential overtime or holiday pay.
     *
     * @param workDate the date of the work entry
     * @param hoursWorked the number of hours worked on the specified date
     * @return the calculated daily pay for the given work date and hours worked
     */
    public double calculateDailyPay(LocalDate workDate, double hoursWorked) {
        double pay;
        double overtimeHours = 0.0;

        // Constants
        final double OVERTIME_THRESHOLD = 7.5;
        final double OVERTIME_RATE = 1.5;

        // Check if it's holiday or weekend
        boolean isHolidayWeekend = Validation.isHolidayOrWeekend(Utilities.HOLIDAYS, workDate);

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

    /**
     * Returns a string representation of the employee's work entries and total pay.
     * Includes details such as the date, weekend/holiday status, hours worked, and daily pay.
     *
     * @return a formatted string summarizing the employee's work entries and total pay
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Detailed pay summary for %s:\n", name));
        result.append(String.format("Hourly Rate %.2f:\n", hourlyRate));
        result.append(String.format("%-12s %-16s %-14s %-12s\n", "Date", "Weekend/Holiday", "Hours Worked", "Pay ($)"));

        for (WorkEntry entry : getWorkEntries()) {
            String indicator = entry.isHolidayOrWeekend();
            result.append(String.format("%-12s %-16s %-14.2f %-12.2f\n",
                    entry.getWorkDate(),
                    indicator,
                    entry.getHoursWorked(),
                    entry.getDailyPay()));
        }

        // Add total pay at the end
        result.append(String.format("Total pay for %s: $%s", name, Utilities.formatCurrency(totalPay)));
        return result.toString();
    }
}
