package bowden.scheduleapp.Helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.*;

public class DateTime {
    public static Timestamp getCurrentTimeStamp() {
        ZoneId zoneID = ZoneId.of("UTC");
        LocalDateTime localDateTime = LocalDateTime.now(zoneID);
        Timestamp currentTimestamp = Timestamp.valueOf(localDateTime);
        return currentTimestamp;
    }

    public static LocalDate getCurrentLocalDate() {
        LocalDate localDate = LocalDate.now();
        return localDate;
    }

    public static ObservableList<LocalTime> getBusinessHours() {
        ObservableList<LocalTime> businessHours = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);
        ZoneId EasternST = ZoneId.of("America/New_York");
        LocalDate today = LocalDate.now();
        while (start.isBefore(end)) {
            LocalDateTime local = LocalDateTime.of(today, start);
            ZonedDateTime zone = ZonedDateTime.of(local, EasternST);
            businessHours.add(zone.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime());
            start = start.plusMinutes(15);
        }
        return businessHours;
    }
}
