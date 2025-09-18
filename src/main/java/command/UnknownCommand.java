package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Command representing unrecognized or invalid user input.
 * Returns appropriate error messages for various types of input failures.
 */
public class UnknownCommand extends Command {

    private final String errorMessage;

    /**
     * Creates an unknown command with a default error message.
     */
    public UnknownCommand() {
        this.errorMessage = "I don't know what you mean! Please re-enter your task :)!";
    }

    /**
     * Creates an unknown command with a specific error message.
     *
     * @param errorMessage the custom error message to display
     */
    public UnknownCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Executes the unknown command by displaying the error message.
     *
     * @param tasks the task list (unused)
     * @param ui the user interface for displaying the error
     * @param storage the storage system (unused)
     * @return the error message explaining why the command failed
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.showError(errorMessage);
    }
}
