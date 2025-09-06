package bug;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineCommand extends Command{

    private final String desc;
    private final String by;
    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DeadlineCommand(String desc, String by) {
        this.desc = desc;
        this.by = by;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (desc.isEmpty()) {
            throw new BugException(":(! a deadline task cannot have an empty description!");
        }

        if (by.isEmpty()) {
            throw new BugException(":(! a deadline task must have a due date!");
        }

        try {
            LocalDate by2 = LocalDate.parse(by, INPUT_DT);
            Deadlines deadline = new Deadlines(desc, by2);
            tasks.add(deadline);
            storage.update(tasks);
            return ui.showDeadline(deadline, tasks);
        } catch (Exception e) {
            throw new BugException(":(! invalid date. use yyyy-MM-dd (eg 2005-11-27)!");
        }
    }
}
