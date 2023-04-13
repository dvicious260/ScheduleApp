package bowden.scheduleapp.Helper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
}
