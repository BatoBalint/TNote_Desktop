package hu.tnote.balint.Controllers;

import hu.tnote.balint.Api;
import hu.tnote.balint.App;
import hu.tnote.balint.User;
import hu.tnote.balint.WindowManager;
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
    private WindowManager windowManager;

    public void initialize() {
        windowManager = new WindowManager(rootContainer);

        Preferences prefs = Preferences.userRoot();
        String PAT = prefs.get("TNotePAT", "");     //Personal access token
        rememberLogin = prefs.getBoolean("TNoteRememberLogin", true);

        if (PAT.isEmpty()) {
            try {
                windowManager.changeToRegLog();
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
                windowManager.changeToDashboard();
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

    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    private void test() {
        alert("test");
    }
}