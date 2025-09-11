package bug;

/**
 * Represents the command to list all tasks in the task management application.
 * This command displays the tasks from the current task list if there are any tasks.
 * If the task list is empty, an exception is thrown.
 */
public class ListCommand extends Command{

    /**
     * Executes the ListCommand by displaying all tasks in the task list.
     * If the task list is empty, a BugException is thrown.
     *
     * @param tasks the current list of tasks
     * @param ui the user interface used to display the list
     * @param storage the storage used to save the tasks (not used in this command)
     * @return a response message showing the list of tasks
     * @throws BugException if the task list is empty
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (tasks.size() == 0) {
            throw new BugException(":(! list is empty!");
        }
        return ui.showList(tasks);
    }
}
