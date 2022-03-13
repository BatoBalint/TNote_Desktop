package hu.tnote.balint.Controllers.InsideViews;

import hu.tnote.balint.Controllers.Api;
import hu.tnote.balint.CustomNode.NoteButton;
import hu.tnote.balint.Note;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoteListController {

    @FXML
    public VBox rootContainer;

    public void initialize() {
        uiInit();

        loadNotes();
    }

    private void uiInit() {
        rootContainer.setPadding(new Insets(40, 0, 40, 0));
    }

    private void loadNotes() {
        int counter = 0;
        List<Note> notes = new ArrayList<>();
        try {
            notes = Api.getNotes();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

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
                new Alert(Alert.AlertType.NONE, nBtn.getNoteContent(), ButtonType.OK).show();
            });
            hboxList.get(counter / 2).getChildren().add(nBtn.get());
            counter++;
        }

        for (HBox h : hboxList) {
            h.setMaxWidth(440);
            rootContainer.getChildren().add(h);
        }
    }

    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }
}
