package bug;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a list of tasks in the task management application.
 * This class provides methods for adding, removing, and finding tasks in the list.
 */
public class TaskList {

    private final List<Task> tasks;

    /**
     * Constructor to initialize an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor to initialize the task list with a given list of tasks.
     *
     * @param init the initial list of tasks
     */
    public TaskList(List<Task> init) {
        this.tasks = new ArrayList<>(init);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task the task to be added
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task at the specified index from the task list.
     *
     * @param index the index of the task to delete
     * @return the task that was removed
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index the index of the task to retrieve
     * @return the task at the specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Finds tasks whose description contains the specified keyword.
     *
     * @param keyword the keyword to search for in the task descriptions
     * @return a list of tasks that contain the keyword in their description
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().contains(keyword)) {
                matches.add(t);
            }
        }
        return matches;
    }
}
