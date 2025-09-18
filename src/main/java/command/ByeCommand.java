package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents the 'Bye' command, which is used to exit the task management application.
 * When executed, it displays a farewell message and indicates that the program should terminate.
 */
public class ByeCommand extends Command {

    /**
     * Executes the 'Bye' command by displaying a farewell message.
     * This method is called when the user wants to exit the application.
     *
     * @param tasks the current list of tasks (not used in this command)
     * @param ui the user interface responsible for displaying messages to the user
     * @param storage the storage system (not used in this command)
     * @return a farewell message to the user
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.showBye(); // Display the farewell message using the UI
    }

    /**
     * Returns true to indicate that this command causes the program to exit.
     * This method is used to signal the end of the program's execution.
     *
     * @return true, indicating that the program should terminate
     */
    @Override
    public boolean isExit() {
        return true; // The 'Bye' command triggers the exit process
    }
}
