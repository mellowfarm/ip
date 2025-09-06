package bug;

public class TodoCommand extends Command {

    private final String desc;

    public TodoCommand(String desc) {
        this.desc = desc;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (desc.isEmpty()) {
            throw new BugException(":(! a todo task cannot have an empty description!");
        }
        Task todo = new ToDos(desc);
        tasks.add(todo);
        storage.update(tasks);
        return ui.showToDo(todo, tasks);
    }
}
