package hu.tnote.balint.Controllers;

import hu.tnote.balint.CustomNode.NoteButton;
import hu.tnote.balint.Note;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public class TestViewController {

    @FXML
    private VBox rootContainer;

    public void initialize() {
//        new Alert(Alert.AlertType.NONE, "Test", ButtonType.OK).show();
        NoteButton nBtn = new NoteButton(new Note(-1, 12, "Lajos a hegyen", "Szeretem a tejete"));
        nBtn.setOnAction(v -> {
            new Alert(Alert.AlertType.NONE, nBtn.getNoteContent(), ButtonType.OK).show();
        });
        rootContainer.getChildren().add(nBtn.get());
    }
}
