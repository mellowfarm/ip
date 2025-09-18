package bug;
import javafx.application.Application;

/**
 * Application launcher to workaround classpath issues with JavaFX.
 * Provides a clean entry point for the JavaFX application when using build tools.
 */
public class Launcher {

    /**
     * Main entry point for launching the JavaFX Bug application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        // Launch the main JavaFX application
        Application.launch(Main.class, args);
    }
}