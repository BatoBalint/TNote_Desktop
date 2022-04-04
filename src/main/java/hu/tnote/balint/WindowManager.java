package hu.tnote.balint;

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
    private ScrollPane noteListScrollpane;

    public WindowManager(VBox rootContainer) {
        this.rootContainer = rootContainer;
    }

    public void setNoteScrollpane(ScrollPane scrollPane) {
        this.noteListScrollpane = scrollPane;
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
        VBox.setVgrow(child, Priority.ALWAYS);
        rootContainer.getChildren().setAll(child);
    }

    public void changeToNoteList() throws Exception {
        if (this.noteListScrollpane == null) throw new Exception("Note Scroll Pane is null");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hu/tnote/balint/insideViews/note-list-view.fxml"));
        VBox vbox = loader.load();
        NoteListController noteListController = loader.getController();
        noteListController.setWindowManager(this);
        noteListController.setScrollPane(noteListScrollpane);
        noteListScrollpane.setContent(vbox);
    }
}
