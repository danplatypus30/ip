package duke;

import java.time.LocalDateTime;

/**
 * Represents an event task.
 */
public class Events extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructor for Events.
     * @param desc
     * @param from
     * @param to
     */
    public Events(String desc, String from, String to) {
        super(desc);
        this.from = convert(from);
        this.to = convert(to);
    }

    /**
     * Constructor for Events.
     * @param desc
     * @param from
     * @param to
     */
    public Events(String desc, LocalDateTime from, LocalDateTime to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructor for Events.
     * @param desc
     * @param isDone
     * @param from
     * @param to
     */
    public Events(String desc, boolean isDone, String from, String to) {
        super(desc, isDone);
        this.from = convert(from);
        this.to = convert(to);
    }

    /**
     * Constructor for Events.
     * @param desc
     * @param isDone
     * @param from
     * @param to
     */
    public Events(String desc, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(desc, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Mark an event as done.
     */
    @Override
    public Events mark() {
        return new Events(this.description, true, this.from, this.to);
    }

    /**
     * Unmark an event.
     */
    @Override
    public Events unmark() {
        return new Events(this.description, false, this.from, this.to);
    }

    /**
     * Convert the event to a string that can be saved to a file.
     */
    @Override
    public String toFileString() {
        int isMarked = 0;
        if (isDone() == true) {
            isMarked = 1;
        }
        return "E | " + isMarked + " | " + this.description + " | "
            + convertBack(this.from) + " | " + convertBack(this.to);
    }

    /**
     * Convert the event to a string that can be printed to the user.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + convertBack(this.from) + " to: " + convertBack(this.to) + ")";
    }
}
