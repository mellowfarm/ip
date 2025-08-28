public class ToDos extends Task {

    public ToDos(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        int status = isDone ? 1 : 0;
        return "T | " + status + " | " + getDescription();
    }
}
