public class ToDos extends Task {
    public ToDos(String desc) {
        super(desc);
    }

    public ToDos(String desc, boolean isDone) {
        super(desc, isDone);
    }

    public ToDos mark() {
        return new ToDos(this.description, true);
    }

    public ToDos unmark() {
        return new ToDos(this.description, false);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
