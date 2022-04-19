package hu.tnote.balint.Controllers.InsideView;

import hu.tnote.balint.Api;
import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.Popup;
import hu.tnote.balint.TimetableElement;
import hu.tnote.balint.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class TTElementEditorController extends Controller {

    @FXML
    private TextField titleInput;
    @FXML
    private CheckBox repeatingInput;
    @FXML
    private ComboBox<String> dayInput;
    @FXML
    private TextField startInput;
    @FXML
    private TextField endInput;
    @FXML
    private TextField descInput;
    @FXML
    private Button saveBtn;
    private TimetableElement ttelement;
    @FXML
    private Button deleteBtn;

    public void initialize() {
        ttelement = null;
        formatterInit();
    }

    private void formatterInit() {
        startInput.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isReplaced()) {
                return change;
            }
            if (change.isDeleted()) {
                if (change.getAnchor() == 3 || change.getAnchor() == 6) {
                    change.setRange(change.getRangeStart() - 1, change.getControlText().length());
                }
                return change;
            }
            if (change.isAdded() && change.getText().matches("([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])")) {
                return change;
            } else if (change.getText().matches("[0-9]") && change.getControlText().length() < 8) {
                switch (change.getAnchor()) {
                    case 1: if (!(change.getControlText() + change.getText()).matches("[0-2]")) return null;
                        break;
                    case 2: if (!(change.getControlText() + change.getText()).matches("([01][0-9]|2[0-3])")) return null;
                        break;
                    case 3: if (!(change.getControlText() + ":" + change.getText()).matches("([01][0-9]|2[0-3]):([0-5])")) return null;
                        break;
                    case 5: if (!(change.getControlText() + change.getText()).matches("([01][0-9]|2[0-3]):([0-5][0-9])")) return null;
                        break;
                    case 6: if (!(change.getControlText() + ":" + change.getText()).matches("([01][0-9]|2[0-3]):([0-5][0-9]):([0-5])")) return null;
                        break;
                    case 8: if (!(change.getControlText() + change.getText()).matches("([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])")) return null;
                        break;
                }
                if (change.getControlText().length() == 2 || change.getControlText().length() == 5) {
                    change.setText(":" + change.getText());
                    change.setAnchor(change.getAnchor() + 1);
                    change.setCaretPosition(change.getCaretPosition() + 1);
                }
                return change;
            }
            return null;
        }));
        endInput.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isDeleted()) {
                if (change.getAnchor() == 3 || change.getAnchor() == 6) {
                    change.setRange(change.getRangeStart() - 1, change.getControlText().length());
                }
                return change;
            }
            if (change.isAdded() && change.getText().matches("([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])")) {
                return change;
            } else if (change.getText().matches("[0-9]") && change.getControlText().length() < 8) {
                switch (change.getAnchor()) {
                    case 1: if (!(change.getControlText() + change.getText()).matches("[0-2]")) return null;
                        break;
                    case 2: if (!(change.getControlText() + change.getText()).matches("([01][0-9]|2[0-3])")) return null;
                        break;
                    case 3: if (!(change.getControlText() + ":" + change.getText()).matches("([01][0-9]|2[0-3]):([0-5])")) return null;
                        break;
                    case 5: if (!(change.getControlText() + change.getText()).matches("([01][0-9]|2[0-3]):([0-5][0-9])")) return null;
                        break;
                    case 6: if (!(change.getControlText() + ":" + change.getText()).matches("([01][0-9]|2[0-3]):([0-5][0-9]):([0-5])")) return null;
                        break;
                    case 8: if (!(change.getControlText() + change.getText()).matches("([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])")) return null;
                        break;
                }
                if (change.getControlText().length() == 2 || change.getControlText().length() == 5) {
                    change.setText(":" + change.getText());
                    change.setAnchor(change.getAnchor() + 1);
                    change.setCaretPosition(change.getCaretPosition() + 1);
                }
                return change;
            }
            return null;
        }));
    }

    public void setTTElement(TimetableElement ttelement) {
        this.ttelement = ttelement;
        if (ttelement.getId() == -1) deleteBtn.setManaged(false);

        loadDataToInputs();
        addWarningIfEmpty();
    }

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    private void loadDataToInputs() {
        if (ttelement.getId() != -1) {
            titleInput.setText(ttelement.getTitle());
            descInput.setText(ttelement.getDescription());
            repeatingInput.setSelected(ttelement.isRepeating());
            startInput.setText(ttelement.getStart().format(DateTimeFormatter.ISO_LOCAL_TIME));
            endInput.setText(ttelement.getEnd().format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
        dayInput.getSelectionModel().select(ttelement.getDayAsInt());
    }

    private void addWarningIfEmpty() {
        titleTyped();
        comboboxAction();
        startTyped();
        endTyped();
    }

    @FXML
    public void saveBtnClick() {
        String title = titleInput.getText().trim();
        String desc = descInput.getText().trim();
        boolean repeating = repeatingInput.isSelected();
        int day = dayInput.getSelectionModel().getSelectedIndex();
        String start = startInput.getText();
        String end = endInput.getText();
        if (title.length() != 0 &&
                day != -1 &&
                !start.equals("") &&
                !end.equals("")) {
            ttelement.setTitle(title);
            ttelement.setDescription(desc);
            ttelement.setDayAsInt(day);
            if (start.length() < 8) start += "00:00:00".substring(start.length());
            if (end.length() < 8) end += "00:00:00".substring(end.length());
            ttelement.setStart(LocalTime.parse(start));
            ttelement.setEnd(LocalTime.parse(end));
            ttelement.setRepeating(repeating);
            if (ttelement.getId() > -1) {
                try {
                    Api.saveTTElement(ttelement);
                    new Popup("Sikeres mentés").setColor("#22FF44")
                            .setTextColor("#00AA11").setCloseTimer(2000)
                            .withFadeInAndOut().show();
                    windowManager.changeToTimetable();
                } catch (IOException e) {
                    e.printStackTrace();
                    new Popup("Valami okból kifolyólag nem sikerült a mentés")
                            .setColor("#FF2222").setTextColor("990000").show();
                    try {
                        windowManager.changeToTimetable();
                    } catch (IOException ignored) {}
                }
            } else {
                try {
                    Api.addTTElement(ttelement);
                    new Popup("Sikeres mentés").setColor("#22FF44")
                            .setTextColor("#00AA11").setCloseTimer(2000)
                            .withFadeInAndOut().show();
                    windowManager.changeToTimetable();
                } catch (IOException e) {
                    e.printStackTrace();
                    new Popup("Valami okból kifolyólag nem sikerült a mentés")
                            .setColor("#FF2222").setTextColor("990000").show();
                    try {
                        windowManager.changeToTimetable();
                    } catch (IOException ignored) {}
                }
            }
            try {
                windowManager.changeToTimetable();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Popup.hideByIndex(0);
        } else {
            Popup.blink();
        }
    }

    @FXML
    public void titleTyped() {
        if (titleInput.getText().trim().length() == 0) titleInput.getStyleClass().add("red");
        else titleInput.getStyleClass().remove("red");
    }

    @FXML
    public void comboboxAction() {
        if (dayInput.getSelectionModel().getSelectedIndex() == -1) dayInput.getStyleClass().add("red");
        else dayInput.getStyleClass().remove("red");
    }

    @FXML
    public void startTyped() {
        if (startInput.getText().length() == 0) startInput.getStyleClass().add("red");
        else startInput.getStyleClass().remove("red");
    }

    @FXML
    public void endTyped() {
        if (endInput.getText().length() == 0) endInput.getStyleClass().add("red");
        else endInput.getStyleClass().remove("red");
    }

    @FXML
    public void deleteBtnClick() {
        StringBuilder message = new StringBuilder();
        message.append("Biztos, hogy törölni szeretnéd ezt az órát?\n");
        message.append("\"").append(ttelement.getTitle());
        if (!ttelement.getDescription().equals("")) message.append(", ").append(ttelement.getDescription());
        message.append("\"");
        if (new Popup().confirm(message.toString()).get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            Popup.hideAll();
            try {
                Api.deleteTTElement(ttelement.getId());
                new Popup("Sikeres törlés").setColor("#22FF44")
                        .setTextColor("#00AA11").setCloseTimer(2000)
                        .withFadeInAndOut().show();
                windowManager.changeToTimetable();
            } catch (IOException e) {
                e.printStackTrace();
                new Popup("Valami okból kifolyólag nem sikerült a törlés")
                        .setColor("#FF2222").setTextColor("990000").show();
                try {
                    windowManager.changeToTimetable();
                } catch (IOException ignored) {}
            }
        }
    }
}
