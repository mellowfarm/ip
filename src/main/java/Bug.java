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

            if ("bye".equalsIgnoreCase(input)) {
                break;
            }

            if ("list".equalsIgnoreCase(input)) {
                for (int i = 1; i <= size; i++) {
                    System.out.println(i + ".[" + tasks[i - 1].getStatusIcon() + "] " + tasks[i - 1].getDescription());
                }
                System.out.println("____________________________________________________________");
            } else if ("mark".equalsIgnoreCase(input.trim().split("\\s+", 2)[0])) {
                int taskNum = Integer.parseInt(input.trim().split("\\s+", 2)[1]) - 1;
                tasks[taskNum].markAsDone();
                System.out.println("Nice! I've marked this task as done:\n[" + tasks[taskNum].getStatusIcon() + "] " +
                        tasks[taskNum].getDescription());
                System.out.println("____________________________________________________________");
            } else if ("unmark".equalsIgnoreCase(input.trim().split("\\s+", 2)[0])) {
                int taskNum = Integer.parseInt(input.trim().split("\\s+", 2)[1]) - 1;
                tasks[taskNum].markAsUndone();;
                System.out.println("OK, I've marked this task as not done yet:\n[" + tasks[taskNum].getStatusIcon() + "] " +
                        tasks[taskNum].getDescription());
                System.out.println("____________________________________________________________");
            } else {
                tasks[size] = new Task(input);
                size++;
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                System.out.println("____________________________________________________________");
            }


        }

        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
