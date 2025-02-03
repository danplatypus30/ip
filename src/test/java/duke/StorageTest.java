package duke;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    @Test
    public void fileIOTest() {
        // define file and dir paths
        String dir = "./data";
        String filepath = dir+ "/tasklist.txt";
        TaskList tl = new TaskList();
        Storage s = new Storage(dir, filepath, tl);
        try {
            s = s.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
