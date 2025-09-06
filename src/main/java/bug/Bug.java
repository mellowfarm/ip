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

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println(ui.showGreeting());

        while (true) {
            String input = sc.nextLine();

            if (input == null) {
                break;
            }

            try {
                Command command = Parser.parse(input);
                String response = command.execute(tasks, ui, storage);
                System.out.println(response);
                if (command.isExit()) break;
            } catch (BugException e) {
                System.out.println(ui.showError(e.getMessage()));
            }
        }

    }

    public String getResponse(String input) {
        try {
            Command cmd = Parser.parse(input);
            String response = cmd.execute(tasks, ui, storage);
            if (cmd.isExit()) {
                Platform.exit();
            }
            return response;
        } catch (BugException e) {
            return ui.showError(e.getMessage());
        }
    }


    public static void main(String[] args) {
        Bug bug = new Bug();
        bug.run();
    }
}

