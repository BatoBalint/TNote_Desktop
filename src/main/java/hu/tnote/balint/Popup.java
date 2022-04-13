package hu.tnote.balint;

import javafx.scene.layout.VBox;

public class Popup {
    private VBox parentContainer;
    private VBox popupContainer;

    public Popup(VBox parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void show() {
        popupContainer = new VBox();
        popupContainer.setManaged(false);
        popupContainer.setPickOnBounds(false);

        VBox vbox = new VBox();
        vbox.setMinWidth(200);
        vbox.setMaxWidth(200);
        vbox.setMaxHeight(200);
        vbox.setMinHeight(200);
        vbox.setTranslateX(100);
        vbox.setTranslateY(100);
        vbox.setStyle("-fx-background-color: white");

        popupContainer.getChildren().add(vbox);

        parentContainer.getChildren().add(popupContainer);
    }

    public void hide() {
        parentContainer.getChildren().remove(popupContainer);
    }
}
