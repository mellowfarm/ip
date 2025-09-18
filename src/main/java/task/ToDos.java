package task;

/**
 * Represents a Todo task in the task management application.
 * This class extends the Task class and includes the description of the task.
 */
public class ToDos extends Task {

    /**
     * Constructor for creating a new Todo task with a given description.
     *
     * @param description the description of the Todo task
     */
    public ToDos(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the Todo task, including its description and status.
     *
     * @return the string representation of the Todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string representation of the Todo task formatted for saving to a file.
     * The format includes the task's status, description, and task type.
     *
     * @return the string representation of the Todo task for file storage
     */
    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return "T | " + status + " | " + getDescription();
    }
}
