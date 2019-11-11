package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

//@@author talesrune
class TaskListTest {

    @Test
    void taskListTest() {
        TaskList items = new TaskList();
        Task task = new Todo("Hello");
        Task task2 = new Todo("world");
        Task task3 = new Todo("!");
        items.add(task);
        items.add(task2);
        items.add(task3);
        assertEquals("     1.[T]" + task.getStatusIconGui()
                + " Hello\n"
                + "     2.[T]" + task.getStatusIconGui()
                + " world\n"
                + "     3.[T]" + task.getStatusIconGui()
                + " !\n", items.getListGui());
    }
}