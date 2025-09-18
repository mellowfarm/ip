package task;

import java.util.List;
import java.util.ArrayList;

/**
 * Manages a collection of tasks with operations for adding, removing, and searching.
 * Provides a wrapper around ArrayList with additional task-specific functionality.
 */
public class TaskList {

    private final List<Task> tasks;

    /**
     * Creates a new empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks != null : "task list should never be null after initialisation!";
    }

    /**
     * Creates a new task list initialized with the provided tasks.
     *
     * @param init the initial list of tasks to copy
     */
    public TaskList(List<Task> init) {
        assert init != null : "initial task list cannot be null!";
        this.tasks = new ArrayList<>(init);
        assert tasks != null : "task list should never be null after initialisation!";
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the current size of the task list
     */
    public int size() {
        assert tasks != null : "task list should never be null!";
        return tasks.size();
    }

    /**
     * Adds a new task to the end of the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        assert task != null : "cannot add null task to tak list!";
        assert tasks != null : "task list should never be null!";
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index the zero-based index of the task to remove
     * @return the removed task
     */
    public Task delete(int index) {
        assert tasks != null : "task list should never be null!";
        assert index >= 0 : "index cannot be negative!";
        assert index < tasks.size() : "index must be within task lost bounds!";
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index without removing it.
     *
     * @param index the zero-based index of the task to retrieve
     * @return the task at the specified index
     */
    public Task get(int index) {
        assert tasks != null : "task list should never be null!";
        assert index >= 0 : "index cannot be negative!";
        assert index < tasks.size() : "index must be within task list bounds!";
        return tasks.get(index);
    }

    /**
     * Finds all tasks whose descriptions contain the specified keyword.
     * Performs case-sensitive substring matching.
     *
     * @param keyword the text to search for in task descriptions
     * @return list of tasks containing the keyword (may be empty)
     */
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
