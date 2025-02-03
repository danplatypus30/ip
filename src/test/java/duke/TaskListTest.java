package duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void createTaskListTest(){
        TaskList tl = new TaskList();
        assertEquals(0, tl.size());
        tl = tl.add(new ToDos("huggies"));
        assertEquals(1, tl.size());
        assertEquals("[[T][ ] huggies]", tl.getList().toString());
    }
}
