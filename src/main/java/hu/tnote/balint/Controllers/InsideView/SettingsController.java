package hu.tnote.balint.Controllers.InsideView;

import hu.tnote.balint.Controllers.Controller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.util.prefs.Preferences;

public class SettingsController extends Controller {

    @FXML
    private CheckBox stayLoggedInCheckbox;
    @FXML
    private VBox rootContainer;

    public void initialize() {
        stayLoggedInCheckbox.setSelected(Preferences.userRoot().getBoolean("TNoteRememberLogin", false));
        stayLoggedInCheckbox.setOnAction(this::stayLoggedInCheckboxAction);
    }

    private void stayLoggedInCheckboxAction(Event e) {
        Preferences.userRoot().putBoolean("TNoteRememberLogin", stayLoggedInCheckbox.isSelected());
    }
}
