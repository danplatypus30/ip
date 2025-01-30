public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }


    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    public boolean isDone() {
        return isDone;
    }

    public Task mark() {
        return new Task(this.description, true);
    }

    public Task unmark() {
        return new Task(this.description, false);
    }

    public String toFileString() {
        int isMarked = 0;
        if (isDone() == true) {
            isMarked = 1;
        }
        return "T | " + isMarked + " | " + this.description;
    }
    
    @Override
    public String toString() {
        return this.getStatusIcon() + " " + description;
    }
}
