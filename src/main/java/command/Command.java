package command;

import exception.BugException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Abstract base class for all commands in the Bug application.
 * Defines the contract for command execution and exit behavior.
 * Implements the Command pattern for handling user actions.
 */
public abstract class Command {

    /**
     * Executes the command with the given application components.
     *
     * @param tasks the task list to operate on
     * @param ui the user interface for displaying messages
     * @param storage the storage system for persisting changes
     * @return the response message to display to the user
     * @throws BugException if the command execution fails
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws BugException;

    /**
     * Indicates whether this command should terminate the application.
     *
     * @return true if the application should exit after this command
     */
    public boolean isExit() {
        return false; // Default behavior is that the command doesn't exit the application
    }
}
