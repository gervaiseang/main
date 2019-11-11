package duke.parser;

import duke.command.DeleteCommand;
import duke.command.Command;
import duke.command.ListCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.AddCommand;

import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.BudgetList;
import duke.task.ContactList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author talesrune
class ParserTest {
    Ui ui = new Ui();
    TaskList items = new TaskList();
    BudgetList budgetList = new BudgetList();
    ContactList contactList = new ContactList();

    @Test
    void parserTest() throws Exception {
        Task task = new Todo("Hi");
        Task task2 = new Todo("there");
        items.add(task);
        items.add(task2);

        Command cmd = Parser.parse("todo Work", items, budgetList, contactList);
        assertTrue(cmd instanceof AddCommand);
        cmd = Parser.parse("deadline basketball /by 19/04/2019 1900", items, budgetList, contactList);
        assertTrue(cmd instanceof AddCommand);
        cmd = Parser.parse("deadline watch movies /by 20/07/2018 1240", items, budgetList, contactList);
        assertTrue(cmd instanceof AddCommand);
        cmd = Parser.parse("find word", items, budgetList, contactList);
        assertTrue(cmd instanceof FindCommand);
        cmd = Parser.parse("done 1", items, budgetList, contactList);
        assertTrue(cmd instanceof DoneCommand);
        cmd = Parser.parse("list", items, budgetList, contactList);
        assertTrue(cmd instanceof ListCommand);
        cmd = Parser.parse("delete 1", items, budgetList, contactList);
        assertTrue(cmd instanceof DeleteCommand);
    }

    @Test
    public void parserTest_exceptionThrown() {
        try {
            Parser.parse("invalid", items, budgetList, contactList);
            fail();
        } catch (Exception e) {
            assertEquals("     (>_<) OoPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        }
    }

    @Test
    void parserTest_DetectAnomalies() throws Exception {
        Task task = new Deadline("basketball", "19/04/2019 1900");
        items.add(task);
        Command cmd;
        try {
            Parser.parse("deadline soccer /by 19/04/2019 1900", items, budgetList, contactList);
            fail();
        } catch (Exception e) {
            assertEquals("     (>_<) OOPS!!! The date/time for deadline clashes with "
                    + "[D][X] basketball (by: 19th of April 2019, 7PM)\n"
                    + "     Please choose another date/time! Or mark the above task as Done first!", e.getMessage());
        }
        cmd = Parser.parse("deadline soccer /by 19/04/2019 2000", items, budgetList, contactList);
        assertTrue(cmd instanceof AddCommand);
        cmd = Parser.parse("done 1", items, budgetList, contactList);
        cmd.executeGui(items, ui);
        cmd = Parser.parse("deadline party /by 19/04/2019 1900", items, budgetList, contactList);
        assertTrue(cmd instanceof AddCommand);
    }
}
