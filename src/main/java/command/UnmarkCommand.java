package command;

import exception.BugException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Command to mark a task as not completed.
 * Updates the task's completion status and saves changes to storage.
 */
public class UnmarkCommand extends Command {

    private final int index;

    /**
     * Creates a new unmark command for the specified task index.
     *
     * @param index the zero-based index of the task to mark as not done
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the unmark command by setting the task as not completed.
     *
     * @param tasks the task list containing the task to unmark
     * @param ui the user interface for displaying confirmation
     * @param storage the storage system for persisting changes
     * @return confirmation message showing the uncompleted task
     * @throws BugException if no tasks exist or the index is out of bounds
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (tasks.size() == 0) {
            throw new BugException("No tasks available to unmark!");
        }

        if (index < 0 || index >= tasks.size()) {
            throw new BugException("Task index " + (index + 1) + " is out of range! You have " + tasks.size() + " tasks.");
        }

        try {
            Task task = tasks.get(index);
            task.markAsUndone();
            storage.update(tasks);
            return ui.showUndone(task);
        } catch (Exception e) {
            throw new BugException("Could not unmark task at index " + (index + 1) + "!");
        }
    }
}
