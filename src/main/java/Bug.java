import java.util.*;

public class Bug {
    public static void main(String[] args) {
        String name = "Bug";
        Scanner sc = new Scanner(System.in);
        String[] tasks = new String[100];
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
                    System.out.println(i + ". " + tasks[i - 1]);
                }
                System.out.println("____________________________________________________________");
            } else {
                tasks[size] = input;
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
