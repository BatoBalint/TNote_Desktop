package hu.tnote.balint.Controllers;

import hu.tnote.balint.Popup;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class TestViewController extends Controller {

    @FXML
    private VBox rootContainer ;
    private StringProperty message;

    public void initialize() {
        Button btn = new Button("Click me");
        btn.setOnAction(e -> {
            btnClick();
        });
        rootContainer.getChildren().add(btn);
    }

    private void btnClick() {
        System.out.println(new Popup().confirm("Biztos"));
    }
}
