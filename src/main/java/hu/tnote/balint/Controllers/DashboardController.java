package hu.tnote.balint.Controllers;

import hu.tnote.balint.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.lang.reflect.MalformedParametersException;

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
        int id = User.getId();
        String name = User.getName();
        String email = User.getEmail();
        welcomeText.setText(String.format("Üdv újra %s", name));

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
        alert("note");
    }

    @FXML
    public void settingsBtnClick() {
        alert("settings");
    }

    @FXML
    public void profileBtnClick() {
        alert("profile");
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
