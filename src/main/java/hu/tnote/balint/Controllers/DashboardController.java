package hu.tnote.balint.Controllers;

import hu.tnote.balint.Controllers.InsideViews.NoteListController;
import hu.tnote.balint.Controllers.InsideViews.ProfileController;
import hu.tnote.balint.Controllers.InsideViews.SettingsController;
import hu.tnote.balint.Note;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {

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
    //endregion

    private MainController parentController;

    public void initialize() {
        uiInit();


    }

    private void uiInit() {
        HBox.setHgrow(logoutBtn, Priority.ALWAYS);
        HBox.setHgrow(noteBtn, Priority.ALWAYS);
        HBox.setHgrow(settingsBtn, Priority.ALWAYS);
        HBox.setHgrow(profileBtn, Priority.ALWAYS);

        VBox.setVgrow(controlsContainer, Priority.ALWAYS);


    }

    public void setParentController(MainController mainController) {
        parentController = mainController;
    }

    @FXML
    public void logoutBtnClick() {
        try {
            Api.logout();
            parentController.changeToRegLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void noteBtnClick() {
        resetDashboardSelection();
        noteBtn.getStyleClass().add(0, "btnFocus");
        FXMLLoader loader = loadFxmlToContentContainer("/hu/tnote/balint/insideViews/note-list-view.fxml");
        NoteListController noteListController = loader.getController();
        noteListController.passScrollPane(contentContainer);
    }

    @FXML
    public void settingsBtnClick() {
        resetDashboardSelection();
        settingsBtn.getStyleClass().add(0, "btnFocus");
        FXMLLoader loader = loadFxmlToContentContainer("/hu/tnote/balint/insideViews/settings-view.fxml");
        SettingsController noteListController = loader.getController();
    }

    @FXML
    public void profileBtnClick() {
        resetDashboardSelection();
        profileBtn.getStyleClass().add(0, "btnFocus");
        FXMLLoader loader = loadFxmlToContentContainer("/hu/tnote/balint/insideViews/profile-view.fxml");
        ProfileController noteListController = loader.getController();
    }

    private void resetDashboardSelection() {
        profileBtn.getStyleClass().remove("btnFocus");
        settingsBtn.getStyleClass().remove("btnFocus");
        noteBtn.getStyleClass().remove("btnFocus");
    }

    private FXMLLoader loadFxmlToContentContainer(String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        try {
            VBox vbox = loader.load();
            contentContainer.setContent(vbox);
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    //region Tools
    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    private void test() {
        alert("test");
    }
    //endregion
}
