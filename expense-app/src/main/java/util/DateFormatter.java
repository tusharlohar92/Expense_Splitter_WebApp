package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Prevent instantiation
    private DateFormatter() {}

    public static String format(LocalDateTime date) {
        return date.format(formatter);
    }

    public static LocalDateTime parse(String dateString) {
        return LocalDateTime.parse(dateString, formatter);
    }

    public static String currentDateTimeFormatted() {
        return format(LocalDateTime.now());
    }
}