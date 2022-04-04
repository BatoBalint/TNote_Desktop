package hu.tnote.balint.Controllers.InsideViews;

import hu.tnote.balint.Api;
import hu.tnote.balint.CustomNode.NoteButton;
import hu.tnote.balint.Note;
import hu.tnote.balint.User;
import hu.tnote.balint.WindowManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoteListController {

    @FXML
    public VBox rootContainer;
    private ScrollPane scrollPane;
    private WindowManager windowManager;

    public void initialize() {
        uiInit();

        loadNotes();
    }

    private void uiInit() {
    }

    public void setScrollPane (ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    private void loadNotes() {
        int counter = 0;
        List<Note> notes = new ArrayList<>();
        try {
            notes = Api.getNotes();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        notes.add(new Note(-1, User.getId(), "", ""));

        List<HBox> hboxList = new ArrayList<>();

        for (Note n : notes) {
            HBox hbox;
            if (counter % 2 == 0) {
                hbox = new HBox();
                hbox.setSpacing(40);
                hboxList.add(hbox);
            }
            NoteButton nBtn = new NoteButton(n);
            nBtn.setOnAction(v -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/hu/tnote/balint/insideViews/note-editor-view.fxml"));
                    VBox child = loader.load();
                    NoteEditorController c = loader.getController();
                    c.passData(nBtn.getNote(), scrollPane);
                    c.setWindowManager(windowManager);
                    rootContainer.getChildren().setAll(child);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            hboxList.get(counter / 2).getChildren().add(nBtn.get());
            counter++;
        }

        for (HBox h : hboxList) {
            h.setMaxWidth(440);
            VBox.setMargin(h, new Insets(40, 0, 0, 0));
            rootContainer.getChildren().add(h);
        }
    }

    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }
}
