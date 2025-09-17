package bug;

/**
 * Represents the command to create a Todo task.
 * This command parses the description for the Todo task, creates the task, and updates the task list and storage.
 */
public class TodoCommand extends Command {

    private final String description;

    /**
     * Constructor for creating a TodoCommand with a description.
     *
     * @param description the description of the Todo task
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the TodoCommand by creating a new Todo task and adding it to the task list.
     * If the description is empty, a BugException is thrown.
     *
     * @param tasks the current list of tasks
     * @param ui the user interface used to display the result
     * @param storage the storage used to save the tasks
     * @return a response message showing the created Todo task
     * @throws BugException if the description is empty
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (description.isEmpty()) {
            throw new BugException("A todo task cannot have an empty description!");
        }
        Task todo = new ToDos(description);
        tasks.add(todo);
        storage.update(tasks);
        return ui.showToDo(todo, tasks);
    }
}
