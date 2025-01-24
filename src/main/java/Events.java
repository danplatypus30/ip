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

    public Events mark() {
        return new Events(this.description, true, this.from, this.to);
    }

    public Events unmark() {
        return new Events(this.description, false, this.from, this.to);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
