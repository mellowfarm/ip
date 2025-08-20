import java.util.*;

public class Bug {
    public static void main(String[] args) {
        String name = "Bug";
        Scanner sc = new Scanner(System.in);

        // greetings
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm " + name + "\nWhat can I do for you?");
        System.out.println("____________________________________________________________");

        // input and processing input
        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            if("bye".equalsIgnoreCase(input)) {
                break;
            }

            System.out.println("____________________________________________________________");
            System.out.println(input);
            System.out.println("____________________________________________________________");
        }

        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
