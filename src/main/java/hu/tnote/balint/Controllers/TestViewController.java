package hu.tnote.balint.Controllers;

import hu.tnote.balint.Api;
import hu.tnote.balint.CustomNode.NoteButton;
import hu.tnote.balint.CustomNode.TTElementButton;
import hu.tnote.balint.Note;
import hu.tnote.balint.TimetableElement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalTime;

public class TestViewController extends Controller {

    @FXML
    private VBox rootContainer ;

    public void initialize() {
        TimetableElement tte = new TimetableElement(1,
                1,
                "Friday",
                "Matek",
                "Matematikaegycsodalaatostantargy",
                LocalTime.parse("10:00:00"),
                LocalTime.parse("10:45:00"),
                true);

        TTElementButton tteBtn = new TTElementButton(tte);
        tteBtn.addOnClickListener(v -> {
            try {
                Api.addTTElement(tte);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        rootContainer.getChildren().add(tteBtn.getVBox());
    }
}
