package bug;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues and launch the JavaFX application.
 * This class is necessary for running the JavaFX application when using a build tool like Maven or Gradle,
 * as it provides a clean entry point for the application.
 */
public class Launcher {

    /**
     * The main method serves as the entry point for launching the JavaFX application.
     *
     * @param args command-line arguments passed to the application (not used in this case)
     */
    public static void main(String[] args) {
        // Launch the main JavaFX application
        Application.launch(Main.class, args);
    }
}