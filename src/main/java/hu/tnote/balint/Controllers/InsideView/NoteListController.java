package hu.tnote.balint.Controllers.InsideView;

import hu.tnote.balint.*;
import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.CustomNode.NoteButton;
import javafx.application.Platform;
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

    public void initialize() {
        uiInit();
    }

    private void uiInit() {}

    public void setWindowManager(WindowManager windowManager) {
        super.setWindowManager(windowManager);

        new Thread(this::loadNotes).start();
    }

    private void loadNotes() {
        int counter = 0;
        List<Note> notes = new ArrayList<>();
        try {
            notes = Api.getNotes();
        } catch (IOException e) {
            Platform.runLater(() -> new Popup("Nem sikerült csatlakozni a szerverhez").setTextColor("#770000").setColor("red").setCloseTimer(3000).withFadeInAndOut().show());
        } catch (ParseException e) {
            Platform.runLater(() -> new Popup("Hiba történt a jegyzetek betöltésekor").setTextColor("#770000").setColor("red").setCloseTimer(3000).withFadeInAndOut().show());
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
            Platform.runLater(() -> {
                rootContainer.getChildren().add(h);
            });
        }
    }
}
