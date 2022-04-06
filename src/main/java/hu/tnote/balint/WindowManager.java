package hu.tnote.balint;

import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.Controllers.DashboardController;
import hu.tnote.balint.Controllers.InsideViews.NoteListController;
import hu.tnote.balint.Controllers.ReglogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.io.IOException;
import java.lang.reflect.MalformedParametersException;

public class WindowManager {
    private VBox rootContainer;
    private ScrollPane innerScrollpane;

    public WindowManager(VBox rootContainer) {
        this.rootContainer = rootContainer;
    }

    public void setInnerScrollpane(ScrollPane scrollPane) {
        this.innerScrollpane = scrollPane;
    }

    public void changeToRegLog() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("reglog-view.fxml"));
        BorderPane child = loader.load();
        ReglogController reglogController = loader.getController();
        reglogController.setWindowManager(this);
        VBox.setVgrow(child, Priority.ALWAYS);
        rootContainer.getChildren().setAll(child);
    }

    public void changeToDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("dashboard.fxml"));
        GridPane child = loader.load();
        DashboardController dashboardController = loader.getController();
        dashboardController.setWindowManager(this);
        setInnerScrollpane(dashboardController.getScrollPane());
        VBox.setVgrow(child, Priority.ALWAYS);
        rootContainer.getChildren().setAll(child);
    }

    public void changeToNoteList() throws Exception {
        if (this.innerScrollpane == null) throw new Exception("Note Scroll Pane is null");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hu/tnote/balint/insideViews/note-list-view.fxml"));
        VBox vbox = loader.load();
        NoteListController noteListController = loader.getController();
        noteListController.setWindowManager(this);
        noteListController.setScrollPane(innerScrollpane);
        innerScrollpane.setContent(vbox);
    }

    public void changeToFxml(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        VBox vbox = loader.load();
        ((Controller) loader.getController()).setWindowManager(this);
        innerScrollpane.setContent(vbox);
    }
}
