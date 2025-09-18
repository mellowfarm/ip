package task;

import exception.BugException;

import java.time.Duration;

/**
 * Abstract base class representing a task in the Bug application.
 * Provides common functionality for task description, completion status, and basic operations.
 * Subclasses implement specific task types with additional features like dates and snoozing.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the specified description.
     * Task is initially marked as not completed.
     *
     * @param description the task description text
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the visual status indicator for the task.
     *
     * @return "X" if completed, " " if not completed
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the task description.
     *
     * @return the task's description text
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns a formatted string representation of the task.
     *
     * @return string showing completion status and description
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * Returns the file storage representation of the task.
     * Must be implemented by subclasses to define their storage format.
     *
     * @return pipe-separated string for file storage
     */
    public abstract String toFileString();

    /**
     * Snoozes the task by the specified duration.
     * Default implementation throws exception for tasks without dates (like todos).
     *
     * @param duration the time period to postpone the task
     * @throws BugException if the task type doesn't support snoozing
     */
    public void snooze(Duration duration) throws BugException {
        throw new BugException("Cannot snooze todos, they have no dates!");
    }
}
