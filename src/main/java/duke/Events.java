package duke;

import java.time.LocalDateTime;

public class Events extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Events(String desc, String from, String to) {
        super(desc);
        this.from = convert(from);
        this.to = convert(to);
    }

    public Events(String desc, LocalDateTime from, LocalDateTime to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    public Events(String desc, boolean isDone, String from, String to) {
        super(desc, isDone);
        this.from = convert(from);
        this.to = convert(to);
    }

    public Events(String desc, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(desc, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public Events mark() {
        return new Events(this.description, true, this.from, this.to);
    }

    @Override
    public Events unmark() {
        return new Events(this.description, false, this.from, this.to);
    }

    @Override
    public String toFileString() {
        int isMarked = 0;
        if (isDone() == true) {
            isMarked = 1;
        }
        return "E | " + isMarked + " | " + this.description + " | "
            + convertBack(this.from) + " | " + convertBack(this.to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + convertBack(this.from) + " to: " + convertBack(this.to) + ")";
    }
}
