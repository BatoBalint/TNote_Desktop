package hu.tnote.balint.Controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
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
    private PasswordField passwordAgainInputDots;
    @FXML
    private TextField passwordAgainInputText;
    @FXML
    private PasswordField passwordInputDots;
    @FXML
    private TextField passwordInputText;
    //endregion

    private boolean regSelected;
    private SimpleBooleanProperty showPassword;

    public void initialize() {
        showPassword = new SimpleBooleanProperty();
        showPassword.set(false);

        uiInit();

        regBtnSelected();
    }

    private void uiInit() {
        HBox.setHgrow(regBtn, Priority.ALWAYS);
        HBox.setHgrow(loginBtn, Priority.ALWAYS);
        regBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setMaxWidth(Double.MAX_VALUE);

        passwordInputText.visibleProperty().bind(showPassword);
        passwordInputText.managedProperty().bind(showPassword);
        passwordAgainInputText.visibleProperty().bind(showPassword);
        passwordAgainInputText.managedProperty().bind(showPassword);

        passwordInputDots.visibleProperty().bind(showPassword.not());
        passwordInputDots.managedProperty().bind(showPassword.not());
        passwordAgainInputDots.visibleProperty().bind(showPassword.not());
        passwordAgainInputDots.managedProperty().bind(showPassword.not());

        passwordInputText.textProperty().bindBidirectional(passwordInputDots.textProperty());
        passwordAgainInputText.textProperty().bindBidirectional(passwordAgainInputDots.textProperty());
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
        //String text = String.format("Name: %s\nEmail: %s\nPass: %s", nameInput.getText(), emailInput.getText(), passwordInputText.getText());
        //alert((regSelected) ? "reg selected" : "login selected");
        //alert(text);
        showPassword.set(!showPassword.get());
    }

    //mainly styles
    public void regBtnSelected() {
        if (!regSelected) {

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
    }

    //mainly styles
    public void loginBtnSelected() {
        if (regSelected) {
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
    }

    private void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    private void test() {
        alert("test");
    }
}
