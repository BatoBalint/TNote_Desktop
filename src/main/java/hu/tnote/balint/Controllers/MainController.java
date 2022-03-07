package hu.tnote.balint.Controllers;

import hu.tnote.balint.App;
import hu.tnote.balint.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class MainController {

    @FXML
    private VBox rootContainer;
    private String PAT; //Personal access token

    public void initialize() {
        PAT = Preferences.userRoot().get("TNotePATasd", "");
        if (PAT.isEmpty()) {
            try {
                changeToRegLog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            User.setToken(PAT);
            try {
                changeToDashboard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changeToRegLog() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("reglog-view.fxml"));
            BorderPane child = loader.load();
            ReglogController reglogController = loader.getController();
            //System.out.println((reglogController != null) ? "not null" : "null");
            reglogController.setParentController(this);
            rootContainer.getChildren().setAll(child);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("dashboard.fxml"));
            VBox child = loader.load();
            rootContainer.getChildren().setAll(child);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    private void test() {
        alert("test");
    }
}