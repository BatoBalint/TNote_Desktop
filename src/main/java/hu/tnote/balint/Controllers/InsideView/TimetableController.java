package hu.tnote.balint.Controllers.InsideView;

import hu.tnote.balint.Api;
import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.CustomNode.TTElementButton;
import hu.tnote.balint.TimetableElement;
import hu.tnote.balint.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableController extends Controller {
    @FXML
    private VBox rootContainer;
    @FXML
    public HBox timetableContainer;
    private int usedTtid;

    public void initialize() {
        timetableContainer.setSpacing(20);
    }

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;

        loadTimetables();
    }

    private void loadTimetables() {
        String[] dayNames = "Hétfő Kedd Szerda Csütörtök Péntek Szomat Vasárnap".split(" ");

        List<TimetableElement> timetableElementList = new ArrayList<>();
        try {
            timetableElementList = Api.getTimetableElements();
            usedTtid = Api.getTimetableIds().get(0);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        timetableElementList = timetableElementList.stream()
                .sorted(Comparator.comparing(TimetableElement::getDayAsInt))
                .sorted(Comparator.comparing(TimetableElement::getStart))
                .collect(Collectors.toList());

        int today = LocalDate.now().getDayOfWeek().getValue() - 1;

        for (int i = 0; i < dayNames.length; i++) {
            VBox a = new VBox();
            if (i == today) a.setStyle("-fx-background-color: darkgrey; -fx-background-radius: 3; -fx-padding: 5");
            Label day = new Label(dayNames[i]);
            day.setStyle("-fx-font-size: 125%");
            a.getChildren().add(day);
            a.setSpacing(5);
            TTElementButton tteBtn = new TTElementButton(new TimetableElement(-1, usedTtid, i));
            tteBtn.setWindowManager(this.windowManager);
            a.getChildren().add(tteBtn.getVBox());

            timetableContainer.getChildren().add(a);
        }
        for (TimetableElement e: timetableElementList) {
            TTElementButton tteBtn = new TTElementButton(e);
            tteBtn.setWindowManager(this.windowManager);
            ((VBox) timetableContainer.getChildren().get(e.getDayAsInt())).getChildren().add(tteBtn.getVBox());
        }
    }
}
