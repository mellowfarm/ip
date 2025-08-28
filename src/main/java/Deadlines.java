public class Deadlines extends Task{

    protected String by;

    public Deadlines(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + by + ")";
    }

    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return "D | " + status + " | " + getDescription() + " | " + by;
    }
}
