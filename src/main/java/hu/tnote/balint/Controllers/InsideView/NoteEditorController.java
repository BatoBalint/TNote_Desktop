package hu.tnote.balint.Controllers.InsideView;

import hu.tnote.balint.Api;
import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.Note;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NoteEditorController extends Controller {
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
    private SimpleIntegerProperty lineCount = new SimpleIntegerProperty();

    public void initialize() {
        uiInit();

        moreButtonSetup();

        textArea.prefRowCountProperty().bind(lineCount);
        lineCount.set(23);
        textArea.textProperty().addListener((observableValue, s, t1) -> {
            t1 = t1.replace("\n", "\na");
            String[] lines = t1.split("\n");

            if (lines.length < 19) {
                lineCount.set(20);
            } else {
                lineCount.set(lines.length + 1);
                windowManager.scrollDown();
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

    private void moreButtonSetup() {
        MenuItem delete = new MenuItem("Törlés");

        delete.setOnAction(actionEvent -> {
            try {
                Api.deleteNote(note.getId());
                alertWait("Sikeres törlés");
                windowManager.changeToNoteList();
            } catch (IOException e) {
                e.printStackTrace();
                alert("Valami miatt nem sikerült törölni a jegyzetet");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        MenuItem test = new MenuItem("Vissza");

        test.setOnAction(actionEvent -> {
            try {
                menuBtn.hide();
                windowManager.changeToNoteList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        menuBtn.getItems().add(delete);
        menuBtn.getItems().add(test);
    }

    public void passData(Note note) {
        this.note = note;
        noteTitle.setText(this.note.getTitle());
        textArea.setText(this.note.getContent());
    }

    @FXML
    public void saveBtnClick() {
        if (note.getId() >= 0) {
            try {
                Api.saveNote(note.getId(), noteTitle.getText(), textArea.getText());
                alert("Sikeres mentés");
            } catch (IOException e) {
                e.printStackTrace();
                alert("Valami okból kifolyólag nem sikerült menteni a jegyzetet.");
            }
        } else {
            try {
                Api.addNote(note.getId(), noteTitle.getText(), textArea.getText());
                alert("Sikeres mentés");
            } catch (IOException e) {
                e.printStackTrace();
                alert("Valami okból kifolyólag nem sikerült menteni a jegyzetet.");
            }
        }

    }

    @FXML
    public void menubuttonAction() {
        new Alert(Alert.AlertType.NONE, "Test", ButtonType.OK).show();
    }
}