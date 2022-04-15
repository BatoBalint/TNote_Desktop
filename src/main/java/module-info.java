module hu.tnote.balint.tnote {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires json.simple;

    exports hu.tnote.balint;
    exports hu.tnote.balint.Controllers;
    exports hu.tnote.balint.Controllers.InsideView;
    opens hu.tnote.balint to javafx.fxml;
    opens hu.tnote.balint.Controllers to javafx.fxml;
    opens hu.tnote.balint.Controllers.InsideView to javafx.fxml;
}