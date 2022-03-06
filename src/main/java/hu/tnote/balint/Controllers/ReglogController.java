package hu.tnote.balint.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ReglogController {

    //region FXML variables
    @FXML
    private Button loginBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private Button regBtn;
    @FXML
    private HBox regLogBtnHbox;
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
    //endregion

    private boolean regSelected;

    public void initialize() {
        uiInit();

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
        String text = String.format("Name: %s\nEmail: %s\nPass: %s", nameInput.getText(), emailInput.getText(), passwordInput.getText());
        //alert((regSelected) ? "reg selected" : "login selected");
        alert(text);
    }

    public void regBtnSelected() {
        submitBtn.setText("Regisztráció");

        nameContainer.setVisible(true);
        nameContainer.setManaged(true);
        passwordAgainContainer.setVisible(true);
        passwordAgainContainer.setManaged(true);

        regBtn.getStyleClass().removeAll("regBtn");
        regBtn.getStyleClass().removeAll("roundTRB");
        regBtn.getStyleClass().removeAll("secondaryTextColor");

        regBtn.getStyleClass().add("regBtnSelected");
        regBtn.getStyleClass().add("roundTR");
        regBtn.getStyleClass().add("primaryTextColor");

        loginBtn.getStyleClass().removeAll("loginBtnSelected");
        loginBtn.getStyleClass().removeAll("roundTR");
        loginBtn.getStyleClass().removeAll("primaryTextColor");

        loginBtn.getStyleClass().add("loginBtn");
        loginBtn.getStyleClass().add("roundTRL");
        loginBtn.getStyleClass().add("secondaryTextColor");


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
        loginBtn.getStyleClass().removeAll("secondaryTextColor");

        loginBtn.getStyleClass().add("loginBtnSelected");
        loginBtn.getStyleClass().add("roundTR");
        loginBtn.getStyleClass().add("primaryTextColor");

        regBtn.getStyleClass().removeAll("regBtnSelected");
        regBtn.getStyleClass().removeAll("roundTR");
        regBtn.getStyleClass().removeAll("primaryTextColor");

        regBtn.getStyleClass().add("regBtn");
        regBtn.getStyleClass().add("roundTRB");
        regBtn.getStyleClass().add("secondaryTextColor");

        regSelected = false;
    }

    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    private void test() {
        alert("test");
    }
}
