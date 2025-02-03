package duke;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.Scanner;

/**
 * Storage class is responsible for reading and writing to the file.
 * It reads the file and parses the data into Task object.
 * It also writes the Task object into the file.
 */
public class Storage {
    private String dir;
    private String filepath;
    private TaskList list;

    /**
     * Constructor for Storage class
     * @param dir
     * @param filepath
     * @param list
     */
    public Storage(String dir, String filepath, TaskList list) {
        this.dir = dir;
        this.filepath = filepath;
        this.list = list;
    }

    /**
     * Getter for TaskList
     * @return
     */
    public TaskList getList() {
        return this.list;
    }

    /**
     * Executes the file operations required for reading and initializing the application's task storage.
     * This method performs the following operations:
     * - Creates the directory if it does not already exist.
     * - Creates a file within the directory if the file does not already exist.
     * - Reads the contents of the file and parses each line into Task objects to populate the TaskList.
     *
     * @return a new Storage instance initialized with the directory path, file path, and populated TaskList.
     * @throws IOException if the directory cannot be created, the file cannot be created,
     *                     the file cannot be read, or an unexpected error occurs during parsing.
     */
    public Storage run() throws IOException {
        // create dir if it does not exist
        File directory = new File(dir);
        if (directory.exists() == false) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + dir);
            } else {
                //System.out.println("Failed to create directory: " + dir);
                throw new IOException("Failed to create directory: " + dir); // exit if create dir fails
            }
        }

        // create file obj, dir should exist
        File file = new File(filepath);

        // create file if file does not exist
        if (file.exists() == false) {
            try {
                if (file.createNewFile()) {
                    System.out.println("file created: " + filepath);
                } else {
                    System.out.println("file already exists: " + filepath);
                }
            } catch (IOException e) {
                throw e;
            }
        }

        // read the file contents
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                // parse the data into Task object
                String line = scanner.nextLine();
                list = list.parseTaskLine(line); // arraylist is passed by reference
            }
        } catch (FileNotFoundException e) {
            throw new IOException("file not found: " + file.getAbsolutePath());
        } catch (UnexpectedException e) {
            throw new IOException(e.getMessage());
        }
        return new Storage(dir, filepath, list);
    }

    /**
     * Writes the TaskList object into the file.
     * @param list
     * @throws IOException
     */
    public static void writeListToFile(TaskList list) throws IOException {
        try (FileWriter writer = new FileWriter("./data/tasklist.txt")) {
            for (Task tasks : list.getList()) {
                writer.write(tasks.toFileString() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("unable to update file, error...");
            throw e;
        }
    }
}
