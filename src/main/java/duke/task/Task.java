package duke.task;

import duke.dukeexception.DukeException;
import duke.enums.Numbers;

//@@author talesrune
/**
 * Represents a task that stores description and boolean that indicates the task as completed.
 */
public class Task {

    protected String description;
    protected String notesDesc = "empty";
    protected boolean isDone;
    public int numberOfDays = Numbers.ZERO.value;
    public int priority;

    /**
     * Creates a task with the specified description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of a task.
     *
     * @return String of the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of a task.
     *
     * @param description String of the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the notes description of a task.
     *
     * @param notesDesc String of the description.
     */
    public void setNotes(String notesDesc) {
        this.notesDesc = notesDesc;
    }

    /**
     * Deletes the notes description of a task.
     *
     */
    public void deleteNotes() {
        this.notesDesc = "empty";
    }

    /**
     * Sets the notes description of a task.
     *
     * @return String of the notes description.
     */
    public String getNotes() {
        return notesDesc;
    }

    /**
     * Returns a boolean of the task being done or not.
     *
     * @return Boolean of isDone.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the status icon of a task.
     *
     * @return String of the status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "[/]" : "[X]");
    }

    /**
     * Sets the status icon of a task to true/false.
     *
     * @param setDone The boolean of the task.
     */
    public void setStatusIcon(boolean setDone) {
        isDone = setDone;
    }

    /**
     * Returns the status icon of a task (GUI).
     *
     * @return String of the status icon.
     */
    public String getStatusIconGui() {
        return (isDone ? "[\u2713]" : "[\u2718]"); //To pass checkstyle
    }

    /**
     * Returns the status icon of a task (GUI).
     *
     * @return String of the status icon.
     */
    public String getTasks() {
        return (isDone ? "[\u2713]" : "[\u2718]"); //To pass checkstyle
    }

    //@@author
    /**
     * Set the priority of one task.
     *
     * @param  priorityLevel The priority level of the task, 1 to 5, high to low.
     */
    public void setPriority(int priorityLevel) {
        this.priority = priorityLevel;
    }

    /**
     * Set the priority of one task.
     *
     * @return the priority of the task.
     */
    public int getPriority() {
        return this.priority;
    }

    //@@author talesrune
    /**
     * Extracting a task content into readable string.
     *
     * @return String that contains the status and the description of the task.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    /**
     * Extracting a task content into readable string (GUI).
     *
     * @return String that contains the status and the description of the task.
     */
    public String toStringGui() {
        return getStatusIconGui() + " " + description;
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String that contains the status and the description of the task.
     */
    public String toFile() {
        String numStr = "";
        if (isDone) {
            numStr = "1|";
        } else {
            numStr = "0|";
        }
        return  numStr + description + "|" + notesDesc;
    }

    /**
     * Retrieves the date of the task as a String format.
     *
     * @return String of Date (Only in Deadline and Event)
     */
    public String getDateTime() {
        return null;
    }

    /**
     * Set the date of the task.
     *
     * @param dateTime String of the date/time.
     * @throws Exception  If there is an error interpreting the user input
     */
    public void setDateTime(String dateTime) throws Exception {
        throw new DukeException("     Error! This task does not have date/time.");
    }

    //@@author
    /**
     * Checks if the input and description matches.
     *
     * @param arr1 Input from user.
     * @return Boolean that states if the input is a duplicate.
     */
    public boolean isContain(String arr1) {
        return arr1.equalsIgnoreCase(description);

    }
}