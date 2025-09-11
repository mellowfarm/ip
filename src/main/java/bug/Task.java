package bug;

/**
 * Represents a task in the task management application.
 * A task has a description and a status indicating whether it's completed or not.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor to initialize a task with a description.
     * The task is initially set as not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing the task's completion status.
     *
     * @return "X" if the task is done, " " (empty space) if it's not done
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the description of the task.
     *
     * @return the task's description
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Marks the task as done.
     * This updates the task's status to "done".
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as undone.
     * This updates the task's status to "not done".
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task in the format "[status] description".
     *
     * @return a string that includes the status and the description
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * Returns a string representation of the task formatted for saving to a file.
     * Subclasses must implement this method to provide specific file string formats.
     *
     * @return a string that represents the task in a file-friendly format
     */
    public abstract String toFileString();
}
