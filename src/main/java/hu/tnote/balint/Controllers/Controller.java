package hu.tnote.balint.Controllers;

import hu.tnote.balint.WindowManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Controller {
    protected WindowManager windowManager;

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    protected void alert(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).show();
    }

    protected void test() {
        alert("test");
    }

    protected void alertWait(String text) {
        new Alert(Alert.AlertType.NONE, text, ButtonType.OK).showAndWait();
    }


}
