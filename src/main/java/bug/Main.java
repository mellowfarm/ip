package bug;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main entry point for the JavaFX application.
 * This class initializes the application window and sets up the UI components.
 */
public class Main extends Application {

    private Bug bug = new Bug(); // The core logic of the task management app

    /**
     * Starts the JavaFX application by loading the main window FXML and displaying it.
     *
     * @param stage the primary stage for the application
     */
    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file for the main window
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            // Create a scene with the loaded layout and set it on the stage
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Set the minimum size for the window
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            // Set the controller for the main window and pass the Bug instance to it
            fxmlLoader.<MainWindow>getController().setBug(bug);

            // Show the stage (the application window)
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if loading the FXML fails
        }
    }
}
