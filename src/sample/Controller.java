package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;




public class Controller {
    @FXML
    private AnchorPane layout;

    @FXML
    private Button addBlackAndWhiteNode;

    @FXML
    private Button addBlurMode;

    @FXML
    private Button addBloomNode;

    @FXML
    private Button addSepiaNode;

    @FXML
    private Button addReflectionNode;
    @FXML
    private MenuItem seleectButton;

    @FXML
    private MenuItem saveButton;

    ImageEditor imageEditor;

    @FXML
    void initialize() throws IOException {

        seleectButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            var file =fileChooser.showOpenDialog(seleectButton.getParentPopup().getScene().getWindow());
            if(file != null) {
                try {
                    imageEditor = new ImageEditor(layout, file.getPath());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        });

        saveButton.setOnAction(actionEvent -> {
            if(imageEditor != null) {
                try {
                    imageEditor.saveToFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });


        addBlackAndWhiteNode.setOnAction(actionEvent -> {
            if(imageEditor != null)
                imageEditor.addNode(DragType.BLACKANDWHITE);
        });

        addBlurMode.setOnAction(actionEvent -> {
            if(imageEditor != null)
                imageEditor.addNode(DragType.MOTIONBLUR);
        });

        addBloomNode.setOnAction(actionEvent -> {
            if(imageEditor != null)
                imageEditor.addNode(DragType.BLOOM);
        });

        addSepiaNode.setOnAction(actionEvent -> {
            if(imageEditor != null)
                imageEditor.addNode(DragType.SepiaTone);
        });

        addReflectionNode.setOnAction(actionEvent -> {
            if(imageEditor != null)
                imageEditor.addNode(DragType.Reflection);
        });

    }
}

