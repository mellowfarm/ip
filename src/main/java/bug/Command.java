package bug;

public abstract class Command {
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws BugException;

    public boolean isExit() {
        return false;
    }
}
