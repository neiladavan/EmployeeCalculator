import java.time.LocalDate;
import java.util.ArrayList;

public class Employee {
    private final String name;
    private final double hourlyRate;
    private ArrayList<WorkEntry> workEntries;
    private double totalPay;

    public Employee(String name, double hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.workEntries = new ArrayList<>();
        this.totalPay = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    // Method to add work entry
    public void addWorkEntry(LocalDate workDate, double hoursWorked) {
        double dailyPay = calculateDailyPay(workDate, hoursWorked);  // Calculate daily pay

        WorkEntry newEntry = new WorkEntry(workDate, hoursWorked, dailyPay);  // Create WorkEntry
        workEntries.add(newEntry);  // Add to workEntries list
        //totalPay += dailyPay;  // Update total pay
        setTotalPay(dailyPay);
    }

    public ArrayList<WorkEntry> getWorkEntries() {
        return workEntries;
    }

    public void setWorkEntries(ArrayList<WorkEntry> workEntries) {
        this.workEntries = workEntries;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay += totalPay;
    }

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

    // Override toString to include work entries and total pay
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Detailed pay summary for %s:\n", getName()));
        result.append(String.format("Hourly Rate %.2f:\n", getHourlyRate()));
        result.append(String.format("%-12s %-16s %-14s %-12s\n", "Date", "Weekend/Holiday", "Hours Worked", "Pay ($)"));

        for (WorkEntry entry : getWorkEntries()) {
            String indicator = entry.isHolidayOrWeekend(Utilities.HOLIDAYS);
            result.append(String.format("%-12s %-16s %-14.2f %-12.2f\n",
                    entry.getWorkDate(),
                    indicator,
                    entry.getHoursWorked(),
                    entry.getDailyPay()));
        }

        // Add total pay at the end
        result.append(String.format("Total pay for %s: $%.2f", getName(), getTotalPay()));
        return result.toString();
    }
}
