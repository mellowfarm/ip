import java.util.*;

public class Bug {
    public static void main(String[] args) {
        String name = "Bug";
        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int size = 0;

        // greetings
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm " + name + "\nWhat can I do for you?");
        System.out.println("____________________________________________________________");

        // input and processing input
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] split = input.split("\\s+", 2);
            String instruction = split[0];
            String contents = (split.length > 1) ? split[1] : "";

            try {
                if ("bye".equals(instruction)) {
                    break;
                }

                if ("list".equals(instruction)) {
                    for (int i = 1; i <= size; i++) {
                        System.out.println(i + "." + tasks[i - 1].toString());
                    }
                    System.out.println("____________________________________________________________");
                } else if ("mark".equals(instruction)) {
                    int taskNum = Integer.parseInt(contents) - 1;
                    tasks[taskNum].markAsDone();
                    System.out.println("Nice! I've marked this task as done:\n[" + tasks[taskNum].getStatusIcon() + "] " +
                            tasks[taskNum].getDescription());
                    System.out.println("____________________________________________________________");
                } else if ("unmark".equals(instruction)) {
                    int taskNum = Integer.parseInt(contents) - 1;
                    tasks[taskNum].markAsUndone();
                    System.out.println("OK, I've marked this task as not done yet:\n[" + tasks[taskNum].getStatusIcon() + "] " +
                            tasks[taskNum].getDescription());
                    System.out.println("____________________________________________________________");
                } else {
                    if ("todo".equals(instruction)) {
                        if ("".equals(contents)) {
                            throw new BugException(":(! a todo task must have a description!");
                        }
                        tasks[size] = new ToDos(contents);
                        size++;
                        int num = size + 1;
                        System.out.println("____________________________________________________________");
                        System.out.println("Ok! I've added this task:\n" + tasks[size].toString() +
                                "\nNow you have " + num + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    } else if ("deadline".equals(instruction)) {
                        String[] parts = contents.split("/", 2);
                        String desc = parts[0];
                        if ("".equals(desc)) {
                            throw new BugException(":(! a deadline task must have a description!");
                        }
                        String by = parts[1].trim().split("\\s+", 2)[1];
                        if ("".equals(by)) {
                            throw new BugException(":(! a deadline task must have a due date!");
                        }
                        tasks[size] = new Deadlines(desc, by);
                        size++;
                        int num = size + 1;
                        System.out.println("____________________________________________________________");
                        System.out.println("Ok! I've added this task:\n" + tasks[size].toString() +
                                "\nNow you have " + num + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    } else if ("event".equals(instruction)) {
                        String[] parts = contents.split("/", 3);
                        String desc = parts[0];
                        if ("".equals(desc)) {
                            throw new BugException(":(! an event task must have a description!");
                        }
                        String start = parts[1].trim().split("\\s+", 2)[1];
                        if ("".equals(start)) {
                            throw new BugException(":(! an event task must have a start date!");
                        }
                        String end = parts[2].trim().split("\\s+", 2)[1];
                        if ("".equals(end)) {
                            throw new BugException(":(! an event task must have an end date!");
                        }
                        tasks[size] = new Events(desc, start, end);
                        size++;
                        int num = size + 1;
                        System.out.println("____________________________________________________________");
                        System.out.println("Ok! I've added this task:\n" + tasks[size].toString() +
                                "\nNow you have " + num + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    } else {
                        throw new BugException(":(! i don't know what you mean! please re-enter your task :)!");
                    }

                }
            } catch (BugException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            }

        }

        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
