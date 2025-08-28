import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Bug {

    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static void main(String[] args) {
        String name = "Bug";
        Scanner sc = new Scanner(System.in);
        Save storage = new Save();
        ArrayList<Task> tasks = new ArrayList<>(storage.load());

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
                    System.out.println("____________________________________________________________");
                    for (int i = 1; i <= tasks.size(); i++) {
                        System.out.println(i + "." + tasks.get(i - 1).toString());
                    }
                    System.out.println("____________________________________________________________");
                } else if ("mark".equals(instruction)) {
                    System.out.println("____________________________________________________________");
                    int taskNum = Integer.parseInt(contents) - 1;
                    tasks.get(taskNum).markAsDone();
                    System.out.println("Nice! I've marked this task as done:\n[" + tasks.get(taskNum).getStatusIcon() + "] " +
                            tasks.get(taskNum).getDescription());
                    System.out.println("____________________________________________________________");
                    storage.update(tasks);
                } else if ("unmark".equals(instruction)) {
                    System.out.println("____________________________________________________________");
                    int taskNum = Integer.parseInt(contents) - 1;
                    tasks.get(taskNum).markAsUndone();
                    System.out.println("OK, I've marked this task as not done yet:\n[" + tasks.get(taskNum).getStatusIcon() + "] " +
                            tasks.get(taskNum).getDescription());
                    System.out.println("____________________________________________________________");
                    storage.update(tasks);
                } else if ("delete".equals(instruction)) {
                    System.out.println("____________________________________________________________");
                    int taskNum = Integer.parseInt(contents) - 1;
                    Task rem = tasks.get(taskNum);
                    tasks.remove(rem);
                    System.out.println("Ok! I've removed this task:\n" + rem.toString() + "\nNow you have " + tasks.size() +
                            " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    storage.update(tasks);
                } else {
                    if ("todo".equals(instruction)) {
                        if ("".equals(contents)) {
                            throw new BugException(":(! a todo task must have a description!");
                        }
                        Task todo = new ToDos(contents);
                        tasks.add(todo);
                        System.out.println("____________________________________________________________");
                        System.out.println("Ok! I've added this task:\n" + todo.toString() +
                                "\nNow you have " + tasks.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        storage.update(tasks);
                    } else if ("deadline".equals(instruction)) {
                        String[] parts = contents.split("/", 2);
                        String desc = parts[0].trim();
                        if ("".equals(desc)) {
                            throw new BugException(":(! a deadline task must have a description!");
                        }
                        String byStr = parts[1].trim().split("\\s+", 2)[1];
                        if ("".equals(byStr)) {
                            throw new BugException(":(! a deadline task must have a due date!");
                        }

                        LocalDate by;
                        try {
                            by = LocalDate.parse(byStr);
                        } catch (Exception e) {
                            throw new BugException(":(! invalid date. use yyyy-MM-dd (eg 2005-11-27)!");
                        }
                        Task deadline = new Deadlines(desc, by);
                        tasks.add(deadline);
                        System.out.println("____________________________________________________________");
                        System.out.println("Ok! I've added this task:\n" + deadline.toString() +
                                "\nNow you have " + tasks.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        storage.update(tasks);

                    } else if ("event".equals(instruction)) {
                        String[] parts = contents.split("/", 3);
                        String desc = parts[0].trim();
                        if ("".equals(desc)) {
                            throw new BugException(":(! an event task must have a description!");
                        }
                        String startStr = parts[1].trim().split("\\s+", 2)[1];
                        if ("".equals(startStr)) {
                            throw new BugException(":(! an event task must have a start date!");
                        }
                        String endStr = parts[2].trim().split("\\s+", 2)[1];
                        if ("".equals(endStr)) {
                            throw new BugException(":(! an event task must have an end date!");
                        }

                        LocalDateTime start, end;
                        try {
                            start = LocalDateTime.parse(startStr, INPUT_DT);
                            end = LocalDateTime.parse(endStr, INPUT_DT);
                        } catch (Exception e) {
                            throw new BugException(":(! invalid datetime. use yyyy-MM-dd HHmm (eg 2005-11-27 1800)!");
                        }

                        Task event = new Events(desc, start, end);
                        tasks.add(new Events(desc, start, end));
                        System.out.println("____________________________________________________________");
                        System.out.println("Ok! I've added this task:\n" + event.toString() +
                                "\nNow you have " + tasks.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        storage.update(tasks);
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
