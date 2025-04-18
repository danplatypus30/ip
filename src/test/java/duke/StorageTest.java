package duke;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * StorageTest class to test the Storage class.
 */
public class StorageTest {
    /**
     * Tests the fileIO method in the Storage class.
     */
    @Test
    public void fileIoTest() {
        // define file and dir paths
        String dir = "./data";
        String filepath = dir + "/tasklist.txt";
        TaskList tl = new TaskList();
        Storage s = new Storage(dir, filepath, tl);
        try {
            s = s.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail(e);
        }
    }
}
