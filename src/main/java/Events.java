public class Events extends Task {
    protected String from;
    protected String to;

    public Events(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    public Events(String desc, boolean isDone, String from, String to) {
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
            + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
