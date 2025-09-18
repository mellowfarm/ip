package command;

import exception.BugException;
import storage.Storage;
import task.Deadlines;
import task.TaskList;
import ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Command to create a deadline task with a specific due date.
 * Parses task description and due date, then adds the task to the list.
 */
public class DeadlineCommand extends Command {

    private final String description;
    private final String dueDate;
    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Creates a new deadline command.
     *
     * @param description the task description
     * @param dueDate the due date in yyyy-MM-dd format
     */
    public DeadlineCommand(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    /**
     * Executes the deadline command by creating and storing a new deadline task.
     *
     * @param tasks the task list to add the deadline to
     * @param ui the user interface for displaying confirmation
     * @param storage the storage system for persisting the task
     * @return confirmation message showing the created deadline
     * @throws BugException if the description is empty, date is missing, or date format is invalid
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        // Validate description
        if (description.isEmpty()) {
            throw new BugException("A deadline task cannot have an empty description!");
        }

        // Validate due date
        if (dueDate.isEmpty()) {
            throw new BugException("A deadline task must have a due date!");
        }

        // Parse the due date and create the task
        try {
            LocalDate by2 = LocalDate.parse(dueDate, INPUT_DT);
            Deadlines deadline = new Deadlines(description, by2);
            tasks.add(deadline); // Add the task to the list
            storage.update(tasks); // Update the storage with the new task list
            return ui.showDeadline(deadline, tasks); // Show the response in the UI
        } catch (Exception e) {
            throw new BugException("Invalid date. use yyyy-MM-dd (eg 2005-11-27)!");
        }
    }
}
