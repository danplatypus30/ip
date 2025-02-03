package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * Represents a task in the task list.
 */
public class Task {

    protected String description;
    protected boolean isDone;

    /**
     * Constructor for Task.
     * @param description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructor for Task.
     * @param description
     * @param isDone
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Converts a string to a LocalDateTime object.
     * @param datestring
     * @return
     */
    public static LocalDateTime convert(String datestring) {
        // "Aug 16th 2025 4pm"; sample input

        // remove 'st', 'nd', 'rd', 'th' from the day
        datestring = datestring.replaceAll("(\\d+)(st|nd|rd|th)", "$1");

        // format DateTimeFormatter
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("MMM d yyyy ha")
                        .toFormatter(Locale.ENGLISH);

        // parse string into LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(datestring, formatter);
        // will throw DateTimeParseException if text cannot be parsed

        // System.out.println("Parsed LocalDateTime: " + dateTime);
        return dateTime;
    }

    /**
     * Returns the ordinal suffix of a day.
     * @param day
     * @return
     */
    private static String getOrdinalSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th"; // special case for 11-13
        }
        switch (day % 10) {
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

    /**
     * Converts a LocalDateTime object to a string.
     * @param dateTime
     * @return
     */
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

    /**
     * Returns the description of the task.
     * @return
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    /**
     * Returns if the task is done.
     * @return
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as done.
     * @return
     */
    public Task mark() {
        return new Task(this.description, true);
    }

    /**
     * Unmarks the task.
     * @return
     */
    public Task unmark() {
        return new Task(this.description, false);
    }

    /**
     * Returns the description of the task to be written to a file.
     * @return
     */
    public String toFileString() {
        int isMarked = 0;
        if (isDone() == true) {
            isMarked = 1;
        }
        return "T | " + isMarked + " | " + this.description;
    }

    /**
     * Returns the description of the task for the user to read.
     */
    @Override
    public String toString() {
        return this.getStatusIcon() + " " + description;
    }
}
