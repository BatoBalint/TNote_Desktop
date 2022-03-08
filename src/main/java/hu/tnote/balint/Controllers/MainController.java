package hu.tnote.balint.Controllers;

import hu.tnote.balint.App;
import hu.tnote.balint.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.prefs.Preferences;

public class MainController {

    @FXML
    private VBox rootContainer;
    private String PAT; //Personal access token

    public void initialize() {
        //Preferences.userRoot().remove("TNotePAT");
        PAT = Preferences.userRoot().get("TNotePAT", "");
        if (PAT.isEmpty()) {
            try {
                changeToRegLog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            User.setToken(PAT);
            User.setUser(
                    Preferences.userRoot().getInt("TNoteUserId", -1),
                    Preferences.userRoot().get("TNoteUserName", ""),
                    Preferences.userRoot().get("TNoteUserEmail", ""));
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
            BorderPane child = loader.load();
            child.prefHeightProperty().bind(rootContainer.prefHeightProperty());
            child.prefWidthProperty().bind(rootContainer.prefWidthProperty());
            DashboardController dashboardController = loader.getController();
            dashboardController.setParentController(this);
            rootContainer.getChildren().setAll(child);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    private void test() {
        alert("test");
    }
}