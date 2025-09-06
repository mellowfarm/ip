package bug;

public class UnknownCommand extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.showError(":(! i don't know what you mean! please re-enter your task :)!");
    }
}
