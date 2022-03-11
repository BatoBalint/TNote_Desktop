module hu.tnote.balint.tnote {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires json.simple;

    opens hu.tnote.balint to javafx.fxml;
    exports hu.tnote.balint;
    exports hu.tnote.balint.Controllers;
    opens hu.tnote.balint.Controllers to javafx.fxml;
    exports hu.tnote.balint.Controllers.InsideViews;
    opens hu.tnote.balint.Controllers.InsideViews to javafx.fxml;
}