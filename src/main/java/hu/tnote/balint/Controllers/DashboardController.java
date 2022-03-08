package hu.tnote.balint.Controllers;

import hu.tnote.balint.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class DashboardController {
    @FXML
    private Label idLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label nameLabel;

    private MainController parentController;
    @FXML
    private BorderPane rootContainer;

    public void initialize() {
        int id = User.getId();
        String name = User.getName();
        String email = User.getEmail();

        idLabel.setText(id + "");
        nameLabel.setText(name);
        emailLabel.setText(email);
    }

    public void setParentController(MainController mainController) {
        parentController = mainController;
    }

    @javafx.fxml.FXML
    public void logoutBtnClick() {
        try {
            Api.logout();
            parentController.changeToRegLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
