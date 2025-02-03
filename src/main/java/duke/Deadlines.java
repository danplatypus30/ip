package duke;
import java.time.LocalDateTime;

/**
 * Represents a deadline task.
 */
public class Deadlines extends Task {
    protected LocalDateTime by;

    /**
     * Constructor for Deadlines.
     * @param desc
     * @param bystring
     */
    public Deadlines(String desc, String bystring) {
        super(desc);
        this.by = convert(bystring);
    }

    /**
     * Constructor for Deadlines.
     * @param desc
     * @param by
     */
    public Deadlines(String desc, LocalDateTime by) {
        super(desc);
        this.by = by;
    }

    /**
     * Constructor for Deadlines.
     * @param desc
     * @param isDone
     * @param bystring
     */
    public Deadlines(String desc, boolean isDone, String bystring) {
        super(desc, isDone);
        this.by = convert(bystring);
    }

    /**
     * Constructor for Deadlines.
     * @param desc
     * @param isDone
     * @param by
     */
    public Deadlines(String desc, boolean isDone, LocalDateTime by) {
        super(desc, isDone);
        this.by = by;
    }

    /**
     * Mark a Deadline as done.
     */
    @Override
    public Deadlines mark() {
        return new Deadlines(this.description, true, this.by);
    }

    /**
     * Unmark a Deadline as undone
     */
    @Override
    public Deadlines unmark() {
        return new Deadlines(this.description, false, this.by);
    }

    /**
     * Convert a Deadline to a string to be saved in a file.
     */
    @Override
    public String toFileString() {
        int isMarked = 0;
        if (isDone() == true) {
            isMarked = 1;
        }
        return "D | " + isMarked + " | " + this.description + " | "
            + convertBack(this.by);
    }

    /**
     * Convert a Deadline to a string to be displayed to the user.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + convertBack(this.by) + ")";
    }
}
