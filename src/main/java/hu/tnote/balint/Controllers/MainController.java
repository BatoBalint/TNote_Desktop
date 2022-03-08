package hu.tnote.balint.Controllers;

import hu.tnote.balint.App;
import hu.tnote.balint.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class MainController {

    @FXML
    private VBox rootContainer;

    private boolean rememberLogin;
    private Stage stage;

    public void initialize() {
        Preferences prefs = Preferences.userRoot();
        String PAT = prefs.get("TNotePAT", "");     //Personal access token
        rememberLogin = prefs.getBoolean("TNoteRememberLogin", true);

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

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(v -> {
            if (!rememberLogin) {
                try {
                    Api.logout();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void changeToRegLog() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("reglog-view.fxml"));
            BorderPane child = loader.load();
            ReglogController reglogController = loader.getController();
            reglogController.setParentController(this);
            VBox.setVgrow(child, Priority.ALWAYS);
            rootContainer.getChildren().setAll(child);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("dashboard.fxml"));
            GridPane child = loader.load();
            DashboardController dashboardController = loader.getController();
            dashboardController.setParentController(this);
            VBox.setVgrow(child, Priority.ALWAYS);
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