package bug;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventCommand extends Command{

    private final String desc;
    private final String start;
    private final String end;

    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public EventCommand(String desc, String start, String end) {
        this.desc = desc;
        this.start = start;
        this.end = end;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        if (desc.isEmpty()) {
            throw new BugException(":(! an event task cannot have an empty description!");
        }

        if (start.isEmpty()) {
            throw new BugException(":(! an event task must have a start date!");
        }

        if (end.isEmpty()) {
            throw new BugException(":(! an event task must have an end date!");
        }

        try {
            LocalDateTime start2 = LocalDateTime.parse(start, INPUT_DT);
            LocalDateTime end2 = LocalDateTime.parse(end, INPUT_DT);
            Events event = new Events(desc, start2, end2);
            tasks.add(event);
            storage.update(tasks);
            return ui.showEvent(event, tasks);
        } catch (Exception e) {
            throw new BugException(":(! invalid datetime. use yyyy-MM-dd HHmm (eg 2005-11-27 1800)!");
        }
    }
}
