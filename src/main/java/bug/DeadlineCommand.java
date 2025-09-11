package bug;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a command to create a deadline task.
 * This command is responsible for parsing the deadline's description and due date,
 * creating the corresponding `Deadlines` object, and updating the task list and storage.
 */
public class DeadlineCommand extends Command{

    private final String description;
    private final String dueDate;
    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructor for the DeadlineCommand.
     * Initializes the task description and due date.
     *
     * @param description the description of the task
     * @param dueDate the due date of the task in "yyyy-MM-dd" format
     */
    public DeadlineCommand(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    /**
     * Executes the DeadlineCommand by validating the input, parsing the due date,
     * creating a new `Deadlines` task, and updating the task list and storage.
     *
     * @param tasks the current list of tasks
     * @param ui the user interface used to display the response
     * @param storage the storage used to save the tasks
     * @return a response message showing the created deadline task
     * @throws BugException if the input is invalid, such as an empty description or invalid date format
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException {
        // Validate description
        if (description.isEmpty()) {
            throw new BugException(":(! a deadline task cannot have an empty description!");
        }

        // Validate due date
        if (dueDate.isEmpty()) {
            throw new BugException(":(! a deadline task must have a due date!");
        }

        // Parse the due date and create the task
        try {
            LocalDate by2 = LocalDate.parse(dueDate, INPUT_DT);
            Deadlines deadline = new Deadlines(description, by2);
            tasks.add(deadline); // Add the task to the list
            storage.update(tasks); // Update the storage with the new task list
            return ui.showDeadline(deadline, tasks); // Show the response in the UI
        } catch (Exception e) {
            throw new BugException(":(! invalid date. use yyyy-MM-dd (eg 2005-11-27)!");
        }
    }
}
