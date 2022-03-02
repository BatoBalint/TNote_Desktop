package hu.tnote.balint.Controllers;

import hu.tnote.balint.App;
import hu.tnote.balint.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.prefs.Preferences;

public class MainController {

    @FXML
    private VBox rootContainer;
    @FXML
    private Label dataLabel;
    Preferences pref;
    private String PAT;

    public void initialize() {
        pref = Preferences.userRoot();
        PAT = pref.get("TNotePAT", "");
        if (PAT.isEmpty()) {
            try {
                changeToRegLog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                changeToDashboard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void regLogBtnClick() {

    }

    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    private void test() {
        alert("test");
    }

    private void changeToRegLog() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("reglog-view.fxml"));
            VBox child = loader.load();
            RegLogController controller = loader.getController();
            controller.setParentController(this);
            rootContainer.getChildren().setAll(child);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("dashboard.fxml")).load();
            RegLogController controller = loader.getController();
            controller.setParentController(this);
            VBox child = loader.load();
            rootContainer.getChildren().setAll(child);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}