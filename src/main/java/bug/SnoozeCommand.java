package bug;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a command to snooze a task by postponing its deadline or event times.
 * This command works with deadline and event tasks by adding a specified duration to their dates.
 * Todo tasks cannot be snoozed since they don't have associated dates.
 *
 * Supports flexible duration formats:
 * - Days: "3d" (snooze by 3 days)
 * - Hours: "5h" (snooze by 5 hours)
 * - Minutes: "30m" (snooze by 30 minutes)
 */
public class SnoozeCommand extends Command {
    private final int index;
    private final String durationString;

    /**
     * Constructor for creating a SnoozeCommand.
     *
     * @param index the index of the task to snooze (0-based indexing)
     * @param durationString the duration string in format like "3d", "5h", or "30m"
     */
    public SnoozeCommand(int index, String durationString) {
        this.index = index;
        this.durationString = durationString;
    }

    /**
     * Executes the SnoozeCommand by postponing the specified task's deadline or event times.
     * Validates the task index and duration format, then applies the snooze operation.
     * Updates the task list in storage and displays a confirmation message.
     *
     * @param tasks the current list of tasks to operate on
     * @param ui the user interface used to display responses and error messages
     * @param storage the storage system used to persist the updated task list
     * @return a response message showing the snoozed task with updated dates
     * @throws BugException if the task index is invalid, duration format is incorrect,
     *                     or the task type doesn't support snoozing
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BugException{
        try {
            Task task = tasks.get(index);

            if (task.getStatusIcon().equals("X")) {
                throw new BugException("Cannot snooze a completed task! unmark it first if needed!");
            }

            Duration duration = parseDuration(durationString);
            if (duration == null) {
                throw new BugException("Duration cannot be empty!");
            }
            task.snooze(duration);
            storage.update(tasks);
            return ui.showSnooze(task);
        } catch (BugException e) {
            return ui.showError(e.getMessage());
        } catch (Exception e) {
            throw new BugException("No tasks at this index!");
        }
    }

    /**
     * Parses a duration string into a Duration object.
     * Supports three time units with specific format: number followed by unit letter.
     *
     * Supported formats:
     * - "3d" for 3 days
     * - "5h" for 5 hours
     * - "30m" for 30 minutes
     *
     * @param durationString the duration string to parse (e.g., "3d", "5h", "30m")
     * @return a Duration object representing the parsed time period, or null if the format is invalid
     */
    private static Duration parseDuration(String durationString) {
        Pattern pattern = Pattern.compile("(\\d+)([dhm])"); // match number + unit (d for days, h for hours, m for minutes)
        Matcher matcher = pattern.matcher(durationString);

        if (matcher.matches()) {
            int amount = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "d":
                    return Duration.ofDays(amount);
                case "h":
                    return Duration.ofHours(amount);
                case "m":
                    return Duration.ofMinutes(amount);
                default:
                    return null;
            }
        }

        return null;
    }
}
