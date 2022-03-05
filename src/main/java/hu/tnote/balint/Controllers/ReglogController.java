package hu.tnote.balint.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

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
    @FXML
    private VBox nameContainer;
    @FXML
    private VBox emailContainer;
    @FXML
    private VBox passwordContainer;
    @FXML
    private VBox passwordAgainContainer;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField passwordAgainInput;

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
        alert((regSelected) ? "reg selected" : "login selected");
    }

    public void regBtnSelected() {
        submitBtn.setText("Regisztráció");

        nameContainer.setVisible(true);
        nameContainer.setManaged(true);
        passwordAgainContainer.setVisible(true);
        passwordAgainContainer.setManaged(true);

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
        submitBtn.setText("Bejelentkezés");

        nameContainer.setVisible(false);
        nameContainer.setManaged(false);
        passwordAgainContainer.setVisible(false);
        passwordAgainContainer.setManaged(false);

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
        alert("test");
    }
}
