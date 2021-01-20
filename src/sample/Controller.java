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


    //LinkedList<DraggableNode> nodes = new LinkedList<DraggableNode>();

    @FXML
    void initialize() throws IOException {
////////////// BLACK AND WHITE //////////////////////
//        ColorAdjust effect = new ColorAdjust();
//        effect.setSaturation(-1);
////////////////////////////////////////////////


//////////// BLOOM  //////////////////////
//        Bloom effect = new Bloom(0.2);
//
////////////////////////////////////////////////


        ///////////////// SepiaTone  //////////////////////
//         Effect sepia = new SepiaTone();;
//
////////////////////////////////////////////////



        ///////////////// MotionBlur  //////////////////////
//        MotionBlur motionBlur = new MotionBlur();
//
//        //Setting the radius to the effect
//        motionBlur.setRadius(10.5);
//
//        //Setting angle to the effect
//        motionBlur.setAngle(45);
//
////////////////////////////////////////////////


        //Setting the radius to apply the Gaussian Blur effect
        //ImageView view = new ImageView(new Image(new FileInputStream("/Users/sergejvanislavskij/Desktop/joda.jpg")));


        var btn1 = new DraggableNode();
        var btn2 = new DraggableNode();

        btn1.setType(DragType.STARTNODE);
        btn2.setType(DragType.ENDNODE);

        Graph.nodes.add(btn1);
        Graph.nodes.add(btn2);
        Graph.startNode = btn1.getId();
        Graph.endNode = btn2.getId();

        layout.getChildren().addAll(btn1, btn2);
        btn2.setLayoutX(30);
        btn2.setLayoutY(100);


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

