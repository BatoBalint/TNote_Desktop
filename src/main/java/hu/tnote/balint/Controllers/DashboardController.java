package hu.tnote.balint.Controllers;

import hu.tnote.balint.Api;
import hu.tnote.balint.Popup;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private ImageView logoImageview;
    //endregion

    public void initialize() {
        uiInit();
    }

    public ScrollPane getScrollPane() {
        return this.contentContainer;
    }

    private void uiInit() {
        //logoImageview.setImage(new Image(getClass().getResource("/hu/tnote/balint/images/tnote_logo_new.png").toExternalForm()));

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
            String response = Api.logout();
            if (!response.split("\\|")[0].equals("200")) {
                new Popup("Valami okból kifolyólag nem sikerült a kijelentkezés").setWidth(440).setTextColor("#770000").setColor("red")
                        .setCloseTimer(4000).withFadeInAndOut().show();
            } else {
                new Popup("Sikeres kijelentkezés").setWidth(400).setColor("#22FF44")
                        .setTextColor("#00AA11").setCloseTimer(3000).withFadeInAndOut().show();
                windowManager.changeToRegLog();
            }
        } catch (IOException e) {
            new Popup("Valami okból kifolyólag nem sikerült a kijelentkezés").setWidth(440).setTextColor("#770000").setColor("red")
                    .setCloseTimer(4000).withFadeInAndOut().show();
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
            windowManager.changeToTimetable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void settingsBtnClick() {
        resetDashboardSelection();
        settingsBtn.getStyleClass().add(0, "btnFocus");
        try {
            windowManager.changeToFxml("/hu/tnote/balint/InsideView/settings-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void profileBtnClick() {
        resetDashboardSelection();
        profileBtn.getStyleClass().add(0, "btnFocus");
        try {
            windowManager.changeToFxml("/hu/tnote/balint/InsideView/profile-view.fxml");
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
