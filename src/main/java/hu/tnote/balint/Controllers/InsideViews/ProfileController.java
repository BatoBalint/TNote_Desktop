package hu.tnote.balint.Controllers.InsideViews;

import hu.tnote.balint.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfileController {

    @FXML
    private Label emailLabel;
    @FXML
    private Label nameLabel;

    public void initialize() {
        nameLabel.setText(User.getName());
        emailLabel.setText(User.getEmail());
    }

}
