package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class Task {

    protected String description;
    protected boolean isDone;

    public static LocalDateTime convert(String datestring) {
        // "Aug 16th 2025 4pm"; sample input

        // remove 'st', 'nd', 'rd', 'th' from the day
        datestring = datestring.replaceAll("(\\d+)(st|nd|rd|th)", "$1");

        // format DateTimeFormatter
        DateTimeFormatter formatter
                = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("MMM d yyyy ha")
                        .toFormatter(Locale.ENGLISH);

        // parse string into LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(datestring, formatter);
        // will throw DateTimeParseException if text cannot be parsed

        // System.out.println("Parsed LocalDateTime: " + dateTime);
        return dateTime;
    }

    private static String getOrdinalSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th"; // special case for 11-13

                }switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static String convertBack(LocalDateTime dateTime) {
        // define DateTimeFormatter (before ordinal suffix)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy ha", Locale.ENGLISH);

        // format to string
        String formattedDate = dateTime.format(formatter);

        // Convert AM/PM to lowercase
        formattedDate = formattedDate.replaceAll("AM", "am").replaceAll("PM", "pm");

        // append ordinal suffix
        int day = dateTime.getDayOfMonth();
        String dayWithSuffix = day + getOrdinalSuffix(day);

        // replace day in formatted date
        return formattedDate.replaceFirst("\\d+", dayWithSuffix);
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    public boolean isDone() {
        return isDone;
    }

    public Task mark() {
        return new Task(this.description, true);
    }

    public Task unmark() {
        return new Task(this.description, false);
    }

    public String toFileString() {
        int isMarked = 0;
        if (isDone() == true) {
            isMarked = 1;
        }
        return "T | " + isMarked + " | " + this.description;
    }

    @Override
    public String toString() {
        return this.getStatusIcon() + " " + description;
    }
}
