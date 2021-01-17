package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.shape.CubicCurve;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class Controller {
    //class Point2dSerial(Double x, Double y) extends Point2D(x, y), Serializable;

    DraggableNode btn1;
    DraggableNode btn2;

    DataFormat stateAddLink = new DataFormat("linkAdd");
    DataFormat stateAddNode = new DataFormat("nodeAdd");

    class DraggableNode extends AnchorPane {
        @FXML
        AnchorPane rootPane;

        @FXML
        AnchorPane leftLinkPane;

        @FXML
        AnchorPane rightLinkPane;

        @FXML
        Label content;

        EventHandler<DragEvent> contextDragOver;
        EventHandler<DragEvent> contextDragDropped;

        EventHandler<MouseEvent> linkDragDetected;
        EventHandler<DragEvent> linkDragDropped;
        EventHandler<DragEvent> contextLinkDragOver;
        EventHandler<DragEvent> contextLinkDagDropped;

        NodeLink link = new NodeLink();
        Point2D offset = new Point2D(0.0, 0.0);

        AnchorPane superParent = null;

        DraggableNode() throws IOException {
            FXMLLoader fxmlLoader = FXMLLoader.load(getClass().getResource("DraggableNode.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            fxmlLoader.load();
            setId(UUID.randomUUID().toString());

        }

        @FXML
        void initialize() {
            nodeHandlers();
            linkHandlers();

            leftLinkPane.setOnDragDetected(linkDragDetected);
            leftLinkPane.setOnDragDropped(linkDragDropped);
            rightLinkPane.setOnDragDetected(linkDragDetected);
            rightLinkPane.setOnDragDropped(linkDragDropped);

            link.setVisible(false);

            parentProperty().addListener((o,oldVal, newVal) -> { superParent = (AnchorPane)getParent();});
        }

        void updatePoint(Point2D point) {
            var local = getParent().sceneToLocal(point);
            relocate(
                    (local.getX() - offset.getX()),
                    (local.getX() - offset.getX())
            );
        }

        void nodeHandlers() {
            contextDragOver = new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent dragEvent) {
                    updatePoint(new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY()));
                    dragEvent.consume();
                }
            };

            contextDragDropped = new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent dragEvent) {
                    getParent().setOnDragDropped(null);
                    getParent().setOnDragOver(null);
                    dragEvent.setDropCompleted(true);
                    dragEvent.consume();
                }
            };

            content.setOnDragDetected(event -> {
               getParent().setOnDragOver(contextDragOver);
               getParent().setOnDragDropped(contextDragDropped);

               offset = new Point2D(event.getX(), event.getY());
               updatePoint(new Point2D(event.getSceneX(), event.getSceneY()));

               var content = new ClipboardContent();
               content.put(stateAddNode, "node");
               startDragAndDrop(TransferMode.ANY).setContent(content);
            });
        }

        void linkHandlers() {
            linkDragDetected = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getParent().setOnDragOver(null);
                    getParent().setOnDragDropped(null);

                    getParent().setOnDragOver(contextLinkDragOver);
                    getParent().setOnDragDropped(contextLinkDagDropped);

                    superParent.getChildren().add(0, link);
                    link.setVisible(true);

                    var p = new Point2D(getLayoutX() + getWidth()/2, getLayoutY() + getHeight()/2);
                    link.setStart(p);

                    var content = new ClipboardContent();
                    content.put(stateAddLink, "link");
                    startDragAndDrop(TransferMode.ANY).setContent(content);
                    mouseEvent.consume();
                }
            };

            linkDragDropped = new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    System.out.println("link connect");
                    getParent().setOnDragOver(null);
                    getParent().setOnDragDropped(null);

                    link.setVisible(false);
                    superParent.getChildren().remove(0);

                    try {
                        var link = new NodeLink();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    link.bindStartEnd(btn1, btn2);
                    superParent.getChildren().add(0, link);

                    var content = new ClipboardContent();
                    content.put(stateAddLink, "link");
                    startDragAndDrop(TransferMode.ANY).setContent(content);
                    event.setDropCompleted(true);
                    event.consume();
                }
            };

            contextLinkDragOver = new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    event.acceptTransferModes(TransferMode.ANY);
                    if(!link.isVisible())
                        link.setVisible(true);
                    link.setEnd(new Point2D(event.getX(), event.getY()));

                    event.consume();
                }
            };

            contextDragDropped = new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    System.out.println("link droppped");
                    getParent().setOnDragDropped(null);
                    getParent().setOnDragOver(null);

                    link.setVisible(false);
                    superParent.getChildren().remove(0);

                    event.setDropCompleted(true);
                    event.consume();
                }
            };
        }




    }

    class NodeLink extends AnchorPane {
        @FXML
        CubicCurve nodeLink;
        SimpleDoubleProperty offsetX = new SimpleDoubleProperty();
        SimpleDoubleProperty offsetY = new SimpleDoubleProperty();
        SimpleDoubleProperty offsetDirX1 = new SimpleDoubleProperty();
        SimpleDoubleProperty offsetDirX2 = new SimpleDoubleProperty();
        SimpleDoubleProperty offsetDirY1 = new SimpleDoubleProperty();
        SimpleDoubleProperty offsetDirY2 = new SimpleDoubleProperty();

        @FXML
        void initialize() {
            offsetX.set(100.0);
            offsetY.set(50.0);

            offsetDirX1.bind(Bindings.when(nodeLink.startXProperty().greaterThan(nodeLink.endXProperty()))
                    .then(-1.0).otherwise(1.0));

            offsetDirX2.bind(Bindings.when(nodeLink.startXProperty().greaterThan(nodeLink.endXProperty()))
                    .then(-1.0).otherwise(1.0));

            nodeLink.controlX1Property().bind(Bindings.add(nodeLink.startXProperty(), offsetX.multiply(offsetDirX1)));
            nodeLink.controlX2Property().bind(Bindings.add(nodeLink.endXProperty(), offsetX.multiply(offsetDirX2)));
            nodeLink.controlY1Property().bind(Bindings.add(nodeLink.startYProperty(), offsetY.multiply(offsetDirY1)));
            nodeLink.controlY2Property().bind(Bindings.add(nodeLink.endYProperty(), offsetY.multiply(offsetDirY2)));
        }

        void setStart(Point2D point) {
            nodeLink.setStartX(point.getX());
            nodeLink.setStartY(point.getY());
        }

        void setEnd(Point2D point) {
            nodeLink.setEndX(point.getX());
            nodeLink.setEndY(point.getY());
        }

        void bindStartEnd(DraggableNode firstSource, DraggableNode secondSource) {
            nodeLink.startXProperty().bind(Bindings.add(firstSource.layoutXProperty(), firstSource.getWidth()/2));
            nodeLink.startYProperty().bind(Bindings.add(firstSource.layoutYProperty(), firstSource.getHeight()/2));
            nodeLink.endXProperty().bind(Bindings.add(secondSource.layoutXProperty(), secondSource.getWidth()/2));
            nodeLink.endYProperty().bind(Bindings.add(secondSource.layoutYProperty(), secondSource.getHeight()/2));
        }

        public NodeLink() throws IOException {
            FXMLLoader fxmlLoader = FXMLLoader.load(getClass().getResource("NodeLink.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            setId(UUID.randomUUID().toString());
        }
    }


    @FXML
    private AnchorPane layout;

    @FXML
    private Pane test;

    @FXML
    private Button addButton;

    LinkedList<Pane> nodes = new LinkedList<Pane>();

    @FXML
    void initialize() throws IOException {
        btn1 = new DraggableNode();
        btn2 = new DraggableNode();

        layout.getChildren().addAll(btn1, btn2);
    }
}

