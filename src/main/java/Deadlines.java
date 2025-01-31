import java.time.LocalDateTime;

public class Deadlines extends Task {
    protected LocalDateTime by;

    public Deadlines(String desc, String bystring) {
        super(desc);
        this.by = convert(bystring);
    }

    public Deadlines(String desc, LocalDateTime by) {
        super(desc);
        this.by = by;
    }

    public Deadlines(String desc, boolean isDone, String bystring) {
        super(desc, isDone);
        this.by = convert(bystring);
    }

    public Deadlines(String desc, boolean isDone, LocalDateTime by) {
        super(desc, isDone);
        this.by = by;
    }

    @Override
    public Deadlines mark() {
        return new Deadlines(this.description, true, this.by);
    }

    @Override
    public Deadlines unmark() {
        return new Deadlines(this.description, false, this.by);
    }

    @Override
    public String toFileString() {
        int isMarked = 0;
        if (isDone() == true) {
            isMarked = 1;
        }
        return "D | " + isMarked + " | " + this.description + " | "
            + convertBack(this.by);
    }
    
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + convertBack(this.by) + ")";
    }
}
