package hu.tnote.balint.Controllers;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

public class RegLogController {

    @FXML
    private VBox rootContainer;
    private MainController parentController;
    @FXML
    private Button loginBtn;
    @FXML
    private Button regBtn;
    @FXML
    private HBox regLogBtnContainer;

    public void initialize() {
        uiInit();
    }

    private void uiInit() {
        regLogBtnContainer.setHgrow(regBtn, Priority.ALWAYS);
        regBtn.setMaxWidth(100);
        regLogBtnContainer.setHgrow(loginBtn, Priority.ALWAYS);
        loginBtn.setMaxWidth(100);
    }

    @FXML
    public void backBtnClick() {

    }

    public void setParentController(MainController controller) {
        parentController = controller;
    }
}
