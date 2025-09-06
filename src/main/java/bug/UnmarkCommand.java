package bug;

public class UnmarkCommand extends Command {

    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        try {
            Task task = tasks.get(index);
            task.markAsUndone();
            storage.update(tasks);
            return ui.showUndone(task);
        } catch (Exception e) {
            throw new BugException(":(! no tasks at this index!");
        }
    }
}
