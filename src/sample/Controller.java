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
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class Controller {

//    DraggableNode btn1;
//    DraggableNode btn2;

//    DataFormat stateAddLink = new DataFormat("linkAdd");
//    DataFormat stateAddNode = new DataFormat("nodeAdd");





    @FXML
    private AnchorPane layout;

    @FXML
    private Pane test;

    @FXML
    private Button addButton;

    LinkedList<Pane> nodes = new LinkedList<Pane>();

    @FXML
    void initialize() throws IOException {
        var btn1 = new DraggableNode();
        var btn2 = new DraggableNode();
        var btn3 = new DraggableNode();
        var btn4 = new DraggableNode();


        layout.getChildren().addAll(btn1, btn2, btn3, btn4);
        btn2.setLayoutX(30);
        btn2.setLayoutY(100);
    }
}

