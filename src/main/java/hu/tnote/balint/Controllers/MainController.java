package hu.tnote.balint.Controllers;

import hu.tnote.balint.Api;
import hu.tnote.balint.User;
import hu.tnote.balint.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class MainController extends Controller {

    @FXML
    private VBox rootContainer;

    private Stage stage;

    public void initialize() {
        setWindowManager(new WindowManager(rootContainer));

        Preferences prefs = Preferences.userRoot();
        String PAT = prefs.get("TNotePAT", "");     //Personal access token

        if (PAT.isEmpty()) {
            try {
                windowManager.changeToRegLog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            User.setToken(PAT);
            User.setCreatedAt(Preferences.userRoot().get("TNoteUserCreatedAt", ""));
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
            if (!Preferences.userRoot().getBoolean("TNoteRememberLogin", false)) {
                try {
                    User.setToken(Preferences.userRoot().get("TNotePAT", ""));
                    Api.logout();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}