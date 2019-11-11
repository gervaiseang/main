package duke.command;

import duke.storage.Storage;
import duke.task.BudgetList;
import duke.task.TaskList;
import duke.ui.Ui;

//@@author maxxyx96

/**
 * Represents a command to show the budget.
 */
public class ViewBudgetCommand extends Command {

    protected Ui ui = new Ui();
    protected BudgetList budgetList;

    /**
     * Command that allows the user to view the budget that he/she currently has.
     *
     * @param budgetList The list of budget that is stored by Duke Manager.
     */
    public ViewBudgetCommand(BudgetList budgetList) {
        this.budgetList = budgetList;
    }

    /**
     * Executes a command with task list and ui (GUI).
     * (not used)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui    To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return ui.showBudgetGui(budgetList.getBudget()) + "\n" + ui.showBudgetListGui(budgetList.getStringList());
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (not used)
     *
     * @param items   The task list that contains a list of tasks.
     * @param ui      To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {

    }
}
//@@author