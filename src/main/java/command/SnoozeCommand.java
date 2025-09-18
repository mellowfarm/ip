package command;

import exception.BugException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command to postpone a task's deadline or event times by a specified duration.
 * Supports flexible duration formats like "3d", "5h", "30m" for days, hours, and minutes.
 * Cannot be applied to completed tasks or todo tasks without dates.
 */
public class SnoozeCommand extends Command {
    private final int index;
    private final String durationString;

    /**
     * Creates a new snooze command.
     *
     * @param index the zero-based index of the task to snooze
     * @param durationString the duration in format "3d", "5h", or "30m"
     */
    public SnoozeCommand(int index, String durationString) {
        this.index = index;
        this.durationString = durationString;
    }

    /**
     * Executes the snooze command by postponing the task's dates.
     *
     * @param tasks the task list containing the task to snooze
     * @param ui the user interface for displaying confirmation or errors
     * @param storage the storage system for persisting changes
     * @return confirmation message showing the snoozed task with updated dates
     * @throws BugException if task is completed, index is invalid, or duration format is wrong
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
     * Parses duration strings into Duration objects.
     * Supports "3d" (days), "5h" (hours), "30m" (minutes) formats.
     *
     * @param durationString the duration string to parse
     * @return Duration object or null if format is invalid
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
