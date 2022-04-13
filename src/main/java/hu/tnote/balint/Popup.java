package hu.tnote.balint;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Popup {
    private VBox parentContainer;
    private VBox popupContainer;
    private HBox contentContainer;
    private VBox labelContainer;
    private VBox xBtnContainer;
    private Label popupLabel;
    private Label xBtn;
    private IntegerProperty containerXCenter;
    private boolean withBlur = true;
    private String popupColor = "white";
    private int blurRadius = 4;
    private int popupWidth = 400;
    private int popupHeight = 50;
    private int popupTopMargin = 20;

    public Popup(VBox parentContainer) {
        defaultInit(parentContainer);
    }

    public Popup(VBox parentContainer, String text) {
        this(parentContainer);
        popupLabel.setText(text);
    }

    public Popup(VBox parentContainer, String text, String popupColor) {
        this(parentContainer, text);
        contentContainer.setStyle("-fx-background-color: " + popupColor);
    }

    private void defaultInit(VBox parentContainer) {
        this.parentContainer = parentContainer;

        containerXCenter = new SimpleIntegerProperty();
        containerXCenter.bind(parentContainer.widthProperty().divide(2));

        popupLabel = new Label();
        popupLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 125%");

        labelContainerInit();
        xBtnContainerInit();

        contentContainerInit();
        popupContainerInit();

        parentContainer.getChildren().get(0).setOnMouseClicked(v -> {
            this.hide();
        });
    }

    private void labelContainerInit() {
        labelContainer = new VBox();
        labelContainer.setAlignment(Pos.CENTER_LEFT);
        labelContainer.getChildren().add(popupLabel);
    }

    private void xBtnContainerInit() {
        xBtnContainer = new VBox();
        xBtnContainer.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(xBtnContainer, Priority.ALWAYS);
        xBtn = new Label("\uD83D\uDDD9");
        xBtn.setStyle("-fx-font-size: 150%; -fx-font-weight: 900; -fx-text-fill: white; -fx-background-color: transparent; -fx-label-padding: 0 0 4 0");
        xBtn.setOnMouseClicked(e -> {
            this.hide();
        });
        xBtnContainer.getChildren().add(xBtn);
    }

    private void contentContainerInit() {
        contentContainer = new HBox();
        contentContainer.setMinWidth(popupWidth);
        contentContainer.setMaxWidth(popupWidth);
        contentContainer.setMinHeight(popupHeight);
        contentContainer.setMaxHeight(popupHeight);
        contentContainer.setPadding(new Insets(0, 10, 0, 10));
        contentContainer.translateXProperty().bind(containerXCenter.subtract(contentContainer.widthProperty().divide(2)));
        contentContainer.setTranslateY(popupTopMargin);
        contentContainer.setAlignment(Pos.CENTER_LEFT);
        contentContainer.getChildren().add(labelContainer);
        contentContainer.getChildren().add(xBtnContainer);
        contentContainer.setOpacity(0.8);
        contentContainer.setStyle("-fx-background-color: " + popupColor + ";" +
                "-fx-background-radius: 5;");
    }

    private void popupContainerInit() {
        popupContainer = new VBox();
        popupContainer.setManaged(false);
        popupContainer.setPickOnBounds(false);

        popupContainer.getChildren().add(contentContainer);
    }

    public void show() {
        if (withBlur) {
            parentContainer.getChildren().get(0).setEffect(new GaussianBlur((blurRadius)));
        }
        parentContainer.getChildren().add(popupContainer);
    }

    public void hide() {
        parentContainer.getChildren().get(0).setEffect(null);
        parentContainer.getChildren().remove(popupContainer);
    }

    public Popup setText(String text) {
        popupLabel.setText(text);
        return this;
    }

    public Popup setColor(String color) {
        contentContainer.setStyle(contentContainer.getStyle() + "; -fx-background-color: " + color);
        return this;
    }

    public Popup setTextColor(String color) {
        popupLabel.setStyle(popupLabel.getStyle() + "; -fx-text-fill: " + color);
        xBtn.setStyle(popupLabel.getStyle() + "; -fx-text-fill: " + color);
        return this;
    }

    public Popup setOpcaity(Double zeroToOne) {
        contentContainer.setOpacity(zeroToOne);
        return this;
    }
}
