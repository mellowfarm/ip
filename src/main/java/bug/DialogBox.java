package bug;
import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A custom dialog box for displaying messages in the task management application.
 * The dialog box includes a label for the message text and an image for the display picture.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog; // The label that holds the text of the message
    @FXML
    private ImageView displayPicture; // The image view for the display picture

    /**
     * Constructor for creating a DialogBox with text and an image.
     *
     * @param text the message text to display
     * @param img the image to display in the dialog box
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

        dialog.setText(text);
        dialog.setWrapText(true);
        dialog.setMaxWidth(250); // Set the message text in the dialog
        displayPicture.setImage(img); // Set the display picture image
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and the text is on the right.
     * This is used for the "bug" messages to show the text on the opposite side.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp); // Reverse the order of the children nodes
        getChildren().setAll(tmp); // Update the children of this HBox with the reversed nodes
        setAlignment(Pos.TOP_LEFT); // Align the content to the top-left
        dialog.getStyleClass().add("reply-label"); // Add a style class for the reply
    }

    /**
     * Creates a DialogBox for the user message, with text and a user image.
     *
     * @param text the user message text
     * @param img the user display picture
     * @return a DialogBox with the user message and image
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img); // Return a new dialog box for the user
    }

    /**
     * Creates a DialogBox for the bug message, with text and a bug image, and flips it.
     *
     * @param text the bug message text
     * @param img the bug display picture
     * @return a flipped DialogBox for the bug message and image
     */
    public static DialogBox getBugDialog(String text, Image img) {
        var db = new DialogBox(text, img); // Create a new dialog box for the bug
        db.flip(); // Flip the dialog box to position the image and text correctly
        return db;
    }
}