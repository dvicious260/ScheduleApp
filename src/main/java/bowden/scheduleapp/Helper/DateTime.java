package bowden.scheduleapp.Helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTime {
    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");
    private static final ZoneId LOCAL_ZONE_ID = ZoneId.systemDefault();

    public static ZonedDateTime convertToUTC(LocalDateTime localDateTime) {
        ZonedDateTime localZoneDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcDateTime = localZoneDateTime.withZoneSameInstant(utcZoneId);
        return utcDateTime;
    }

    public static LocalDateTime convertLocalToUTC(LocalDateTime localDateTime, ZoneId localZoneId) {
        // Convert the input LocalDateTime to an Instant in the local time zone
        Instant localInstant = localDateTime.atZone(localZoneId).toInstant();

        // Convert the local Instant to an Instant in UTC
        Instant utcInstant = localInstant.atZone(ZoneId.of("UTC")).toInstant();

        // Convert the UTC Instant to a LocalDateTime in the system default time zone
        return LocalDateTime.ofInstant(utcInstant, ZoneId.systemDefault());
    }

    public static LocalDateTime convertFromUTCtoLocal(Timestamp timestamp) {
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZoneId localZoneId = ZoneId.systemDefault();

        Instant instant = timestamp.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, utcZoneId);
        ZonedDateTime zonedDateTime = localDateTime.atZone(utcZoneId);
        ZonedDateTime convertedDateTime = zonedDateTime.withZoneSameInstant(localZoneId);

        return convertedDateTime.toLocalDateTime();
    }

    public static LocalDateTime convertToLocal(ZonedDateTime utcDateTime) {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localDateTime = utcDateTime.withZoneSameInstant(localZoneId);
        return localDateTime.toLocalDateTime();
    }

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

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    public static ObservableList<LocalTime> getBusinessHoursInTimeZone(ZoneId zoneId) {
        ObservableList<LocalTime> businessHours = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);
        LocalDate today = LocalDate.now(zoneId);
        while (start.isBefore(end)) {
            LocalDateTime local = LocalDateTime.of(today, start);
            ZonedDateTime zone = ZonedDateTime.of(local, zoneId);
            businessHours.add(zone.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime());
            start = start.plusMinutes(15);
        }
        return businessHours;
    }
}
