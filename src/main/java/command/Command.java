package command;

import exception.BugException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Abstract class representing a command in the task management application.
 * All specific commands (e.g., Add, Bye, List) will extend this class and define
 * the logic for executing the command.
 */
public abstract class Command {

    /**
     * Executes the command with the provided task list, user interface, and storage.
     * Each subclass of Command will implement its own specific behavior for executing the command.
     *
     * @param tasks the current list of tasks that the command can operate on
     * @param ui the user interface used to display output to the user
     * @param storage the storage system used to load or save tasks
     * @return a response string to be shown to the user after executing the command
     * @throws BugException if an error occurs while executing the command
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws BugException;

    /**
     * Determines if the command should cause the program to exit.
     * The default implementation returns false, but subclasses (e.g., ByeCommand)
     * can override this method to indicate that the command leads to an exit.
     *
     * @return true if the command should cause the program to exit; false otherwise
     */
    public boolean isExit() {
        return false; // Default behavior is that the command doesn't exit the application
    }
}
