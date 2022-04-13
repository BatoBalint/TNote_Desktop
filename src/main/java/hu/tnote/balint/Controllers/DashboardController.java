package hu.tnote.balint.Controllers;

import hu.tnote.balint.Api;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class DashboardController extends Controller {

    //region FXML-s
    @FXML
    public Label welcomeText;
    @FXML
    public Button logoutBtn;
    @FXML
    public Button settingsBtn;
    @FXML
    public Button profileBtn;
    @FXML
    public ScrollPane contentContainer;
    @FXML
    private GridPane rootContainer;
    @FXML
    private Button noteBtn;
    @FXML
    private VBox controlsContainer;
    @FXML
    private VBox logoutVbox;
    @FXML
    private Button timetableBtn;
    //endregion

    public void initialize() {
        uiInit();
    }

    public ScrollPane getScrollPane() {
        return this.contentContainer;
    }

    private void uiInit() {
        HBox.setHgrow(logoutBtn, Priority.ALWAYS);
        HBox.setHgrow(noteBtn, Priority.ALWAYS);
        HBox.setHgrow(settingsBtn, Priority.ALWAYS);
        HBox.setHgrow(profileBtn, Priority.ALWAYS);
        HBox.setHgrow(timetableBtn, Priority.ALWAYS);

        VBox.setVgrow(controlsContainer, Priority.ALWAYS);
    }

    @FXML
    public void logoutBtnClick() {
        try {
            Api.logout();
            windowManager.changeToRegLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void noteBtnClick() {
        resetDashboardSelection();
        noteBtn.getStyleClass().add(0, "btnFocus");
        try {
            windowManager.changeToNoteList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void timetableBtnClick() {
        resetDashboardSelection();
        timetableBtn.getStyleClass().add(0, "btnFocus");
        try {
            windowManager.changeToFxml("/hu/tnote/balint/insideViews/timetable-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void settingsBtnClick() {
        resetDashboardSelection();
        settingsBtn.getStyleClass().add(0, "btnFocus");
        try {
            windowManager.changeToFxml("/hu/tnote/balint/insideViews/settings-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void profileBtnClick() {
        resetDashboardSelection();
        profileBtn.getStyleClass().add(0, "btnFocus");
        try {
            windowManager.changeToFxml("/hu/tnote/balint/insideViews/profile-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetDashboardSelection() {
        timetableBtn.getStyleClass().remove("btnFocus");
        profileBtn.getStyleClass().remove("btnFocus");
        settingsBtn.getStyleClass().remove("btnFocus");
        noteBtn.getStyleClass().remove("btnFocus");
    }
}
