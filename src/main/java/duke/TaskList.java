package duke;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> list;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.list = new ArrayList<Task>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param list The list of tasks.
     */
    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    /**
     * Constructs a TaskList with the same tasks as the given TaskList.
     *
     * @param tlist The TaskList to copy.
     */
    public TaskList(TaskList tlist) {
        this.list = tlist.list;
    }

    /**
     * Returns an ArrayList of tasks.
     * @return
     */
    public ArrayList<Task> getList() {
        return list;
    }

    /**
     * Returns the size of the ArrayList.
     * @return
     */
    public int size() {
        return this.list.size();
    }

    /**
     * Inserts a task at the specified index.
     * @param index
     * @param elem
     * @return
     */
    public TaskList set(int index, Task elem) {
        this.list.set(index, elem);
        return this;
    }
    
    /**
     * Adds a task to the list.
     * @param t
     * @return
     */
    public TaskList add(Task t) {
        this.list.add(t);
        return this;
    }

    /**
     * Removes a task from the list.
     * @param n
     * @return
     */
    public TaskList remove(int n) {
        this.list.remove(n);
        return this;
    }

    /**
     * Converts a string to a TaskList.
     * @param line
     * @return
     * @throws UnexpectedException
     */
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
