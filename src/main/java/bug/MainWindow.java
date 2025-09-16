package bug;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Represents the main window of the task management application.
 * This class handles user interaction, displaying dialogs, and managing the UI.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane; // The scrollable container for the dialog box
    @FXML
    private VBox dialogContainer; // The container holding the dialog boxes
    @FXML
    private TextField userInput; // Text field for the user's input
    @FXML
    private Button sendButton; // Button for sending user input

    private Bug bug; // The core application logic

    // Default images for user and bug
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/sonny1.png"));
    private Image bugImage = new Image(this.getClass().getResourceAsStream("/images/sonny2.png"));

    /**
     * Initializes the MainWindow by binding the scroll position to the dialog container height.
     */
    @FXML
    public void initialise() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the Bug instance, allowing interaction with the core application logic.
     *
     * @param b the Bug instance to be set
     */
    public void setBug(Bug b) {
        bug = b;
        String greeting = bug.greeting();
        dialogContainer.getChildren().add(DialogBox.getBugDialog(greeting, bugImage));
    }

    /**
     * Handles the user input when the send button is pressed.
     * It retrieves the input, gets a response from the Bug, and displays both in the dialog container.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bug.getResponse(input);

        // Add the user dialog and bug response to the dialog container
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBugDialog(response, bugImage)
        );

        userInput.clear();
        Platform.runLater(() -> {
            scrollPane.setVvalue(1.0);
        });
    }
}
