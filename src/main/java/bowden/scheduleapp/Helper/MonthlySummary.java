package bowden.scheduleapp.Helper;

public class MonthlySummary {
    private final String month;
    private final String appointmentType;
    private final int total;

    public MonthlySummary(String month, String appointmentType, int total) {
        this.month = month;
        this.appointmentType = appointmentType;
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public int getTotal() {
        return total;
    }
}
