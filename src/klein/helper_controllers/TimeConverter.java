package klein.helper_controllers;

import java.time.*;

public class TimeConverter {
    private final static ZonedDateTime estOpenDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,00), ZoneId.of("America/New_York"));
    private final static LocalDateTime localOpenDateTime = LocalDateTime.ofInstant(Instant.from(estOpenDateTime), ZoneId.systemDefault());
    private final static LocalTime localOpenTime = localOpenDateTime.toLocalTime();

    private final static ZonedDateTime estCloseDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22,00), ZoneId.of("America/New_York"));
    private final static LocalDateTime localCloseDateTime = LocalDateTime.ofInstant(Instant.from(estCloseDateTime), ZoneId.systemDefault());
    private final static LocalTime localCloseTime = localCloseDateTime.toLocalTime();

    public static ZonedDateTime getEstOpenDateTime() {
        return estOpenDateTime;
    }

    public static LocalDateTime getLocalOpenDateTime() {
        return localOpenDateTime;
    }

    public static LocalTime getLocalOpenTime() {
        return localOpenTime;
    }

    public static ZonedDateTime getEstCloseDateTime() {
        return estCloseDateTime;
    }

    public static LocalDateTime getLocalCloseDateTime() {
        return localCloseDateTime;
    }

    public static LocalTime getLocalCloseTime() {
        return localCloseTime;
    }
}
