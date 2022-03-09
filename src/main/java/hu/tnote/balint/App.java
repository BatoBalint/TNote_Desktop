package hu.tnote.balint;

import hu.tnote.balint.Controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = App.class.getResource("/app.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("TNote");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        MainController mainController = fxmlLoader.getController();
        mainController.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}