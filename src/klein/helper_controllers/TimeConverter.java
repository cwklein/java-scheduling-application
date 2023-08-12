package klein.helper_controllers;

import java.time.*;

public class TimeConverter {
    private final static ZonedDateTime estOpenDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));
    private final static LocalDateTime localOpenDateTime = LocalDateTime.ofInstant(Instant.from(estOpenDateTime), ZoneId.systemDefault());
    private final static LocalTime localOpenTime = localOpenDateTime.toLocalTime();

    private final static ZonedDateTime estCloseDateTime = estOpenDateTime.plusHours(14);
    private final static LocalDateTime localCloseDateTime = LocalDateTime.ofInstant(Instant.from(estCloseDateTime), ZoneId.systemDefault());
    private final static LocalTime localCloseTime = localCloseDateTime.toLocalTime();

    /**
     * Basic getter function for the private attribute 'estCloseDateTime'.
     * @return ZonedDateTime-type private attribute 'estCloseDateTime'.
     * */
    public static ZonedDateTime getEstOpenDateTime() {
        return estOpenDateTime;
    }

    /**
     * Basic getter function for the private attribute 'localOpenDateTime'.
     * @return LocalDateTime-type private attribute 'localOpenDateTime'.
     * */
    public static LocalDateTime getLocalOpenDateTime() {
        return localOpenDateTime;
    }

    /**
     * Basic getter function for the private attribute 'estCloseDateTime'.
     * @return ZonedDateTime-type private attribute 'estCloseDateTime'.
     * */
    public static ZonedDateTime getEstCloseDateTime() {
        return estCloseDateTime;
    }

    /**
     * Basic getter function for the private attribute 'localCloseDateTime'.
     * @return LocalDateTime-type private attribute 'localCloseDateTime'.
     * */
    public static LocalDateTime getLocalCloseDateTime() {
        return localCloseDateTime;
    }

    /**
     * Basic getter function for the private attribute 'localCloseTime'.
     * @return LocalTime-type private attribute 'localCloseTime'.
     * */
    public static LocalTime getLocalCloseTime() {
        return localCloseTime;
    }
}
