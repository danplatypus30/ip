public class Deadlines extends Task {
    protected String by;
    public Deadlines(String desc, String by) {
        super(desc);
        this.by = by;
    }

    public Deadlines(String desc, boolean isDone, String by) {
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
        return "E | " + isMarked + " | " + this.description + " | "
            + this.by;
    }
    
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
