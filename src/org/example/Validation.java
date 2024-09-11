package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Utility class that provides methods for validating names, work dates, and determining holidays or weekends.
 * It contains methods for validating employee names, checking the validity of work dates,
 * and identifying if a date is a holiday or weekend.
 *
 * @author Neil Adavan
 * @version 1.0
 * @since 2024-09
 */
public class Validation {
    // Regular expression for a valid name
    private static final String NAME_PATTERN = "^[A-Za-z\\s'-]+$";

    /**
     * Validates if the provided name is valid according to a specific pattern.
     *
     * @param name the name to validate
     * @return {@code true} if the name is valid; {@code false} otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false; // Name cannot be null or empty
        }
        return Pattern.matches(NAME_PATTERN, name);
    }

    /**
     * Validates if the provided work date is valid. The date is considered valid if it is in the current year
     * and does not duplicate any existing work entry dates.
     *
     * @param workDate the date to validate
     * @param workEntries the list of existing {@link WorkEntry} objects to ensure the date is unique
     * @return {@code true} if the date is valid; {@code false} otherwise
     */
    public static boolean isValidDate(LocalDate workDate, ArrayList<WorkEntry> workEntries){
        int currentYear = LocalDate.now().getYear();

        if (workDate.getYear() != currentYear){
            return false;
        }

        for (WorkEntry entry : workEntries){
            if (entry.getWorkDate().equals(workDate)){
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the provided work date is a holiday or falls on a weekend.
     *
     * @param holidays a list of {@link LocalDate} representing holidays
     * @param workDate the date to check
     * @return {@code true} if the date is a holiday or falls on a weekend; {@code false} otherwise
     */
    public static boolean isHolidayOrWeekend(ArrayList<LocalDate> holidays, LocalDate workDate){
        // check if it is holiday or weekend or regular
        return holidays.contains(workDate) || workDate.getDayOfWeek().getValue() == 6 || workDate.getDayOfWeek().getValue() == 7;
    }
}
