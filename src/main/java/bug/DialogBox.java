package bug;
import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Custom JavaFX component representing a dialog box in the chat interface.
 * Displays messages with accompanying display pictures for user and bot conversations.
 * Supports flipping layout to differentiate between user and bot messages.
 */
public class DialogBox extends HBox {
    @FXML
    private TextFlow dialog; // The label that holds the text of the message
    @FXML
    private ImageView displayPicture; // The image view for the display picture

    /**
     * Creates a dialog box with the specified text and image.
     *
     * @param text the message text to display
     * @param img the display picture to show alongside the message
     */
    private DialogBox(String text, Image img) {
        try {
            // Load the FXML layout for the dialog box
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this); // Set this class as the controller for the FXML
            fxmlLoader.setRoot(this); // Set the root element of the FXML to this instance
            fxmlLoader.load(); // Load the FXML layout
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if loading fails
        }

        Text textNode = new Text(text);
        textNode.getStyleClass().add("dialog-text");
        dialog.getChildren().add(textNode);
        setFillHeight(false);
        displayPicture.setImage(img); // Set the display picture image
    }

    /**
     * Flips the dialog box layout to show image on the left and text on the right.
     * Used for bot messages to differentiate from user messages.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp); // Reverse the order of the children nodes
        getChildren().setAll(tmp); // Update the children of this HBox with the reversed nodes
        setAlignment(Pos.TOP_LEFT); // Align the content to the top-left
        dialog.getStyleClass().add("reply-text-flow"); // Add a style class for the reply
    }

    /**
     * Creates a dialog box for user messages.
     *
     * @param text the user's message text
     * @param img the user's display picture
     * @return a new dialog box formatted for user messages
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img); // Return a new dialog box for the user
    }

    /**
     * Creates a dialog box for bot messages.
     *
     * @param text the bot's response text
     * @param img the bot's display picture
     * @return a new flipped dialog box formatted for bot messages
     */
    public static DialogBox getBugDialog(String text, Image img) {
        var db = new DialogBox(text, img); // Create a new dialog box for the bug
        db.flip(); // Flip the dialog box to position the image and text correctly
        return db;
    }
}