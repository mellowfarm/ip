package command;

import exception.BugException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents the command to find tasks that match a given keyword.
 * This command searches for tasks whose description contains the specified keyword.
 */
public class FindCommand extends Command {

    private final String keyword;

    /**
     * Constructor for creating a FindCommand.
     *
     * @param keyword the keyword to search for in the task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the FindCommand by searching for tasks that match the provided keyword.
     * If the keyword is empty, a BugException is thrown.
     *
     * @param tasks the current list of tasks
     * @param ui the user interface used to display the result
     * @param storage the storage used to save the tasks (not used in this command)
     * @return a response message showing the tasks that match the keyword
     * @throws BugException if the keyword is empty or if an error occurs while searching
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        // Check if the keyword is empty and throw an exception if it is
        if (keyword.isEmpty()) {
            throw new BugException("What are you searching for?");
        }

        // Search for tasks that contain the keyword in their description
        ArrayList<Task> matches = tasks.findTasks(keyword);

        // Return the result to the user
        return ui.showFoundTasks(matches);
    }
}
