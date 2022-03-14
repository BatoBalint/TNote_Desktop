package hu.tnote.balint;

import hu.tnote.balint.Controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        loadApp(stage);
//        loadTest(stage);
    }

    private void loadApp(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = App.class.getResource("/hu/tnote/balint/css/app.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("TNote");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        MainController mainController = fxmlLoader.getController();
        mainController.setStage(stage);
        stage.show();
    }

    private void loadTest(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("test-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = App.class.getResource("/hu/tnote/balint/css/app.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("TNote TEST");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}