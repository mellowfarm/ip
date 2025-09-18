package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

/**
 * Represents a task with specific start and end times.
 * Extends the base Task class with event scheduling and snooze functionality.
 */
public class Events extends Task {
    protected LocalDateTime start; // The start time of the event
    protected LocalDateTime end; // The end time of the event
    private static final DateTimeFormatter PRETTY = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    /**
     * Creates a new event task.
     *
     * @param description the event description
     * @param start the start time of the event
     * @param end the end time of the event
     */
    public Events(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a formatted string representation of the event task.
     *
     * @return string showing task status, description, and formatted time range
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.format(PRETTY) + " to: " + end.format(PRETTY) + ")";
    }

    /**
     * Returns the file storage format for this event task.
     *
     * @return pipe-separated string for file storage
     */
    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return String.format("E | %d | %s | %s | %s", status, description, start, end);
    }

    /**
     * Snoozes the event by the specified duration.
     * Moves both start and end times by the same duration to preserve event length.
     *
     * @param duration the time to postpone the event
     */
    @Override
    public void snooze(Duration duration) {
        this.start = this.start.plus(duration);
        this.end = this.end.plus(duration);
    }
}
