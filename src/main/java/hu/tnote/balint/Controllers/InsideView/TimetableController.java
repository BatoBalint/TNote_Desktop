package hu.tnote.balint.Controllers.InsideView;

import hu.tnote.balint.Api;
import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.CustomNode.TTElementButton;
import hu.tnote.balint.TimetableElement;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        timetableElementList = timetableElementList.stream().sorted(Comparator.comparing(TimetableElement::getStart)).collect(Collectors.toList());

        List<HBox> hboxList = new ArrayList<>();

        for (TimetableElement tte : timetableElementList) {
            HBox hbox;
            if (counter % 2 == 0) {
                hbox = new HBox();
                hbox.setSpacing(40);
                hboxList.add(hbox);
            }
            TTElementButton tteBtn = new TTElementButton(tte);
            hboxList.get(counter / 2).getChildren().add(tteBtn.getVBox());
            counter++;
        }

        for (HBox h : hboxList) {
            h.setMaxWidth(440);
            VBox.setMargin(h, new Insets(40, 0, 0, 0));
            rootContainer.getChildren().add(h);
        }
    }

}
