package bug;

import java.util.Scanner;

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
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a greeting message to the user.
     */
    public void showGreeting() {
        showLine();
        System.out.println("Hello! I'm " + name + "\nWhat can I do for you?");
        showLine();
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showBye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
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
    public void showList(TaskList tasks) {
        showLine();
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + "." + tasks.get(i - 1).toString());
        }
        showLine();
    }

    /**
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task the task that was marked as done
     */
    public void showDone(Task task) {
        showLine();
        System.out.println("Nice! I've marked this task as done:\n[" + task.getStatusIcon() + "] " +
                task.getDescription());
        showLine();
    }

    /**
     * Displays a message confirming that a task has been marked as not done.
     *
     * @param task the task that was marked as not done
     */
    public void showUndone(Task task) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:\n[" + task.getStatusIcon() + "] " +
                task.getDescription());
        showLine();
    }

    /**
     * Displays a message confirming that a task has been deleted.
     *
     * @param task the task that was deleted
     * @param tasks the updated TaskList
     */
    public void showDeleted(Task task, TaskList tasks) {
        showLine();
        System.out.println("Ok! I've removed this task:\n" + task.toString() + "\nNow you have " + tasks.size() +
                " tasks in the list.");
        showLine();
    }

    /**
     * Displays a message confirming that a new ToDo task has been added.
     *
     * @param todo the ToDo task that was added
     * @param tasks the updated TaskList
     */
    public void showToDo(Task todo, TaskList tasks) {
        showLine();
        System.out.println("Ok! I've added this task:\n" + todo.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a message confirming that a new Deadline task has been added.
     *
     * @param deadline the Deadline task that was added
     * @param tasks the updated TaskList
     */
    public void showDeadline(Task deadline, TaskList tasks) {
        showLine();
        System.out.println("Ok! I've added this task:\n" + deadline.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a message confirming that a new Event task has been added.
     *
     * @param event the Event task that was added
     * @param tasks the updated TaskList
     */
    public void showEvent(Task event, TaskList tasks) {
        showLine();
        System.out.println("Ok! I've added this task:\n" + event.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param error the error message to display
     */
    public void showError(String error) {
        showLine();
        System.out.println(error);
        showLine();
    }

    public void showTasks(int index, Task task) {
        System.out.println("Here are the matching tasks in your list:\n" + index + "." + task.toString());
    }

}
