package command;

import exception.BugException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Command to display all tasks in the current task list.
 * Shows all tasks with their indices and completion status.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks the task list to display
     * @param ui the user interface for showing the task list
     * @param storage the storage system (unused)
     * @return formatted list of all tasks
     * @throws BugException if the task list is empty
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (tasks.size() == 0) {
            throw new BugException("List is empty!");
        }
        return ui.showList(tasks);
    }
}
