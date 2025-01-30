public class ToDos extends Task {
    public ToDos(String desc) {
        super(desc);
    }

    public ToDos(String desc, boolean isDone) {
        super(desc, isDone);
    }

    @Override
    public ToDos mark() {
        return new ToDos(this.description, true);
    }

    @Override
    public ToDos unmark() {
        return new ToDos(this.description, false);
    }

    @Override
    public String toFileString() {
        int isMarked = 0;
        if (isDone() == true) {
            isMarked = 1;
        }
        return "T | " + isMarked + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
