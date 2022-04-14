package hu.tnote.balint.CustomNode;

import hu.tnote.balint.TimetableElement;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.EventListener;

public class TTElementButton {
    private TimetableElement ttelement;
    private VBox container;
    private Label titleLabel;
    private Label descLabel;
    private Label timeLabel;

    public TTElementButton(TimetableElement timetableElement) {
        this.ttelement = timetableElement;

        container = new VBox();
        container.setFillWidth(true);

        container.setMinHeight(100);
        container.setMaxHeight(100);
        container.setMinWidth(200);
        container.setMaxWidth(200);
        container.getStyleClass().add("noteButton");
        container.getStyleClass().add("roundTRBL");
        container.getStyleClass().add("p-4");

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
