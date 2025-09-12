package bug;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

/**
 * Represents a deadline task in the task management application.
 * This class extends the Task class and includes a description of the task
 * and its due date.
 */
public class Deadlines extends Task {

    protected LocalDate dueDate;
    private static final DateTimeFormatter PRETTY = DateTimeFormatter.ofPattern("dd MMM yyyy");

    /**
     * Constructor for creating a new deadline task.
     *
     * @param description the description of the task
     * @param dueDate the due date of the task
     */
    public Deadlines(String description, LocalDate dueDate) {
        super(description); // Initialize the description in the parent Task class
        this.dueDate = dueDate; // Set the due date for the deadline task
    }

    /**
     * Returns a string representation of the deadline task, including its description
     * and due date formatted in a user-friendly way.
     *
     * @return the string representation of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate.format(PRETTY) + ")";
    }

    /**
     * Returns a string representation of the deadline task formatted for saving to a file.
     * The format includes the task's status, description, and due date.
     *
     * @return the string representation of the deadline task for file storage
     */
    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return String.format("D | %d | %s | %s", status, description, dueDate);
    }

    /**
     * Snoozes the deadline task by adding the given duration to the due date.
     * @param duration the duration to snooze the task by
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
