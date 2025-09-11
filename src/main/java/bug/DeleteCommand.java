package bug;

/**
 * Represents the command to delete a task from the task list.
 * This command removes a task based on the provided index and updates the task list and storage.
 */
public class DeleteCommand extends Command {

    private final int taskIndex;

    /**
     * Constructor for the DeleteCommand. Initializes the task index to delete.
     *
     * @param taskIndex the index of the task to delete
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
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
        try {
            // Attempt to delete the task at the given index
            Task task = tasks.delete(taskIndex); // Assuming delete() method handles invalid index internally
            storage.update(tasks); // Update storage after deletion
            return ui.showDeleted(task, tasks); // Show the deleted task in the UI
        } catch (Exception e) {
            // If the index is invalid, throw a custom BugException
            throw new BugException(":(! no tasks at this index!");
        }
    }
}
