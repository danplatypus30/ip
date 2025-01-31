package duke;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    public TaskList(TaskList tlist) {
        this.list = tlist.list;
    }

    public ArrayList<Task> getList() {
        return list;
    }

    public int size() {
        return this.list.size();
    }

    public TaskList set(int index, Task elem) {
        this.list.set(index, elem);
        return this;
    }
    
    public TaskList add(Task t) {
        this.list.add(t);
        return this;
    }

    public TaskList remove(int n) {
        this.list.remove(n);
        return this;
    }

    public TaskList parseTaskLine(String line) throws UnexpectedException {
        // split the line using '|' as a delimiter and trim spaces
        String[] parts = line.split("\\|");
        if (parts.length > 5 || parts.length < 3) {
            throw new UnexpectedException("invalid task: " + line);
        }

        // extract variables
        char type = parts[0].trim().charAt(0); // T, D, or E
        boolean isMarked = parts[1].trim().equals("1"); // convert 1/0 to boolean
        String description = parts[2].trim();

        // add the Task obj to list
        if (type == 'T') {
            ToDos td = new ToDos(description, isMarked);
            list.add(td);
        } else if (type == 'D') {
            String by = parts[3].trim();
            Deadlines dd = new Deadlines(description, isMarked, by);
            list.add(dd);
        } else if (type == 'E') {
            String from = parts[3].trim();
            String to = parts[4].trim();
            Events ee = new Events(description, isMarked, from, to);
            list.add(ee);
        // ensure valid type 
        } else {
            throw new UnexpectedException("Unknown task type: " + type);
        }
        return new TaskList(list);
    }
}
