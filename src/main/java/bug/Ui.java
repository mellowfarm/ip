package bug;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * The Ui class handles all user interface interactions.
 * It is responsible for displaying messages to the user, reading input and displaying task-related information
 * (e.g. task lists, errors, etc.).
 * The class provides methods for greeting the user, showing a list of tasks, marking tasks as done, and handling errors
 */
public class Ui {
    private final Scanner sc = new Scanner(System.in);
    private final String name;


    public Ui() {
        name = "Bug";
    }

    /**
     * Prints a line of underscores for formatting.
     */
    public String showLine() {
        return "____________________________________________________________";
    }

    /**
     * Displays a greeting message to the user.
     */
    public String showGreeting() {
        return "Hello! I'm " + name + "\nWhat can I do for you?";
    }

    /**
     * Displays a goodbye message to the user.
     */
    public String showBye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Reads a line of input from the user.
     *
     * @return the input string from the user, or null if no input is available
     */
    public String readLine() {
        return sc.hasNextLine() ? sc.nextLine() : null;
    }

    /**
     * Displays a list of tasks to the user.
     *
     * @param tasks the TaskList containing all tasks to be displayed.
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
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task the task that was marked as done
     */
    public String showDone(Task task) {
        return "Nice! I've marked this task as done:\n[" + task.getStatusIcon() + "] " +
                task.getDescription();
    }

    /**
     * Displays a message confirming that a task has been marked as not done.
     *
     * @param task the task that was marked as not done
     */
    public String showUndone(Task task) {
        return "OK, I've marked this task as not done yet:\n[" + task.getStatusIcon() + "] " +
                task.getDescription();
    }

    /**
     * Displays a message confirming that a task has been deleted.
     *
     * @param task the task that was deleted
     * @param tasks the updated TaskList
     */
    public String showDeleted(Task task, TaskList tasks) {
        return "Ok! I've removed this task:\n" + task.toString() + "\nNow you have " + tasks.size() +
                " tasks in the list.";
    }

    /**
     * Displays a message confirming that a new ToDo task has been added.
     *
     * @param todo the ToDo task that was added
     * @param tasks the updated TaskList
     */
    public String showToDo(Task todo, TaskList tasks) {
        return "Ok! I've added this task:\n" + todo.toString() + "\nNow you have " +
                tasks.size() + " tasks in the list.";
    }

    /**
     * Displays a message confirming that a new Deadline task has been added.
     *
     * @param deadline the Deadline task that was added
     * @param tasks the updated TaskList
     */
    public String showDeadline(Task deadline, TaskList tasks) {
        return "Ok! I've added this task:\n" + deadline.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Displays a message confirming that a new Event task has been added.
     *
     * @param event the Event task that was added
     * @param tasks the updated TaskList
     */
    public String showEvent(Task event, TaskList tasks) {
        return "Ok! I've added this task:\n" + event.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Displays an error message to the user.
     *
     * @param error the error message to display
     */
    public String showError(String error) {
        return error;
    }

    /**
     * Displays a list of tasks that match a search query.
     *
     * @param matches the list of matching tasks
     * @return a formatted string of the matching tasks
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
     * Displays a message confirming that a task has been snoozed.
     * Shows the task with its updated date/time information after snoozing.
     *
     * @param task the task that was snoozed with updated deadline or event times
     * @return a formatted string showing the confirmation message and the snoozed task details
     */
    public String showSnooze(Task task) {
        return "OK, I've snoozed this task:\n" + task.toString();
    }

}
