package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Command to exit the Bug application.
 * Displays a farewell message and signals application termination.
 */
public class ByeCommand extends Command {

    /**
     * Executes the bye command by showing a farewell message.
     *
     * @param tasks the task list (unused)
     * @param ui the user interface for displaying the farewell message
     * @param storage the storage system (unused)
     * @return the farewell message
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.showBye(); // Display the farewell message using the UI
    }

    /**
     * Indicates that this command terminates the application.
     *
     * @return true to signal application exit
     */
    @Override
    public boolean isExit() {
        return true; // The 'Bye' command triggers the exit process
    }
}
