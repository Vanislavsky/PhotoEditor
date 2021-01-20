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
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;

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
    void initialize() throws IOException {

        var startNode = new DraggableNode();
        var endNode = new DraggableNode();

        startNode.setType(DragType.STARTNODE);
        endNode.setType(DragType.ENDNODE);

        Graph.nodes.add(startNode);
        Graph.nodes.add(endNode);
        Graph.startNode = startNode.getId();
        Graph.endNode = endNode.getId();

        layout.getChildren().addAll(startNode, endNode);

        startNode.setLayoutX(20);
        startNode.setLayoutY(100);
        endNode.setLayoutX(450);
        endNode.setLayoutY(100);


        addBlackAndWhiteNode.setOnAction(actionEvent -> {
            try {
                var newNode = new DraggableNode();
                newNode.setType(DragType.BLACKANDWHITE);
                Graph.nodes.add(newNode);
                layout.getChildren().add(newNode);
                newNode.setLayoutX(layout.getWidth() / 2);
                newNode.setLayoutY(layout.getHeight() / 2);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        });

        addBlurMode.setOnAction(actionEvent -> {
            try {
                var newNode = new DraggableNode();
                newNode.setType(DragType.MOTIONBLUR);
                Graph.nodes.add(newNode);
                layout.getChildren().add(newNode);
                newNode.setLayoutX(layout.getWidth() / 2);
                newNode.setLayoutY(layout.getHeight() / 2);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        });

        addBloomNode.setOnAction(actionEvent -> {
            try {
                var newNode = new DraggableNode();
                newNode.setType(DragType.BLOOM);
                Graph.nodes.add(newNode);
                layout.getChildren().add(newNode);
                newNode.setLayoutX(layout.getWidth() / 2);
                newNode.setLayoutY(layout.getHeight() / 2);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        });

        addSepiaNode.setOnAction(actionEvent -> {
            try {
                var newNode = new DraggableNode();
                newNode.setType(DragType.SepiaTone);
                Graph.nodes.add(newNode);
                layout.getChildren().add(newNode);
                newNode.setLayoutX(layout.getWidth() / 2);
                newNode.setLayoutY(layout.getHeight() / 2);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        });

        addReflectionNode.setOnAction(actionEvent -> {
            try {
                var newNode = new DraggableNode();
                newNode.setType(DragType.Reflection);
                Graph.nodes.add(newNode);
                layout.getChildren().add(newNode);
                newNode.setLayoutX(layout.getWidth() / 2);
                newNode.setLayoutY(layout.getHeight() / 2);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        });

    }
}

