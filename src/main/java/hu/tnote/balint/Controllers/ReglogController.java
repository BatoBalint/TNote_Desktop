package hu.tnote.balint.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ReglogController {

    @FXML
    private Button loginBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private Button regBtn;
    @FXML
    private HBox regLogBtnHbox;

    private boolean regSelected;

    public void initialize() {
        uiInit();

        regSelected = false;
        regBtnSelected();
    }

    private void uiInit() {
        HBox.setHgrow(regBtn, Priority.ALWAYS);
        HBox.setHgrow(loginBtn, Priority.ALWAYS);
        regBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setMaxWidth(Double.MAX_VALUE);
    }

    @FXML
    public void regBtnClick() {
        regBtnSelected();
    }

    @FXML
    public void loginBtnClick() {
        loginBtnSelected();
    }

    @FXML
    public void submitBtnClick() {
        test();
    }

    public void regBtnSelected() {
        regBtn.getStyleClass().removeAll("regBtn");
        regBtn.getStyleClass().removeAll("roundTRB");
        loginBtn.getStyleClass().removeAll("loginBtnSelected");
        loginBtn.getStyleClass().removeAll("roundTR");

        regBtn.getStyleClass().add("regBtnSelected");
        regBtn.getStyleClass().add("roundTR");

        loginBtn.getStyleClass().add("loginBtn");
        loginBtn.getStyleClass().add("roundTRL");

        regSelected = true;
    }

    public void loginBtnSelected() {
        loginBtn.getStyleClass().removeAll("loginBtn");
        loginBtn.getStyleClass().removeAll("roundTRL");
        regBtn.getStyleClass().removeAll("regBtnSelected");
        regBtn.getStyleClass().removeAll("roundTR");

        loginBtn.getStyleClass().add("loginBtnSelected");
        loginBtn.getStyleClass().add("roundTR");

        regBtn.getStyleClass().add("regBtn");
        regBtn.getStyleClass().add("roundTRB");

        regSelected = false;
    }

    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    private void test() {
        alert((regSelected) ? "reg selected" : "login selected");
    }
}
