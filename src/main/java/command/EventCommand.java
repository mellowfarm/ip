package command;

import exception.BugException;
import storage.Storage;
import task.Events;
import task.TaskList;
import ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Command to create an event task with start and end times.
 * Parses task description and time range, then adds the event to the list.
 */
public class EventCommand extends Command {

    private final String description;
    private final String startTime;
    private final String endTime;

    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Creates a new event command.
     *
     * @param description the event description
     * @param startTime the start time in yyyy-MM-dd HHmm format
     * @param endTime the end time in yyyy-MM-dd HHmm format
     */
    public EventCommand(String description, String startTime, String endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Executes the event command by creating and storing a new event task.
     *
     * @param tasks the task list to add the event to
     * @param ui the user interface for displaying confirmation
     * @param storage the storage system for persisting the task
     * @return confirmation message showing the created event
     * @throws BugException if any field is empty or datetime format is invalid
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
