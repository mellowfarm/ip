package bug;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadlines extends Task {

    protected LocalDate by;
    private static final DateTimeFormatter PRETTY = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public Deadlines(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(PRETTY) + ")";
    }

    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return String.format("D | %d | %s | %s", status, description, by);
    }
}
