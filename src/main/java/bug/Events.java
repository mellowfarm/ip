package bug;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task in the task management application.
 * This class extends the Task class and includes the description of the task,
 * as well as the start and end times of the event.
 */
public class Events extends Task {
    protected LocalDateTime start; // The start time of the event
    protected LocalDateTime end; // The end time of the event
    private static final DateTimeFormatter PRETTY = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    /**
     * Constructor for creating an event task.
     *
     * @param description the description of the event
     * @param start the start time of the event
     * @param end the end time of the event
     */
    public Events(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string representation of the event task, including its description,
     * start time, and end time, formatted in a user-friendly way.
     *
     * @return the string representation of the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.format(PRETTY) + " to: " + end.format(PRETTY) + ")";
    }

    /**
     * Returns a string representation of the event task formatted for saving to a file.
     * The format includes the task's status, description, start time, and end time.
     *
     * @return the string representation of the event task for file storage
     */
    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return String.format("E | %d | %s | %s | %s", status, description, start, end);
    }
}
