package hu.tnote.balint;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Popup {
    private static List<Popup> currentPopups = new ArrayList<>();

    private VBox parentContainer;               //the container in which the popup will be displayed (absolute pos, popup can be anywhere despite parent,
                                                // recommended most outer container for blur effect to work properly)
    private VBox trickyContainer;               //add to parent container, turn off managed but contents show properly without messing with dashboard
    private VBox contentContainer;              //holds the popup window
    private HBox titleBarContainer;             //holds the label container and close button
    private VBox labelContainer;                //holds the title text
    private VBox xBtnContainer;                 //holds the close button
    private Label popupLabel;                   //title
    private Label xBtn;                         //close button
    private IntegerProperty containerXCenter;
    private IntegerProperty containerYCenter;
    private boolean withBlur = false;
    private String popupColor = "white";
    private int blurRadius = 4;
    private int popupWidth = 400;
    private int popupHeight = 50;               //currently, not in use
    private int popupTopMargin = 20;            //distance from top of the window
    private int closeWaitMilisec = 0;
    private int fadeTranstionMilis = 200;
    private boolean fadeIn = false;
    private boolean fadeOut = false;
    private boolean closeOnWindoClick = true;

    //region Constructors

    public Popup() {
        defaultInit();
    }

    public Popup(String text) {
        this();
        popupLabel.setText(text);
    }

    public Popup(String text, String popupColor) {
        this(text);
        contentContainer.setStyle(contentContainer.getStyle() + "; -fx-background-color: " + popupColor);
    }

    //endregion

    //region Initialize

    private void defaultInit() {
        this.parentContainer = WindowManager.getRootContainer();

        containerXCenter = new SimpleIntegerProperty();
        containerXCenter.bind(parentContainer.widthProperty().divide(2));
        containerYCenter = new SimpleIntegerProperty();
        containerYCenter.bind(parentContainer.heightProperty().divide(2));

        popupLabel = new Label();
        popupLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 125%");

        labelContainerInit();
        xBtnContainerInit();
        titleBarContainerInit();

        contentContainerInit();
        trickyContainerInit();
    }

    private void labelContainerInit() {
        labelContainer = new VBox();
        labelContainer.setAlignment(Pos.TOP_LEFT);
        labelContainer.getChildren().add(popupLabel);
    }

    private void xBtnContainerInit() {
        xBtnContainer = new VBox();
        xBtnContainer.setAlignment(Pos.CENTER_RIGHT);
        xBtnContainer.setMinWidth(20);
        xBtnContainer.setMaxHeight(20);
        HBox.setHgrow(xBtnContainer, Priority.ALWAYS);
        xBtn = new Label("\uD83D\uDDD9");
        xBtn.setStyle("-fx-font-size: 150%; -fx-font-weight: 900; -fx-text-fill: black; -fx-background-color: transparent");
        xBtn.setOnMouseClicked(e -> {
            this.hide();
        });
        xBtnContainer.getChildren().add(xBtn);
    }

    private void titleBarContainerInit() {
        titleBarContainer = new HBox();
        titleBarContainer.getChildren().add(labelContainer);
        titleBarContainer.getChildren().add(xBtnContainer);
    }

    private void contentContainerInit() {
        contentContainer = new VBox();
        contentContainer.setMinWidth(popupWidth);
        contentContainer.setMaxWidth(popupWidth);
        contentContainer.setPadding(new Insets(10));
        contentContainer.setSpacing(10);
        contentContainer.translateXProperty().bind(containerXCenter.subtract(contentContainer.widthProperty().divide(2)));
        contentContainer.setTranslateY(popupTopMargin);
        contentContainer.setAlignment(Pos.TOP_LEFT);
        contentContainer.setOpacity(1);
        contentContainer.setStyle("-fx-background-color: " + popupColor + ";" +
                "-fx-background-radius: 5");

        contentContainer.getChildren().add(titleBarContainer);
    }

    private void trickyContainerInit() {
        trickyContainer = new VBox();
        trickyContainer.setManaged(false);
        trickyContainer.setPickOnBounds(false);

        trickyContainer.getChildren().add(contentContainer);
    }

    //endregion

    protected VBox getContentContainer() { return this.contentContainer; }

    protected boolean isCloseOnWindoClick() { return this.closeOnWindoClick; }

    protected IntegerProperty getContainerXCenter() { return this.containerXCenter; }

    public void show() {
        currentPopups.add(this);
        if (withBlur) {
            parentContainer.getChildren().get(0).setEffect(new GaussianBlur((blurRadius)));
        }

        parentContainer.getChildren().add(trickyContainer);

        if (fadeIn) {
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(fadeTranstionMilis), contentContainer);
            fadeInTransition.setFromValue(0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
        } else contentContainer.setOpacity(1);

        if (closeWaitMilisec > 0) {
            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(closeWaitMilisec);
                    return null;
                }
            };
            sleeper.setOnSucceeded(e -> { hide(); });
            new Thread(sleeper).start();
        }
    }

    public Optional<ButtonType> confirm(String text) {
        ButtonType okBtn = new ButtonType("Igen", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelbtn = new ButtonType("Mégse", ButtonBar.ButtonData.CANCEL_CLOSE);

        Dialog<ButtonType> d = new Dialog<>();

        Window parentWindow = parentContainer.getScene().getWindow();

        d.initStyle(StageStyle.TRANSPARENT);
        d.getDialogPane().getScene().setFill(Color.TRANSPARENT);
        d.getDialogPane().getStylesheets().addAll(getClass().getResource("/hu/tnote/balint/css/app.css").toExternalForm(),
                getClass().getResource("/hu/tnote/balint/css/buttons.css").toExternalForm());
        d.getDialogPane().getStyleClass().add("bg-secondary");
        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        d.getDialogPane().setMaxWidth(400);
        d.getDialogPane().setContent(textLabel);
        d.getDialogPane().getButtonTypes().add(okBtn);
        d.getDialogPane().getButtonTypes().add(cancelbtn);
        d.getDialogPane().getStyleClass().addAll("roundTRBL");
        d.getDialogPane().getScene().getWindow().setX(parentWindow.getX() + (parentWindow.getWidth() / 2) - (d.getDialogPane().getWidth() / 2));
        d.getDialogPane().getScene().getWindow().setY(parentWindow.getY() + (parentWindow.getHeight() / 2) - 75);
        ((ButtonBar) d.getDialogPane().getChildren().get(2)).getButtons().get(0).getStyleClass().add("saveBtn");
        ((ButtonBar) d.getDialogPane().getChildren().get(2)).getButtons().get(1).getStyleClass().add("deleteBtn");

        return d.showAndWait();
    }

    public void hide() {
        currentPopups.remove(this);
        parentContainer.getChildren().get(0).setEffect(null);

        if (fadeOut) {
            FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(fadeTranstionMilis), contentContainer);
            fadeOutTransition.setFromValue(1.0);
            fadeOutTransition.setToValue(0);
            fadeOutTransition.play();
            fadeOutTransition.setOnFinished(e -> { parentContainer.getChildren().remove(trickyContainer); });
        } else parentContainer.getChildren().remove(trickyContainer);
    }

    public static void hideAll() {
        new Thread(() -> {
            int modifier = 0;
            for (int i = 0; i < currentPopups.size() - modifier; i++) {
                int finalI = i;
                int finalModifier = modifier;
                Platform.runLater(() -> currentPopups.get(finalI - finalModifier).hide());
                modifier++;
            }
        }).start();

    }

    public static void hideByIndex(int i) {
        if (i >= 0 && i < currentPopups.size()) {
            currentPopups.get(i).hide();
        }
    }

    public static void blink() {
        if (currentPopups.size() > 0) {
            int shakeWidth = 20;
            VBox container = currentPopups.get(currentPopups.size() - 1).getContentContainer();
            container.translateXProperty().unbind();
            container.setTranslateX(container.getTranslateX() - (shakeWidth / 2));

            TranslateTransition shake = new TranslateTransition();
            shake.setNode(container);
            shake.setByX(shakeWidth);
            shake.setCycleCount(6);
            shake.setAutoReverse(true);
            shake.setDuration(Duration.millis(50));
            shake.play();
            shake.setOnFinished(v -> {
                container.translateXProperty().bind(currentPopups.get(currentPopups.size() - 1).getContainerXCenter().subtract(container.widthProperty().divide(2)));
            });
        }
    }

    //region Options

    public Popup setText(String text) {
        popupLabel.setText(text);
        return this;
    }

    /**
     * @param color css color ($#FF, rgb(red, green, blue), ...)
     * @return current Popup
     */
    public Popup setColor(String color) {
        contentContainer.setStyle(contentContainer.getStyle() + "; -fx-background-color: " + color);
        return this;
    }

    /**
     * @param color css color (#FFF, rgb(red, green, blue), ...)
     * @return current Popup
     */
    public Popup setTextColor(String color) {
        popupLabel.setStyle(popupLabel.getStyle() + "; -fx-text-fill: " + color);
        xBtn.setStyle(popupLabel.getStyle() + "; -fx-text-fill: " + color);
        return this;
    }

    /**
     * @param oppacity double value between 0 and 1 (<=0 = 0%, ..., 0.5 = 50%, ... >=1 = 100%)
     * @return current Popup
     */
    public Popup setOpcaity(Double oppacity) {
        contentContainer.setOpacity(oppacity);
        return this;
    }

    public Popup withBlur() {
        withBlur = true;
        return this;
    }

    public Popup turnOffHideOnWindowClick() {
        closeOnWindoClick = false;
        parentContainer.getChildren().get(0).setOnMouseClicked(v -> {});
        return this;
    }

    public Popup addBody(VBox body) {
        contentContainer.getChildren().add(body);
        return this;
    }

    public Popup setCloseTimer(int milisec) {
        closeWaitMilisec = milisec;
        return this;
    }

    public Popup withFadeIn() {
        fadeIn = true;
        return this;
    }

    public Popup withFadeOut() {
        fadeOut = true;
        return  this;
    }

    public Popup withFadeInAndOut() {
        fadeIn = true;
        fadeOut = true;
        return this;
    }

    public Popup setFadeTransitionTime(int milis) {
        fadeTranstionMilis = milis;
        return  this;
    }

    public Popup setWidth(int width) {
        this.popupWidth = width;
        contentContainer.setMinWidth(popupWidth);
        contentContainer.setMaxWidth(popupWidth);
        return this;
    }

    public Popup setDistanceFromTop(int distance) {
        this.popupTopMargin = distance;
        return this;
    }

    public Popup placeToCenter() {
        this.contentContainer.translateYProperty().bind(containerYCenter.subtract(contentContainer.heightProperty().divide(2)));
        return this;
    }

    //endregion
}
