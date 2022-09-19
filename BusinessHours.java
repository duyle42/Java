package JavaPractices;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

// Business hours from 8:00 AM - 5:00 PM or 17:00.
public class BusinessHours {

    public static LocalTime BUSINESS_START_TIME = LocalTime.parse("08:00:00");
    public static LocalTime BUSINESS_END_TIME = LocalTime.parse("17:00:00");
    public static Double BUSINESS_HOURS_ONE_DAY = 9.0;

    public static boolean isWeekend(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static Double getHoursBetweenTwoTimes(LocalTime startTime, LocalTime endTime) {
        long elapsedMinutes = Duration.between(startTime, endTime).toMillis();
        return (double) (elapsedMinutes / (1000 * 60 * 60.0));
    }

    public static Double getBusinessHoursBetweenTwoTimes(LocalTime startTime, LocalTime endTime) {
        if (startTime.compareTo(BUSINESS_START_TIME) >= 0
                && endTime.compareTo(BUSINESS_END_TIME) <= 0) {
            return getHoursBetweenTwoTimes(startTime, endTime);
        } else if (startTime.compareTo(BUSINESS_START_TIME) >= 0) {
            return getHoursBetweenTwoTimes(startTime, BUSINESS_END_TIME);
        } else if (endTime.compareTo(BUSINESS_END_TIME) <= 0) {
            return getHoursBetweenTwoTimes(BUSINESS_START_TIME, endTime);
        }
        return BUSINESS_HOURS_ONE_DAY;
    }

    public static LocalDateTime getNextDateTime(LocalDateTime localDateTime) {
        LocalDate nextDate = localDateTime.plusDays(1).toLocalDate();
        LocalDateTime nextDateTime = LocalDateTime.of(nextDate, LocalTime.of(0, 0, 0));
        return nextDateTime;
    }

    public static LocalDateTime getPreviousDateTime(LocalDateTime localDateTime) {
        LocalDate previousDate = localDateTime.plusDays(-1).toLocalDate();
        LocalDateTime previousDateTime = LocalDateTime.of(previousDate, LocalTime.of(0, 0, 0));
        return previousDateTime;
    }

    public static boolean isNextDay(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        return startDate.plusDays(1).equals(endDate);
    }

    public static boolean isEqualDay(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        return startDate.equals(endDate);
    }

    public static Double getBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        System.out.println(startDateTime + " " + endDateTime);
        Double totalBusinessHours = 0.0;
        if (startDateTime == null || endDateTime == null
                || endDateTime.toEpochSecond(ZoneOffset.UTC) - startDateTime.toEpochSecond(ZoneOffset.UTC) <= 0) {
            return totalBusinessHours;
        }

        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();
        if (isEqualDay(startDateTime, endDateTime)) {
            if (!isWeekend(startDateTime)) {
                totalBusinessHours += getBusinessHoursBetweenTwoTimes(startTime, endTime);
            }
            return totalBusinessHours;
        }

        totalBusinessHours += getBusinessHoursBetweenTwoTimes(startTime, BUSINESS_END_TIME);
        totalBusinessHours += getBusinessHoursBetweenTwoTimes(BUSINESS_START_TIME, endTime);

        if (!isNextDay(startDateTime, endDateTime)) {
            startDateTime = getNextDateTime(startDateTime);
            while (!isEqualDay(startDateTime, endDateTime)) {
                totalBusinessHours += BUSINESS_HOURS_ONE_DAY;
                startDateTime = getNextDateTime(startDateTime);
            }
        }

        return totalBusinessHours;
    }

    public static void main(String args[]) {
        System.out.println(getBusinessHours(LocalDateTime.parse("2022-09-19T11:00:00.000"),
                LocalDateTime.parse("2022-09-19T15:00:00.000")));
        System.out.println(getBusinessHours(LocalDateTime.parse("2022-09-19T06:00:00.000"),
                LocalDateTime.parse("2022-09-19T15:00:00.000")));
        System.out.println(getBusinessHours(LocalDateTime.parse("2022-09-19T11:00:00.000"),
                LocalDateTime.parse("2022-09-19T21:00:00.000")));
        System.out.println(getBusinessHours(LocalDateTime.parse("2022-09-19T06:00:00.000"),
                LocalDateTime.parse("2022-09-19T20:00:00.000")));

        System.out.println(getBusinessHours(LocalDateTime.parse("2022-09-18T11:00:00.000"),
                LocalDateTime.parse("2022-09-19T15:00:00.000")));
        System.out.println(getBusinessHours(LocalDateTime.parse("2022-09-17T06:00:00.000"),
                LocalDateTime.parse("2022-09-19T15:00:00.000")));
    }
}