package bug;

import java.util.Scanner;

public class Ui {
    private final Scanner sc = new Scanner(System.in);
    private final String name;

    public Ui() {
        name = "Bug";
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showGreeting() {
        showLine();
        System.out.println("Hello! I'm " + name + "\nWhat can I do for you?");
        showLine();
    }

    public void showBye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    public String readLine() {
        return sc.hasNextLine() ? sc.nextLine() : null;
    }

    public void showList(TaskList tasks) {
        showLine();
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + "." + tasks.get(i - 1).toString());
        }
        showLine();
    }

    public void showDone(Task task) {
        showLine();
        System.out.println("Nice! I've marked this task as done:\n[" + task.getStatusIcon() + "] " +
                task.getDescription());
        showLine();
    }

    public void showUndone(Task task) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:\n[" + task.getStatusIcon() + "] " +
                task.getDescription());
        showLine();
    }

    public void showDeleted(Task task, TaskList tasks) {
        showLine();
        System.out.println("Ok! I've removed this task:\n" + task.toString() + "\nNow you have " + tasks.size() +
                " tasks in the list.");
        showLine();
    }

    public void showToDo(Task todo, TaskList tasks) {
        showLine();
        System.out.println("Ok! I've added this task:\n" + todo.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    public void showDeadline(Task deadline, TaskList tasks) {
        showLine();
        System.out.println("Ok! I've added this task:\n" + deadline.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    public void showEvent(Task event, TaskList tasks) {
        showLine();
        System.out.println("Ok! I've added this task:\n" + event.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    public void showError(String error) {
        showLine();
        System.out.println(error);
        showLine();
    }

    public void showTasks(int index, Task task) {
        System.out.println("Here are the matching tasks in your list:\n" + index + "." + task.toString());
    }

}
