package hu.tnote.balint.Controllers.InsideViews;

import hu.tnote.balint.Controllers.Api;
import hu.tnote.balint.Note;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NoteEditorController {
    @FXML
    public VBox saveBtnContainer;
    @FXML
    private VBox rootContainer;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField noteTitle;
    @FXML
    private HBox titleHbox;
    @FXML
    public MenuButton menuBtn;
    private Note note;
    private ScrollPane scrollPane;
    private SimpleIntegerProperty lineCount = new SimpleIntegerProperty();

    public void initialize() {
        uiInit();

        moteButtonSetup();

        textArea.prefRowCountProperty().bind(lineCount);
        lineCount.set(23);
        textArea.textProperty().addListener((observableValue, s, t1) -> {
            t1 = t1.replace("\n", "\na");
            String[] lines = t1.split("\n");

            if (lines.length < 19) {
                lineCount.set(20);
            } else {
                lineCount.set(lines.length + 1);
                scrollPane.setVvalue(1.0D);
            };
        });
    }

    private void uiInit() {
        VBox.setVgrow(titleHbox, Priority.ALWAYS);
        HBox.setHgrow(noteTitle, Priority.ALWAYS);
        rootContainer.setAlignment(Pos.TOP_CENTER);

        textArea.setMaxWidth(800);
        textArea.setWrapText(true);
    }

    private void moteButtonSetup() {
        MenuItem delete = new MenuItem("Törlés");

        delete.setOnAction(actionEvent -> {
            try {
                Api.deleteNote(note.getId());
                alertWait("Sikeres törlés");
            } catch (IOException e) {
                e.printStackTrace();
                alert("Valami miatt nem sikerült törölni a jegyzetet");
            }
        });

        menuBtn.getItems().add(delete);
    }

    public void passData(Note note, ScrollPane scrollPane) {
        this.note = note;
        noteTitle.setText(this.note.getTitle());
        textArea.setText(this.note.getContent());

        this.scrollPane = scrollPane;
    }

    @FXML
    public void saveBtnClick() {
        if (note.getId() >= 0) {
            try {
                Api.saveNote(note.getId(), noteTitle.getText(), textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Api.addNote(note.getId(), noteTitle.getText(), textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void moreBtnClick() {
        new Alert(Alert.AlertType.NONE, "TEST", ButtonType.OK).show();
    }

    @FXML
    public void menubuttonAction() {
        new Alert(Alert.AlertType.NONE, "Test", ButtonType.OK).show();
    }

    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    private void alertWait(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).showAndWait();
    }
}
