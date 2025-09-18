package command;

import exception.BugException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;

/**
 * Command to search for tasks containing a specific keyword.
 * Searches task descriptions and returns matching tasks.
 */
public class FindCommand extends Command {

    private final String keyword;

    /**
     * Creates a new find command with the specified search keyword.
     *
     * @param keyword the text to search for in task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks containing the keyword.
     *
     * @param tasks the task list to search through
     * @param ui the user interface for displaying search results
     * @param storage the storage system (unused)
     * @return message showing all matching tasks
     * @throws BugException if the keyword is empty
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
