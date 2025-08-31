package bug;

import java.util.List;
import java.util.ArrayList;

/**
 * The TaskList class is responsible for managing a collection of tasks.
 * It provides methods to add, remove and access tasks in the list.
 * The class serves as a container for tasks and is used to interact with the task data in the application.
 * It supports operations such as retrieving a task by its index, adding tasks to the list, and removing tasks from the list.
 */
public class TaskList {

    private List<Task> tasks;

    /**
     * Creates a new TaskList with an empty list of tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a new TaskList with an existing list of tasks.
     *
     * @param init the list of tasks to initialise the TaskList with
     */
    public TaskList(List<Task> init) {
        this.tasks = new ArrayList<>(init);
    }

    /**
     * Returns the number of tasks currently in the list
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a task to the list of tasks.
     *
     * @param task the task to be added to the list
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the task at the specified index from the list.
     *
     * @param index the index of the task to be removed
     * @return the task that was removed
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index the index of the task to retrieve
     * @return the task at the specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

}
