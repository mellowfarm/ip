package ui;

import task.TaskList;
import task.Task;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Handles all user interface interactions for the Bug application.
 * Provides methods for displaying messages, reading input, and formatting output.
 * Centralizes all user-facing text and formatting for consistency.
 */
public class Ui {
    private final Scanner sc = new Scanner(System.in);
    private final String name;

    /**
     * Creates a new user interface with the default application name.
     */
    public Ui() {
        name = "Bug";
    }

    /**
     * Returns the application greeting message.
     *
     * @return welcome message introducing the application
     */
    public String showGreeting() {
        return "Hello :)! I'm " + name + "\nWhat can I do for you?";
    }

    /**
     * Returns the farewell message for application exit.
     *
     * @return goodbye message
     */
    public String showBye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Reads a line of input from the user console.
     *
     * @return the user's input line, or null if no input available
     */
    public String readLine() {
        return sc.hasNextLine() ? sc.nextLine() : null;
    }

    /**
     * Formats and returns a numbered list of all tasks.
     *
     * @param tasks the task list to display
     * @return formatted string showing all tasks with indices
     */
    public String showList(TaskList tasks) {
        StringBuilder list = new StringBuilder();
        list.append("your tasks are here:");
        for (int i = 1; i <= tasks.size(); i++) {
            list.append("\n").append(i).append(".").append(tasks.get(i - 1).toString());
        }
        return list.toString();
    }

    /**
     * Returns confirmation message for marking a task as completed.
     *
     * @param task the task that was marked as done
     * @return confirmation message showing the completed task
     */
    public String showDone(Task task) {
        return "Nice! I've marked this task as done:\n[" + task.getStatusIcon() + "] " +
                task.getDescription();
    }

    /**
     * Returns confirmation message for marking a task as not completed.
     *
     * @param task the task that was marked as undone
     * @return confirmation message showing the uncompleted task
     */
    public String showUndone(Task task) {
        return "OK, I've marked this task as not done yet:\n[" + task.getStatusIcon() + "] " +
                task.getDescription();
    }

    /**
     * Returns confirmation message for task deletion.
     *
     * @param task the task that was deleted
     * @param tasks the updated task list for showing new count
     * @return confirmation message showing deleted task and remaining count
     */
    public String showDeleted(Task task, TaskList tasks) {
        return "Ok! I've removed this task:\n" + task.toString() + "\nNow you have " + tasks.size() +
                " tasks in the list.";
    }

    /**
     * Returns confirmation message for todo task creation.
     *
     * @param todo the todo task that was created
     * @param tasks the updated task list for showing new count
     * @return confirmation message showing created todo and task count
     */
    public String showToDo(Task todo, TaskList tasks) {
        return "Ok! I've added this task:\n" + todo.toString() + "\nNow you have " +
                tasks.size() + " tasks in the list.";
    }

    /**
     * Returns confirmation message for deadline task creation.
     *
     * @param deadline the deadline task that was created
     * @param tasks the updated task list for showing new count
     * @return confirmation message showing created deadline and task count
     */
    public String showDeadline(Task deadline, TaskList tasks) {
        return "Ok! I've added this task:\n" + deadline.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns confirmation message for event task creation.
     *
     * @param event the event task that was created
     * @param tasks the updated task list for showing new count
     * @return confirmation message showing created event and task count
     */
    public String showEvent(Task event, TaskList tasks) {
        return "Ok! I've added this task:\n" + event.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the provided error message without additional formatting.
     *
     * @param error the error message to display
     * @return the error message as provided
     */
    public String showError(String error) {
        return error;
    }

    /**
     * Formats and returns a numbered list of tasks matching a search.
     *
     * @param matches the list of tasks that matched the search criteria
     * @return formatted string showing matching tasks with indices
     */
    public String showFoundTasks(ArrayList<Task> matches) {
        StringBuilder list = new StringBuilder();
        list.append("your matching tasks are here:");
        for (int i = 1; i <= matches.size(); i++) {
            list.append("\n").append(i).append(".").append(matches.get(i - 1).toString());
        }
        return list.toString();
    }

    /**
     * Returns confirmation message for task snoozing.
     *
     * @param task the task that was snoozed with updated dates
     * @return confirmation message showing the snoozed task
     */
    public String showSnooze(Task task) {
        return "OK, I've snoozed this task:\n" + task.toString();
    }

}
