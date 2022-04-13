package hu.tnote.balint.Controllers.InsideView;

import hu.tnote.balint.Api;
import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.CustomNode.NoteButton;
import hu.tnote.balint.Note;
import hu.tnote.balint.TimetableElement;
import hu.tnote.balint.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimetableController extends Controller {
    @FXML
    private VBox rootContainer;

    public void initialize() {
        loadTimetables();
    }

    private void loadTimetables() {
        int counter = 0;
        List<TimetableElement> timetableElementList = new ArrayList<>();
        try {
            timetableElementList = Api.getTimetableElements();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        List<HBox> hboxList = new ArrayList<>();

        for (TimetableElement tte : timetableElementList) {
            HBox hbox;
            if (counter % 2 == 0) {
                hbox = new HBox();
                hbox.setSpacing(40);
                hboxList.add(hbox);
            }
            Label label = new Label(tte.getTitle());
            hboxList.get(counter / 2).getChildren().add(label);
            counter++;
        }

        for (HBox h : hboxList) {
            h.setMaxWidth(440);
            VBox.setMargin(h, new Insets(40, 0, 0, 0));
            rootContainer.getChildren().add(h);
        }
    }

}
