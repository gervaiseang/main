package duke;

import duke.command.BackupCommand;
import duke.command.ExitCommand;
import duke.command.ListPriorityCommand;
import duke.command.Command;
import duke.command.AddMultipleCommand;
import duke.command.SetPriorityCommand;
import duke.command.DeleteCommand;
import duke.command.FilterCommand;
import duke.command.FindTasksByPriorityCommand;
import duke.dukeexception.DukeException;
import duke.parser.Parser;
import duke.storage.BudgetStorage;
import duke.storage.ContactStorage;
import duke.storage.PriorityStorage;
import duke.storage.Storage;
import duke.task.BudgetList;
import duke.task.PriorityList;
import duke.task.FilterList;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Represents a duke that controls the program.
 */
public class Duke {
    private Storage storage;
    private TaskList items;
    private FilterList filterList;
    private Ui ui;
    private ContactStorage contactStorage;
    private ContactList contactList;
    private DukeLogger dukeLogger;

    private PriorityStorage priorityStorage;
    private PriorityList priorityList;

    private BudgetStorage budgetStorage;
    private BudgetList budgetList;

    private static final String storageFilePath = "data";
    private static final String taskFilePath = "data/duke.txt";
    private static final String priorityFilePath = "data/priority.txt";
    private static final String budgetFilePath = "data/budget.txt";
    private static final String contactsFilePath = "data/contacts.txt";
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Creates a duke to initialize storage, task list, and ui.
     */
    public Duke() {
        initialize();
        dukeLogger.setupLogger();
        checkStorageExist();
        try {
            readStorage();
        } catch (IOException e) {
            ui.showLoadingError();
            logr.log(Level.SEVERE,"Storage text file is not found");
            createEmptyTaskList();
        }
        try {
            readPriorityStorage();
        } catch (IOException e) {
            ui.showLoadingError();
            logr.log(Level.SEVERE,"Priority storage text file is not found");
            createEmptyPriorityList();
        }
        try {
            readContactStorage();
        } catch (IOException e) {
            ui.showLoadingError();
            logr.log(Level.SEVERE,"Contact list text file is not found");
            createEmptyContactList();
        }
        try {
            readBudgetStorage();
        } catch (IOException e) {
            ui.showLoadingError();
            logr.log(Level.SEVERE,"Budget list text file is not found");
            createEmptyBudgetList();
        }
    }

    private void initialize() {
        dukeLogger = new DukeLogger();
        ui = new Ui();
        filterList = new FilterList();
        storage = new Storage(taskFilePath);
        priorityStorage = new PriorityStorage(priorityFilePath);
        contactStorage = new ContactStorage(contactsFilePath);
        budgetStorage = new BudgetStorage(budgetFilePath);
    }

    private void readStorage() throws IOException {
        items = new TaskList(storage.read());
    }

    private void readPriorityStorage() throws IOException {
        priorityList = new PriorityList(priorityStorage.read());
    }

    private void readContactStorage() throws IOException {
        contactList = new ContactList(contactStorage.read());
    }

    private void readBudgetStorage() throws IOException {
        budgetList = new BudgetList(budgetStorage.read());
    }

    private void createEmptyTaskList() {
        items = new TaskList();
    }

    private void createEmptyPriorityList() {
        priorityList = new PriorityList();
    }

    private void createEmptyContactList() {
        contactList = new ContactList();
    }

    private void createEmptyBudgetList() {
        budgetList = new BudgetList();
    }

    /**
     * Echoes the user input back the the user.
     * (Not in use)
     *
     * @param input The user input.
     * @return String of the response.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }

    //@@author maxxyx96
    /**
     * Creates a directory for data storage if there is none created yet.
     *
     */
    public void checkStorageExist() {
        File storageFileDirectory = new File(storageFilePath);
        if (!storageFileDirectory.exists()) {
            storageFileDirectory.mkdirs();
        }
    }

    /**
     * Gets the budget list from Duke.
     *
     * @return the budget List.
     */
    public BudgetList getBudgetList() {
        return budgetList;
    }
    //@@author

    //@@author e0318465
    /**
     * Retrieves the current contact list (GUI).
     *
     * @return A list of contacts.
     */
    public ContactList getContactList() {
        return contactList;
    }
    //@@author

    /**
     * Retrieves a command from interpreting the user input (GUI).
     *
     * @param sentence The user input.
     * @return Command to be executed thereafter.
     * @throws Exception  If there is an error reading the command.
     */
    public Command getCommand(String sentence) throws Exception {
        Command cmd = Parser.parse(sentence, items, budgetList, contactList);
        return cmd;
    }

    /**
     * Executes a command to overwrite existing storage with the current updated lists(GUI).
     *
     * @param cmd Command to be executed.
     * @throws IOException  If there is an error writing the text file.
     */
    public void saveState(Command cmd) throws IOException {
        cmd.executeStorage(items, ui, storage, budgetStorage, budgetList,
                contactStorage, contactList, priorityStorage, priorityList);
    }

    /**
     * Executes a command and outputs the result to the user (GUI).
     *
     * @param cmd Command to be executed.
     * @return String to be outputted.
     * @throws IOException  If there is an error writing the text file
     */
    public String executeCommand(Command cmd) throws IOException {
        String str = cmd.executeGui(items, ui);
        if (cmd instanceof FilterCommand) {
            cmd.execute(items,filterList);
        }
        return str;
    }

    //@@author talesrune
    /**
     * Retrieves the current task list (GUI).
     *
     * @return A list of tasks.
     */
    public TaskList getTaskList() {
        return items;
    }

    /**
     * Retrieves the current task list (GUI).
     *
     * @return A list of tasks.
     */
    public FilterList getFilterList() {
        return filterList;
    }

    //@@author
    /**
     * Runs the duke program until exit command is executed.
     */
    public void run() {
        ui.showWelcome();
        //Ui.showReminder(items);
        String sentence;

        while (true) {
            sentence = ui.readCommand();
            ui.showLine();
            try {
                Command cmd = Parser.parse(sentence, items, budgetList, contactList);
                if (cmd instanceof ExitCommand) {
                    priorityStorage.write(priorityList);
                    budgetStorage.write(budgetList);
                    contactStorage.write(contactList);
                    cmd.executeStorage(items, ui, storage);
                    break;
                } else if (cmd instanceof ListPriorityCommand
                        || cmd instanceof AddMultipleCommand
                        || cmd instanceof DeleteCommand
                        || cmd instanceof SetPriorityCommand
                        || cmd instanceof FindTasksByPriorityCommand) {
                    cmd.execute(items, priorityList, ui);
                } else if (cmd instanceof BackupCommand) {
                    priorityStorage.write(priorityList);
                    budgetStorage.write(budgetList);
                    contactStorage.write(contactList);
                    storage.write(items);
                    cmd.execute(items, ui);
                    cmd.executeStorage(items, ui, storage);
                } else {
                    cmd.execute(items,ui);
                    priorityList = priorityList.addDefaultPriority(cmd);
                }
            } catch (DukeException e) {
                ui.showErrorMsg(e.getMessage());
            } catch (Exception e) {
                ui.showErrorMsg("     New error, please fix:");
                logr.log(Level.WARNING,"New error, please fix", e);
                e.printStackTrace();
                ui.showErrorMsg("     Duke will continue as per normal.");
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}