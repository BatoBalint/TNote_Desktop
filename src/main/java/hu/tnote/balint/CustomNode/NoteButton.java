package hu.tnote.balint.CustomNode;

import hu.tnote.balint.Note;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class NoteButton {
    private Button button;
    private Note note;

    public String getNoteContent() {
        return this.note.getContent();
    }
    public Note getNote() { return this.note; }

    public NoteButton(Note note) {
        this.note = note;
        createButton();
    }

    private void createButton() {
        this.button = new Button();
        this.button.setMinWidth(200);
        this.button.setMaxWidth(200);
        this.button.setMinHeight(75);
        this.button.setMaxHeight(75);
        if (note.getId() >= 0) {
            this.button.setText(note.getTitle());
        } else {
            this.button.setText("+");
            this.button.getStyleClass().add("h1");
        }
        this.button.getStyleClass().add("noteButton");
        this.button.getStyleClass().add("roundTRBL");
        this.button.setAlignment(Pos.CENTER_LEFT);
    }

    public Button get() {
        return this.button;
    }

    public void setOnAction(EventHandler<ActionEvent> eventHandler) {
        this.button.setOnAction(eventHandler);
    }
}
