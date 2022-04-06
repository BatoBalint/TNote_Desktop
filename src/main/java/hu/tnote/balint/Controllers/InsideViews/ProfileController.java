package hu.tnote.balint.Controllers.InsideViews;

import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfileController extends Controller {

    @FXML
    private Label emailLabel;
    @FXML
    private Label nameLabel;

    public void initialize() {
        nameLabel.setText(User.getName());
        emailLabel.setText(User.getEmail());
    }
}
