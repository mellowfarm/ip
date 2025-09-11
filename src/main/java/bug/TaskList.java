package bug;

import java.util.List;
import java.util.ArrayList;

public class TaskList {

    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks != null : "task list should never be null after initialisation!";
    }

    public TaskList(List<Task> init) {
        assert init != null : "initial task list cannot be null!";
        this.tasks = new ArrayList<>(init);
        assert tasks != null : "task list should never be null after initialisation!";
    }

    public int size() {
        assert tasks != null : "task list should never be null!";
        return tasks.size();
    }

    public void add(Task task) {
        assert task != null : "cannot add null task to tak list!";
        assert tasks != null : "task list should never be null!";
        tasks.add(task);
    }

    public Task delete(int index) {
        assert tasks != null : "task list should never be null!";
        assert index >= 0 : "index cannot be negative!";
        assert index < tasks.size() : "index must be within task lost bounds!";
        return tasks.remove(index);
    }

    public Task get(int index) {
        assert tasks != null : "task list should never be null!";
        assert index >= 0 : "index cannot be negative!";
        assert index < tasks.size() : "index must be within task list bounds!";
        return tasks.get(index);
    }

    public ArrayList<Task> findTasks(String keyword) {
        assert keyword != null : "search keyword cannot be null!";
        assert tasks != null : "task list should never be null!";
        ArrayList<Task> matches = new ArrayList<>();
        for (Task t : tasks) {
            assert t != null : "task in list should never be null!";
            if (t.getDescription().contains(keyword)) {
                matches.add(t);
            }
        }
        return matches;
    }
}
