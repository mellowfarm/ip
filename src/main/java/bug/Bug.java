package bug;

import javafx.application.Application;
import javafx.application.Platform;

import java.util.Scanner;

/**
 * The Bug class is the entry point of the task management application.
 * It interacts with the user, manages tasks, and stores them in a file.
 */
public class Bug {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructor initializes the application by setting up the user interface (UI),
     * loading tasks from storage, and preparing the task list.
     */
    public Bug() {
        ui = new Ui();
        storage = new Storage();
        TaskList loadedTasks;

        try {
            loadedTasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showError(":(! failed to load tasks!");
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
    }

    /**
     * Runs the application, accepting user input and executing commands in a loop.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println(ui.showGreeting());

        while (true) {
            String input = sc.nextLine();

            if (input == null) {
                break; // Exit the loop if input is null
            }

            try {
                Command command = Parser.parse(input); // Parse the user input into a command
                String response = command.execute(tasks, ui, storage); // Execute the command
                System.out.println(response);

                if (command.isExit()) break; // Exit if the command signals to quit

            } catch (BugException e) {
                System.out.println(ui.showError(e.getMessage())); // Show error message if command fails
            }
        }

    }

    /**
     * Returns the response to a given user input.
     * This is used for testing purposes or when the application needs to respond programmatically.
     *
     * @param input the user input to process
     * @return the response based on the input
     */
    public String getResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ui.showError(":(! please enter a command!");
        }

        try {
            Command cmd = Parser.parse(input); // Parse the input into a command
            String response = cmd.execute(tasks, ui, storage); // Execute the command

            if (cmd.isExit()) {
                Platform.exit(); // Exit the JavaFX application if the exit command is issued
            }

            return response;
        } catch (BugException e) {
            return ui.showError(e.getMessage());
        } catch (Exception e) {
            return ui.showError(":(! something went wrong! Please try again.");
        }
    }

    public String greeting() {
        return ui.showGreeting();
    }

    /**
     * The main method is the entry point for the application. It starts the task management system.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        Bug bug = new Bug(); // Create an instance of the Bug class
        bug.run(); // Start the application
    }
}

