package command;

import exception.BugException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Represents the command to mark a task as done.
 * This command marks a specific task in the task list as done based on the given index.
 */
public class MarkCommand extends Command {

    private final int index;

    /**
     * Constructor for creating a MarkCommand to mark a task as done at the specified index.
     *
     * @param index the index of the task to mark as done
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the MarkCommand by marking the task at the specified index as done.
     * If the task index is invalid (out of bounds), a BugException is thrown.
     *
     * @param tasks the current list of tasks
     * @param ui the user interface used to display the result
     * @param storage the storage used to save the tasks
     * @return a response message showing the task that was marked as done
     * @throws BugException if the task index is invalid or out of bounds
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (tasks.size() == 0) {
            throw new BugException("No tasks available to mark!");
        }

        if (index < 0 || index >= tasks.size()) {
            throw new BugException("Task index " + (index + 1) + " is out of range! You have " + tasks.size() + " tasks.");
        }

        try {
            Task task = tasks.get(index);
            task.markAsDone();
            storage.update(tasks);
            return ui.showDone(task);
        } catch (Exception e) {
            throw new BugException("Could not mark task at index " + (index + 1) + "!");
        }
    }
}
