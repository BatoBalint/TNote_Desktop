package hu.tnote.balint.Controllers;

import hu.tnote.balint.Api;
import hu.tnote.balint.Popup;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ReglogController extends Controller {

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
    @FXML
    private Button showPassBtn;
    @FXML
    private Button showPassBtnAgain;
    //endregion

    private boolean regSelected;
    private SimpleBooleanProperty showPassword;
    private SimpleBooleanProperty showPasswordAgain;

    public void initialize() {
        showPassword = new SimpleBooleanProperty();
        showPassword.set(false);
        showPasswordAgain = new SimpleBooleanProperty();
        showPasswordAgain.set(false);

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
        passwordAgainInputText.visibleProperty().bind(showPasswordAgain);
        passwordAgainInputText.managedProperty().bind(showPasswordAgain);

        passwordInputDots.visibleProperty().bind(showPassword.not());
        passwordInputDots.managedProperty().bind(showPassword.not());
        passwordAgainInputDots.visibleProperty().bind(showPasswordAgain.not());
        passwordAgainInputDots.managedProperty().bind(showPasswordAgain.not());

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
        if (regSelected) {
            String name = nameInput.getText().trim();
            String email = emailInput.getText().trim();
            String pass = passwordInputText.getText().trim();
            String passAgain = passwordAgainInputText.getText().trim();

            boolean ok = checkInputs(name, email, pass, passAgain);

            if (ok) {
                try {
                    int statuscode = Api.register(name, email, pass);
                    if (statuscode / 100 == 2) {
                        clearInputs();
                        windowManager.changeToDashboard();
                        new Popup("Sikeres regisztráció").setTextColor("#00EE00").setColor("green").setCloseTimer(4000).withFadeInAndOut().show();
                    }
                } catch (Exception e) {
                    new Popup("Valami okból kifolyólag nem sikerült a regisztráció").setTextColor("#550000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
                }
            }
        } else {
            String email = emailInput.getText().trim();
            String pass = passwordInputText.getText().trim();

            if (email.isEmpty()) {
                new Popup("Az email cím megadása kötelező").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
                return;
            } else if (pass.isEmpty()) {
                new Popup("Az jelszó megadása kötelező").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
                return;
            }

            try {
                int statuscode = Api.login(email, pass);
                if (statuscode / 100 == 2) {
                    clearInputs();
                    windowManager.changeToDashboard();
                }
                if (statuscode == 401) {
                    new Popup("Helytelen email cím vagy jelszó").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
                }
            } catch (IOException | ParseException e) {
                new Popup("Valami okból kifolyólag nem sikerült a bejelentkezés").setTextColor("#550000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
            }
        }
    }

    private boolean checkInputs(String name, String email, String pass, String passAgain) {
        if (name.isEmpty()) {
            new Popup("A név mező nem lehet üres").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
            return false;
        } else if (name.length() < 5) {
            new Popup("A névnek legalább 5 karakternek kell lennie").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
            return false;
        } else if (name.length() > 255) {
            new Popup("A név nem lehet hosszabb 255 karakternél").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
            return false;
        }

        if (email.isEmpty()) {
            new Popup("Az email nem lehet üres").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
            return false;
        } else if (!emailFormatCheck(email)) {
            new Popup("Az email nem jó formátúmú").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
            return false;
        }

        if (pass.isEmpty()) {
            new Popup("A jelszó nem lehet üres").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
            return false;
        } else if (pass.length() < 8) {
            new Popup("A jelszónak elegalább 8 karakternek kell lennie").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
            return false;
        } else if (!pass.equals(passAgain)) {
            new Popup("A két jelszó nem egyezik meg").setTextColor("#770000").setColor("red").setCloseTimer(4000).withFadeInAndOut().show();
            return false;
        }

        return true;
    }

    private boolean emailFormatCheck(String email) {
        String[] split = email.split("@");
        if (split.length != 2 || split[0].length() == 0) return false;

        String[] split2 = split[1].split("\\.");
        if (split2.length != 2) return false;

        return split2[0].length() >= 1 && split2[1].length() >= 1;
    }

    private void clearInputs() {
        nameInput.setText("");
        emailInput.setText("");
        passwordInputText.setText("");
        passwordAgainInputText.setText("");
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
            regBtn.getStyleClass().removeAll("text-secondary");

            regBtn.getStyleClass().add("regBtnSelected");
            regBtn.getStyleClass().add("roundTR");
            regBtn.getStyleClass().add("text-primary");

            loginBtn.getStyleClass().removeAll("loginBtnSelected");
            loginBtn.getStyleClass().removeAll("roundTR");
            loginBtn.getStyleClass().removeAll("text-primary");

            loginBtn.getStyleClass().add("loginBtn");
            loginBtn.getStyleClass().add("roundTRL");
            loginBtn.getStyleClass().add("text-secondary");

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
            loginBtn.getStyleClass().removeAll("text-secondary");

            loginBtn.getStyleClass().add("loginBtnSelected");
            loginBtn.getStyleClass().add("roundTR");
            loginBtn.getStyleClass().add("text-primary");

            regBtn.getStyleClass().removeAll("regBtnSelected");
            regBtn.getStyleClass().removeAll("roundTR");
            regBtn.getStyleClass().removeAll("text-primary");

            regBtn.getStyleClass().add("regBtn");
            regBtn.getStyleClass().add("roundTRB");
            regBtn.getStyleClass().add("text-secondary");

            regSelected = false;
        }
    }

    @FXML
    public void showPassBtnClick() {
        if (!showPassword.get()) {
            showPassBtn.getStyleClass().removeAll("showPassBtn");
            showPassBtn.getStyleClass().add("showPassBtnClicked");
        } else {
            showPassBtn.getStyleClass().removeAll("showPassBtnClicked");
            showPassBtn.getStyleClass().add("showPassBtn");
        }

        showPassword.set(!showPassword.get());
    }

    @FXML
    public void showPassAgainBtnClick() {
        if (!showPasswordAgain.get()) {
            showPassBtnAgain.getStyleClass().removeAll("showPassBtn");
            showPassBtnAgain.getStyleClass().add("showPassBtnClicked");
        } else {
            showPassBtnAgain.getStyleClass().removeAll("showPassBtnClicked");
            showPassBtnAgain.getStyleClass().add("showPassBtn");
        }

        showPasswordAgain.set(!showPasswordAgain.get());
    }
}
