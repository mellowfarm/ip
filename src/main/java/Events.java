import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Events extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;
    private static final DateTimeFormatter PRETTY = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    public Events(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.format(PRETTY) + " to: " + end.format(PRETTY) + ")";
    }

    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return String.format("E | %d | %s | %s | %s", status, description, start, end);
    }
}
