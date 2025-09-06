package bug;

import java.util.ArrayList;

public class FindCommand extends Command{

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (keyword.isEmpty()) {
            throw new BugException(":(! what are you searching for?");
        }
        ArrayList<Task> matches = tasks.findTasks(keyword);
        return ui.showFoundTasks(matches);
    }
}
