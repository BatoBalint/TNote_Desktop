package hu.tnote.balint.CustomNode;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConfirmPanel {
    private VBox container;
    private Label title;
    private HBox buttonContainer;
    private Button okButton;
    private Button cancelButton;

    public ConfirmPanel(String text) {
        titleInit(text);
        buttonContainerInit();
        containerInit();
    }

    private void titleInit(String text) {
        title = new Label(text);
        title.getStyleClass().add("text-white");
    }

    private void buttonContainerInit() {
        buttonsInit();
    }

    private void buttonsInit() {
        okButton = new Button();
        okButton.getStyleClass().add("saveBtn");
        okButton.setText("Igen");

        cancelButton = new Button();
        cancelButton.getStyleClass().add("deleteBtn");
        cancelButton.setText("Mégse");
    }

    private void containerInit() {
        container = new VBox();
        container.setMinWidth(400);
        container.setMinWidth(200);
        container.setPadding(new Insets(10));
        container.getStyleClass().add("roundTRBL");
        container.getStylesheets().add("/hu/tnote/balint/css/buttons.css");
    }
}
