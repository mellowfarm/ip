package task;

/**
 * Represents a simple todo task without any associated dates.
 * Extends the base Task class with todo-specific display formatting.
 * Cannot be snoozed since it has no deadline or schedule.
 */
public class ToDos extends Task {

    /**
     * Creates a new todo task.
     *
     * @param description the todo task description
     */
    public ToDos(String description) {
        super(description);
    }

    /**
     * Returns a formatted string representation of the todo task.
     *
     * @return string showing "[T]" prefix with task status and description
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the file storage format for this todo task.
     *
     * @return pipe-separated string: "T | status | description"
     */
    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return "T | " + status + " | " + getDescription();
    }
}
