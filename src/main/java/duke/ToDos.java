package duke;

/**
 * Represents a ToDos task.
 */
public class ToDos extends Task {
    /**
     * Constructor for ToDos.
     * @param desc
     */
    public ToDos(String desc) {
        super(desc);
    }

    /**
     * Constructor for ToDos.
     * @param desc
     * @param isDone
     */
    public ToDos(String desc, boolean isDone) {
        super(desc, isDone);
    }

    /**
     * Marks the ToDos task as done.
     */
    @Override
    public ToDos mark() {
        return new ToDos(this.description, true);
    }

    /**
     * Unmarks the ToDos task as done.
     */
    @Override
    public ToDos unmark() {
        return new ToDos(this.description, false);
    }

    /**
     * Returns the string representation of the ToDos task to be saved in the file.
     */
    @Override
    public String toFileString() {
        int isMarked = 0;
        if (isDone() == true) {
            isMarked = 1;
        }
        return "T | " + isMarked + " | " + this.description;
    }

    /**
     * Returns the string representation of the ToDos task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
