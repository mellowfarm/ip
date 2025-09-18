package command;

import exception.BugException;
import storage.Storage;
import task.Task;
import task.TaskList;
import task.ToDos;
import ui.Ui;

/**
 * Command to create a simple todo task without any dates.
 * Validates the task description and adds the todo to the task list.
 */
public class TodoCommand extends Command {

    private final String description;

    /**
     * Creates a new todo command with the specified description.
     *
     * @param description the todo task description
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the todo command by creating and storing a new todo task.
     *
     * @param tasks the task list to add the todo to
     * @param ui the user interface for displaying confirmation
     * @param storage the storage system for persisting the task
     * @return confirmation message showing the created todo
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
