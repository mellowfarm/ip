package bug;

public class DeleteCommand extends Command {

    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        try {
            Task task = tasks.delete(index);
            storage.update(tasks);
            return ui.showDeleted(task, tasks);
        } catch (Exception e) {
            throw new BugException(":(! no tasks at this index!");
        }
    }
}
