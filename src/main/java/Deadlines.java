import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Deadlines extends Task{

    protected LocalDateTime by;
    private static final DateTimeFormatter PRETTY = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public Deadlines(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + by.format(PRETTY) + ")";
    }
}
