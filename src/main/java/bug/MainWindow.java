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
 * Controller for the main JavaFX window of the Bug application.
 * Handles user input, displays chat dialogs, and manages the GUI interaction flow.
 * Coordinates between user interface elements and the Bug application logic.
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
     * Initializes the main window by setting up scroll behavior.
     */
    @FXML
    public void initialise() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the Bug application instance and displays the initial greeting.
     *
     * @param b the Bug application instance to connect with
     */
    public void setBug(Bug b) {
        bug = b;
        String greeting = bug.greeting();
        dialogContainer.getChildren().add(DialogBox.getBugDialog(greeting, bugImage));
    }

    /**
     * Handles user input when the send button is pressed or enter key is hit.
     * Processes the command through Bug and displays both user input and bot response.
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
