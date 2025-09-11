package bug;

/**
 * Represents an unknown command that is triggered when the user enters an unrecognized command.
 * This command returns an error message indicating that the input is invalid.
 */
public class UnknownCommand extends Command {

    /**
     * Executes the UnknownCommand, displaying an error message to the user when the input is unrecognized.
     *
     * @param tasks the current list of tasks (not used in this command)
     * @param ui the user interface used to display the error message
     * @param storage the storage used to save the tasks (not used in this command)
     * @return a response message indicating that the command was unrecognized
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.showError(":(! i don't know what you mean! please re-enter your task :)!");
    }
}
