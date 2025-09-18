package command;

import exception.BugException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Command to mark a task as completed.
 * Updates the task's completion status and saves changes to storage.
 */
public class MarkCommand extends Command {

    private final int index;

    /**
     * Creates a new mark command for the specified task index.
     *
     * @param index the zero-based index of the task to mark as done
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the mark command by setting the task as completed.
     *
     * @param tasks the task list containing the task to mark
     * @param ui the user interface for displaying confirmation
     * @param storage the storage system for persisting changes
     * @return confirmation message showing the completed task
     * @throws BugException if no tasks exist, index is out of bounds, or storage fails
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        validateTaskExists(tasks);
        validateIndex(tasks);

        Task task = tasks.get(index);
        task.markAsDone();
        storage.update(tasks);
        return ui.showDone(task);
    }

    /**
     * Validates that tasks exist in the list.
     *
     * @param tasks the task list to validate
     * @throws BugException if no tasks exist
     */
    private void validateTaskExists(TaskList tasks) throws BugException {
        if (tasks.size() == 0) {
            throw new BugException("No tasks available to mark!");
        }
    }

    /**
     * Validates that the index is within bounds.
     *
     * @param tasks the task list to check bounds against
     * @throws BugException if index is out of bounds
     */
    private void validateIndex(TaskList tasks) throws BugException {
        if (index < 0 || index >= tasks.size()) {
            throw new BugException("Task index " + (index + 1) + " is out of range! You have " + tasks.size() + " tasks.");
        }
    }
}