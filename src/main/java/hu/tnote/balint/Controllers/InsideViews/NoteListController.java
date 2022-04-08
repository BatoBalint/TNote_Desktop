package hu.tnote.balint.Controllers.InsideViews;

import hu.tnote.balint.Api;
import hu.tnote.balint.Controllers.Controller;
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

public class NoteListController extends Controller {

    @FXML
    public VBox rootContainer;
    private ScrollPane scrollPane;

    public void initialize() {
        uiInit();

        loadNotes();
    }

    private void uiInit() {
    }

    public void setScrollPane (ScrollPane scrollPane) {
        this.scrollPane = scrollPane;

        final double SPEED = 0.005;
        this.scrollPane.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * SPEED;
            this.scrollPane.setVvalue(this.scrollPane.getVvalue() - deltaY);
        });
    }

    private void loadNotes() {
        int counter = 0;
        List<Note> notes = new ArrayList<>();
        try {
            notes = Api.getNotes();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 1; i++) {
            notes.add(new Note(-1, User.getId(), "", ""));
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
                try {
                    windowManager.changeToNoteEditor(nBtn.getNote());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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
}
