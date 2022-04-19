package hu.tnote.balint;

import hu.tnote.balint.Controllers.MainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static boolean test = false;

    @Override
    public void start(Stage stage) throws IOException {
        if (test) loadTest(stage);
        else loadApp(stage);
    }

    private void loadApp(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = App.class.getResource("/hu/tnote/balint/css/app.css").toExternalForm();
        scene.getStylesheets().add(css);
        css = App.class.getResource("/hu/tnote/balint/css/buttons.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("TNote");
        stage.getIcons().setAll(new Image(getClass().getResource("/hu/tnote/balint/images/tnote_logo_new.png").toExternalForm()));
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
        if (args.length > 0 && args[0].equals("--test")) test = true;
        launch();
    }
}