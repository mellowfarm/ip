package command;

import exception.BugException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Represents the command to delete a task from the task list.
 * This command removes a task based on the provided index and updates the task list and storage.
 */
public class DeleteCommand extends Command {

    private final int index;

    /**
     * Constructor for the DeleteCommand. Initializes the task index to delete.
     *
     * @param index the index of the task to delete
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the DeleteCommand by removing the task at the given index,
     * updating the task list and storage, and displaying the result to the user.
     *
     * @param tasks the current list of tasks
     * @param ui the user interface used to display the response
     * @param storage the storage used to save the tasks
     * @return a response message showing the deleted task and updated list
     * @throws BugException if the index is invalid or there are no tasks at that index
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (tasks.size() == 0) {
            throw new BugException("No tasks available to delete!");
        }

        if (index < 0 || index >= tasks.size()) {
            throw new BugException("Task index " + (index + 1) + " is out of range! You have " + tasks.size() + " tasks.");
        }

        try {
            Task task = tasks.delete(index);
            storage.update(tasks);
            return ui.showDeleted(task, tasks);
        } catch (Exception e) {
            throw new BugException("Could not mark task at index " + (index + 1) + "!");
        }
    }
}
