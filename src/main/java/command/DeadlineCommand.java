package command;

import exception.BugException;
import storage.Storage;
import task.Deadlines;
import task.TaskList;
import ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
     * @throws BugException if the description is empty, date is missing, date format is invalid, or storage fails
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        validateInputs();

        try {
            LocalDate by2 = LocalDate.parse(dueDate, INPUT_DT);
            Deadlines deadline = new Deadlines(description, by2);
            tasks.add(deadline);
            storage.update(tasks);
            return ui.showDeadline(deadline, tasks);
        } catch (DateTimeParseException e) {
            throw new BugException("Invalid date format. Use yyyy-MM-dd (e.g., 2005-11-27)");
        }
    }

    /**
     * Validates the input parameters for the deadline command.
     *
     * @throws BugException if validation fails
     */
    private void validateInputs() throws BugException {
        if (description.isEmpty()) {
            throw new BugException("A deadline task cannot have an empty description!");
        }

        if (dueDate.isEmpty()) {
            throw new BugException("A deadline task must have a due date!");
        }
    }
}
