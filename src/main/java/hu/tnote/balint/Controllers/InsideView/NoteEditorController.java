package hu.tnote.balint.Controllers.InsideView;

import hu.tnote.balint.Api;
import hu.tnote.balint.Controllers.Controller;
import hu.tnote.balint.Note;
import hu.tnote.balint.Popup;
import hu.tnote.balint.WindowManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class NoteEditorController extends Controller {
    @FXML
    public VBox saveBtnContainer;
    @FXML
    private VBox rootContainer;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField noteTitle;
    @FXML
    private HBox titleHbox;
    @FXML
    public MenuButton menuBtn;
    private Note note;
    private SimpleIntegerProperty lineCount = new SimpleIntegerProperty();

    public void initialize() {
        uiInit();
    }

    private void uiInit() {
        VBox.setVgrow(titleHbox, Priority.ALWAYS);
        HBox.setHgrow(noteTitle, Priority.ALWAYS);
        rootContainer.setAlignment(Pos.TOP_CENTER);

        textArea.setMaxWidth(800);
        textArea.setWrapText(true);
    }

    private void moreButtonSetup() {
        menuBtn.getItems().removeAll();

        menuBtn.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        MenuItem delete = new MenuItem("Törlés");

        delete.setOnAction(actionEvent -> {
            StringBuilder message = new StringBuilder();
            message.append("Biztos törölni szeretnéd ezt az jegyzetet?\n\"").append(note.getTitle()).append("\"");
            if (new Popup().confirm(message.toString()).get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                Popup.hideAll();
                if (note.getId() == -1) {
                    try {
                        windowManager.changeToNoteList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Api.deleteNote(note.getId());
                        new Popup("Sikeres törlés").setColor("#22FF44")
                                .setTextColor("#00AA11").setCloseTimer(2000)
                                .withFadeInAndOut().show();
                        windowManager.changeToNoteList();
                    } catch (Exception e) {
                        new Popup("Valami okból kifolyólag nem sikerült a törlés")
                                .setColor("#FF2222").setTextColor("990000").show();
                    }
                }
            }
        });
        delete.getStyleClass().add("menuItemDelete");

        MenuItem back = new MenuItem("Vissza");

        back.setOnAction(actionEvent -> {
            try {
                menuBtn.hide();
                windowManager.changeToNoteList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        if (menuBtn.getItems().size() == 0) menuBtn.getItems().add(back);
        if (note.getId() != -1) menuBtn.getItems().add(delete);     //corrects the menu button popup position
        menuBtn.setOnShown(v -> {
            menuBtn.hide();
            menuBtn.setOnShown(v2 -> {});
            menuBtn.show();
        });
    }

    public void passData(Note note) {
        this.note = note;
        noteTitle.setText(this.note.getTitle());
        textArea.setText(this.note.getContent());

        moreButtonSetup();

        textArea.prefHeightProperty().bind(WindowManager.getRootContainer().heightProperty().subtract(150));
    }

    @FXML
    public void saveBtnClick() {
        if (note.getId() >= 0) {
            if (noteTitle.getText().trim().length() == 0) {
                new Popup("A cím mező nem lehet üres")
                        .setColor("#FF2222").setTextColor("#990000").show();
            } else {

                try {
                    Api.saveNote(note.getId(), noteTitle.getText(), textArea.getText());
                    new Popup("Sikeres mentés").setColor("#22FF44")
                            .setTextColor("#00AA11").setCloseTimer(2000)
                            .withFadeInAndOut().show();
                } catch (IOException e) {
                    e.printStackTrace();
                    new Popup("Valami okból kifolyólag nem sikerült a mentés")
                            .setColor("#FF2222").setTextColor("#990000").show();
                }
            }
        } else {
            if (noteTitle.getText().trim().length() == 0) {
                new Popup("A cím mező nem lehet üres")
                        .setColor("#FF2222").setTextColor("#990000").withFadeInAndOut().setCloseTimer(3000).show();
            } else {
                try {
                    note = Api.addNote(note.getId(), noteTitle.getText(), textArea.getText());
                    new Popup("Sikeres mentés").setColor("#22FF44")
                            .setTextColor("#00AA11").withFadeInAndOut().setCloseTimer(2000).show();
                    moreButtonSetup();
                } catch (IOException | ParseException e ) {
                    new Popup("Valami okból kifolyólag nem sikerült a mentés")
                            .setColor("#FF2222").withFadeInAndOut().setTextColor("#990000").show();
                }
            }
        }
    }
}
