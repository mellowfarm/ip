package command;

import exception.BugException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Command to remove a task from the task list.
 * Validates the task index and removes the specified task.
 */
public class DeleteCommand extends Command {

    private final int index;

    /**
     * Creates a new delete command for the specified task index.
     *
     * @param index the zero-based index of the task to delete
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the delete command by removing the task at the specified index.
     *
     * @param tasks the task list to remove the task from
     * @param ui the user interface for displaying confirmation
     * @param storage the storage system for persisting changes
     * @return confirmation message showing the deleted task
     * @throws BugException if no tasks exist or the index is out of bounds
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
