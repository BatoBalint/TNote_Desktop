package hu.tnote.balint.Controllers.InsideViews;

import hu.tnote.balint.Note;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class NoteEditorController {
    @FXML
    private VBox rootContainer;
    @FXML
    private TextArea textArea;
    @FXML
    private Label noteTitle;
    private Note note;
    private SimpleIntegerProperty lineCount = new SimpleIntegerProperty(20);

    public void initialize() {
        rootContainer.setAlignment(Pos.TOP_CENTER);

        textArea.setMaxWidth(800);
        textArea.setWrapText(true);
        textArea.prefRowCountProperty().bind(lineCount);
        textArea.textProperty().addListener((observableValue, s, t1) -> {
            t1.concat("a");
            String[] lines = t1.split("\n");
            System.out.println(lines.length);
            lineCount.set((lines.length < 17) ? 20 : lines.length + 1);
        });
    }

    public void setNote(Note note) {
        this.note = note;
        noteTitle.setText(this.note.getTitle());
        textArea.setText(this.note.getContent());
    }

}
