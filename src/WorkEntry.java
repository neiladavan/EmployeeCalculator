import java.time.LocalDate;
import java.util.ArrayList;

public class WorkEntry {
    private final LocalDate workDate;
    private final double hoursWorked;
    private final double dailyPay;

    public WorkEntry(LocalDate workDate, double hoursWorked, double dailyPay) {
        this.workDate = workDate;
        this.hoursWorked = hoursWorked;
        this.dailyPay = dailyPay;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getDailyPay() {
        return dailyPay;
    }

    public String isHolidayOrWeekend(ArrayList<LocalDate> holidays){
        // check if it is holiday
        if (holidays.contains(workDate)){
            return "Holiday";
        }

        // check if it is Saturday(6) or Sunday(7)
        if (workDate.getDayOfWeek().getValue() == 6 || workDate.getDayOfWeek().getValue() == 7){
            return "Weekend";
        }

        return "Regular";
    }
}
