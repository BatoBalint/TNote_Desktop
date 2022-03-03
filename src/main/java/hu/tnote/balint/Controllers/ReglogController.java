package hu.tnote.balint.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    public void initialize() {
        uiInit();
    }

    private void uiInit() {
        HBox.setHgrow(regBtn, Priority.ALWAYS);
        HBox.setHgrow(loginBtn, Priority.ALWAYS);
        regBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setMaxWidth(Double.MAX_VALUE);
    }
}
