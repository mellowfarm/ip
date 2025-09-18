package task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

/**
 * Represents a task with a specific deadline.
 * Extends the base Task class with due date functionality and snooze capability.
 */
public class Deadlines extends Task {

    protected LocalDate dueDate;
    private static final DateTimeFormatter PRETTY = DateTimeFormatter.ofPattern("dd MMM yyyy");

    /**
     * Creates a new deadline task.
     *
     * @param description the task description
     * @param dueDate the due date for the task
     */
    public Deadlines(String description, LocalDate dueDate) {
        super(description); // Initialize the description in the parent Task class
        this.dueDate = dueDate; // Set the due date for the deadline task
    }

    /**
     * Returns a formatted string representation of the deadline task.
     *
     * @return string showing task status, description, and formatted due date
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate.format(PRETTY) + ")";
    }

    /**
     * Returns the file storage format for this deadline task.
     *
     * @return pipe-separated string for file storage
     */
    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return String.format("D | %d | %s | %s", status, description, dueDate);
    }

    /**
     * Snoozes the deadline by the specified duration.
     * Converts duration to days and adds to the due date.
     *
     * @param duration the time to postpone the deadline
     */
    @Override
    public void snooze(Duration duration) {
        long totalHours = duration.toHours();
        long daysToAdd = totalHours / 24;

        // if there are remaining hours, add one more day to be safe
        if (totalHours % 24 > 0) {
            daysToAdd++;
        }

        this.dueDate = this.dueDate.plusDays(daysToAdd);
    }
}
