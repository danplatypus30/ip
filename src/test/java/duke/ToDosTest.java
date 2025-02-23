package duke;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test to make sure Todos print functions are working.
 */
public class ToDosTest {
    /**
     * Tests the toString method in the Todos class.
     */
    @Test
    public void toStringTest() {
        ToDos td = new ToDos("pat burd");
        assertEquals("[T][ ] pat burd", td.toString());
        assertEquals("T | 0 | pat burd", td.toFileString());
    }
}
