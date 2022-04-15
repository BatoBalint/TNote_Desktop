package hu.tnote.balint.Controllers.InsideView;

import hu.tnote.balint.Api;
import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.CustomNode.TTElementButton;
import hu.tnote.balint.TimetableElement;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableController extends Controller {
    @FXML
    private VBox rootContainer;
    @FXML
    public HBox timetableContainer;

    public void initialize() {
        timetableContainer.setSpacing(20);
//        ttTest();
        loadTimetables();
    }

    private void loadTimetables() {
        String[] dayNames = "Hétfő Kedd Szerda Csütörtök Péntek Szomat Vasárnap".split(" ");
        String[] dayNamesEN = "Monday Tuesday Wednesday Thursday Friday Saturday Sunday".split(" ");
        HashMap<String, Integer> dayToNum = new HashMap<>();
        for (int i = 0; i < dayNamesEN.length; i++) {
            dayToNum.put(dayNamesEN[i], i);
        }

        List<TimetableElement> timetableElementList = new ArrayList<>();
        try {
            timetableElementList = Api.getTimetableElements();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        timetableElementList.sort(Comparator.comparing(a -> dayToNum.getOrDefault(a.getDay(), 6)));

        int today = LocalDate.now().getDayOfWeek().getValue() - 1;

        for (int i = 0; i < dayNames.length; i++) {
            VBox a = new VBox();
            if (i == today) a.setStyle("-fx-background-color: darkgrey; -fx-background-radius: 3; -fx-padding: 5");
            Label day = new Label(dayNames[i]);
            day.setStyle("-fx-font-size: 125%");
            a.getChildren().add(day);
            a.setSpacing(5);
            a.getChildren().add(new TTElementButton(new TimetableElement(-1, -1, null, null, null, null, null, true)).getVBox());
            timetableContainer.getChildren().add(a);
        }
        for (TimetableElement e: timetableElementList) {
            ((VBox) timetableContainer.getChildren().get(dayToNum.get(e.getDay()))).getChildren().add(new TTElementButton(e).getVBox());
        }
    }
}
