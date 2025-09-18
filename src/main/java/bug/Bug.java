package bug;

import command.Command;
import exception.BugException;
import javafx.application.Platform;
import storage.Storage;
import task.TaskList;
import ui.Parser;
import ui.Ui;

import java.util.Scanner;

/**
 * Main application class for the Bug task management system.
 * Handles user interaction, command processing, and application lifecycle.
 * Serves as the entry point and coordinator between UI, storage, and task management components.
 */
public class Bug {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Initializes the Bug application with UI, storage, and task list components.
     * Loads existing tasks from storage or starts with an empty list if loading fails.
     */
    public Bug() {
        ui = new Ui();
        storage = new Storage();
        TaskList loadedTasks;

        try {
            loadedTasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showError("Failed to load tasks!");
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
    }

    /**
     * Runs the application in console mode with continuous user input processing.
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
     * Processes a single user input and returns the response.
     * Used for GUI mode and testing.
     *
     * @param input the user command to process
     * @return the response message
     */
    public String getResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ui.showError("Please enter a command!");
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
            return ui.showError("Something went wrong! Please try again.");
        }
    }

    /**
     * Returns the application greeting message.
     *
     * @return the greeting message
     */
    public String greeting() {
        return ui.showGreeting();
    }

    /**
     * Main entry point for console application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Bug bug = new Bug(); // Create an instance of the Bug class
        bug.run(); // Start the application
    }
}

