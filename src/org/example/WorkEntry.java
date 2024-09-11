package org.example;

import java.time.LocalDate;

/**
 * Represents a work entry for an employee, including details such as the date of work, hours worked, and daily pay.
 * This class provides methods to access work details and determine if the work date falls on a holiday or weekend.
 *
 * @author Neil Adavan
 * @version 1.0
 * @since 2024-09
 */
@SuppressWarnings("ClassCanBeRecord")
public class WorkEntry {
    private final LocalDate workDate;
    private final double hoursWorked;
    private final double dailyPay;

    /**
     * Constructs a new {@code WorkEntry} instance with the specified work date, hours worked, and daily pay.
     *
     * @param workDate the date of work
     * @param hoursWorked the number of hours worked on the specified date
     * @param dailyPay the amount of pay earned for the work on the specified date
     */
    public WorkEntry(LocalDate workDate, double hoursWorked, double dailyPay) {
        this.workDate = workDate;
        this.hoursWorked = hoursWorked;
        this.dailyPay = dailyPay;
    }

    /**
     * Returns the date of the work entry.
     *
     * @return the work date
     */
    public LocalDate getWorkDate() {
        return workDate;
    }

    /**
     * Returns the number of hours worked on the specified date.
     *
     * @return the hours worked
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Returns the amount of pay earned for the work on the specified date.
     *
     * @return the daily pay
     */
    public double getDailyPay() {
        return dailyPay;
    }

    /**
     * Determines if the work date falls on a holiday, weekend, or is a regular working day.
     *
     * @return a string indicating if the work date is a "Holiday", "Weekend", or "Regular"
     */
    public String isHolidayOrWeekend(){
        // check if it is holiday
        if (Utilities.HOLIDAYS.contains(workDate)){
            return "Holiday";
        }

        // check if it is Saturday(6) or Sunday(7)
        if (workDate.getDayOfWeek().getValue() == 6 || workDate.getDayOfWeek().getValue() == 7){
            return "Weekend";
        }

        return "Regular";
    }
}
