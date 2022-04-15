package hu.tnote.balint.CustomNode;

import hu.tnote.balint.Popup;
import hu.tnote.balint.TimetableElement;
import hu.tnote.balint.WindowManager;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TTElementButton {
    private TimetableElement ttelement;
    private VBox container;
    private VBox ttelementEditorContainer;
    private Label titleLabel;
    private Label descLabel;
    private Label timeLabel;

    public TTElementButton(TimetableElement timetableElement) {
        this.ttelement = timetableElement;

        basicInit();
        ttelementEditorInit();
        container.setOnMouseClicked(v -> {
            new Popup(WindowManager.getRootContainer(), "Órarend elem felvétele", "#22FFFF")
                    .turnOffHideOnWindowClick().setTextColor("#0000AA").addBody(ttelementEditorContainer).show();
        });
        if (ttelement.getId() != -1) {
            init();
        } else {
            plusBtnInit();
        }
    }

    private void basicInit() {
        container = new VBox();
        container.setFillWidth(true);

        container.setMinHeight(100);
        container.setMaxHeight(100);
        container.setMinWidth(200);
        container.setMaxWidth(200);
        container.getStyleClass().add("noteButton");
        container.getStyleClass().add("roundTRBL");
        container.getStyleClass().add("p-4");
    }

    private void plusBtnInit() {
        container.setAlignment(Pos.CENTER);

        titleLabel = new Label("+");
        titleLabel.getStyleClass().add("h1");

        container.getChildren().add(titleLabel);
    }

    private void init() {
        titleLabel = new Label(ttelement.getTitle());
        titleLabel.getStyleClass().add("h3");

        descLabel = new Label(formatDescription(ttelement.getDescription()));
        descLabel.getStyleClass().add("h7");

        timeLabel = new Label(ttelement.getStart().toString() + " - " + ttelement.getEnd().toString());
        timeLabel.getStyleClass().add("h8");

        HBox timeLabelHbox = new HBox(timeLabel);
        VBox.setVgrow(timeLabelHbox, Priority.ALWAYS);
        timeLabelHbox.setAlignment(Pos.BOTTOM_LEFT);

        container.getChildren().add(titleLabel);
        container.getChildren().add(descLabel);
        container.getChildren().add(timeLabelHbox);
    }

    private void ttelementEditorInit() {
        ttelementEditorContainer = new VBox();
        ttelementEditorContainer.setFillWidth(true);
        ttelementEditorContainer.setAlignment(Pos.TOP_CENTER);
        try {
            ttelementEditorContainer.getChildren().add(FXMLLoader.load(getClass().getResource("/hu/tnote/balint/insideViews/ttelement-editor.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDescription(String raw) {
        if (raw.length() < 30) return raw;
        else {
            StringBuilder formatted = new StringBuilder();
            int index = 0;

            String[] words = raw.split(" ");
            if (words[0].length() > 28) return words[0].substring(0, 28) + "...";
            while (index < words.length && formatted.length() + words[index].length() + 1 < 28) {
                formatted.append(words[index++]).append(" ");
            }

            formatted.append(" ...");

            return formatted.toString();
        }
    }

    public void addOnClickListener(EventHandler<MouseEvent> eventHandler) {
        container.setOnMouseClicked(eventHandler);
    }

    public VBox getVBox() {
        return this.container;
    }
}
