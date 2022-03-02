package hu.tnote.balint.Controllers;

import hu.tnote.balint.User;
import javafx.scene.control.Label;

public class DashboardController {
    @javafx.fxml.FXML
    private Label idLabel;
    @javafx.fxml.FXML
    private Label emailLabel;
    @javafx.fxml.FXML
    private Label nameLabel;

    public void initialize() {
        int id = User.getId();
        String name = User.getName();
        String email = User.getEmail();

        idLabel.setText(id + "");
        nameLabel.setText(name);
        emailLabel.setText(email);
    }
}
