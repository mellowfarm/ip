package bug;

public class ListCommand extends Command{

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (tasks.size() == 0) {
            throw new BugException(":(! list is empty!");
        }
        return ui.showList(tasks);
    }
}
