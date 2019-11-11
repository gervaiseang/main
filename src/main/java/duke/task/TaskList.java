package duke.task;

import duke.enums.Numbers;

import java.util.ArrayList;

//@@author talesrune
/**
 * Represents a task list that stores a list of tasks.
 */
public class TaskList {
    protected ArrayList<Task> items;

    /**
     * Creates an empty task list using an array list.
     */
    public TaskList() {
        items = new ArrayList<Task>();
    }

    /**
     * Creates an updated task list with the specified array list.
     *
     * @param items The updated array list.
     */
    public TaskList(ArrayList<Task> items) {
        this.items = items;
    }

    /**
     * To add a task into the task list.
     *
     * @param taskObj a task to be added.
     */
    public void add(Task taskObj) {
        items.add(taskObj);
    }

    /**
     * To remove a task from the task list.
     *
     * @param index The index of task to be removed.
     */
    public void remove(int index) {
        items.remove(index);
    }

    /**
     * To update a task with different type.
     *
     * @param index The index of task to be removed.
     * @param taskObj The index of task to be removed.
     */
    public void setTaskType(int index, Task taskObj) {
        items.set(index, taskObj);
    }

    /**
     * Get remaining tasks in the ArrayList.
     *
     * @return tasks that represents the items.
     */
    public ArrayList<Task> getTasks() {
        return items;
    }

    /**
     * The size of the task list.
     *
     * @return int that represents the task list size.
     */

    public int size() {
        return items.size();
    }

    /**
     * Retrieves the task from the task list via the task's index.
     *
     * @param index The index of task.
     * @return Task which is retrieved from the index.
     */
    public Task get(int index) {
        return items.get(index);
    }

    /**
     * Retrieves index of task in the task list (GUI).
     *
     * @param taskObj A task from the task list.
     * @return int that contains the index of task.
     */
    public int getIndex(Task taskObj) {
        int index = Numbers.MINUS_ONE.value;
        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            if (taskObj.equals(items.get(i))) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Retrieves all tasks from the task list (GUI).
     *
     * @return String that contains the whole list of tasks.
     */
    public String getListGui() {
        String listStr = "";
        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            listStr += "     " + (i + Numbers.ONE.value) + "." + items.get(i).toStringGui() + "\n";
        }
        return listStr;
    }
}