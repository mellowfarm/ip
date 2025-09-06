package bug;

public class MarkCommand extends Command {

    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        try {
            Task task = tasks.get(index);
            task.markAsDone();
            storage.update(tasks);
            return ui.showDone(task);
        } catch (Exception e) {
            throw new BugException(":(! no tasks at this index!");
        }
    }
}
