package bug;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a command to create an event task.
 * This command parses the event's description, start time, and end time,
 * creates the corresponding `Events` object, and updates the task list and storage.
 */
public class EventCommand extends Command{

    private final String description;
    private final String startTime;
    private final String endTime;

    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Constructor for creating an EventCommand.
     *
     * @param description the description of the event
     * @param startTime the start time of the event in "yyyy-MM-dd HHmm" format
     * @param endTime the end time of the event in "yyyy-MM-dd HHmm" format
     */
    public EventCommand(String description, String startTime, String endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Executes the EventCommand by validating the input, parsing the start and end times,
     * creating a new `Events` task, and updating the task list and storage.
     *
     * @param tasks the current list of tasks
     * @param ui the user interface used to display the response
     * @param storage the storage used to save the tasks
     * @return a response message showing the created event task
     * @throws BugException if any input is invalid (e.g., empty fields or invalid date format)
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        // Validate the inputs
        if (description.isEmpty()) {
            throw new BugException("An event task cannot have an empty description!");
        }

        if (startTime.isEmpty()) {
            throw new BugException("An event task must have a start date!");
        }

        if (endTime.isEmpty()) {
            throw new BugException("An event task must have an end date!");
        }

        // Parse the start and end times
        try {
            LocalDateTime start2 = LocalDateTime.parse(startTime, INPUT_DT);
            LocalDateTime end2 = LocalDateTime.parse(endTime, INPUT_DT);
            Events event = new Events(description, start2, end2);
            tasks.add(event); // Add the event to the task list
            storage.update(tasks); // Update storage with the new task list
            return ui.showEvent(event, tasks); // Show the event in the UI
        } catch (Exception e) {
            throw new BugException("Invalid datetime. use yyyy-MM-dd HHmm (eg 2005-11-27 1800)!");
        }
    }
}
