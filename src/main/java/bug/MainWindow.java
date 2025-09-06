package bug;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bug bug;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/sonny1.png"));
    private Image bugImage = new Image(this.getClass().getResourceAsStream("/images/sonny2.png"));

    @FXML
    public void initialise() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setBug(Bug b) {
        bug = b;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bug.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBugDialog(response, bugImage)
        );
        userInput.clear();
        scrollPane.setVvalue(1.0);
    }
}
