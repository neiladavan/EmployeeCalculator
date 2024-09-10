import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Validation {
    // Regular expression for a valid name
    private static final String NAME_PATTERN = "^[A-Za-z\\s'-]+$";

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false; // Name cannot be null or empty
        }
        return Pattern.matches(NAME_PATTERN, name);
    }

    // Validate work date (current year and unique date)
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

    public static boolean isHolidayOrWeekend(ArrayList<LocalDate> holidays, LocalDate workDate){
        // check if it is holiday or weekend or regular
        return holidays.contains(workDate) || workDate.getDayOfWeek().getValue() == 6 || workDate.getDayOfWeek().getValue() == 7;
    }
}
