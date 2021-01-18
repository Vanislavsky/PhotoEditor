package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

class Point2dSerial extends Point2D implements Serializable {

    public Point2dSerial(double x, double y) {
        super(x, y);
        // TODO Auto-ge nerated constructor stub
    }
}

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DraggableNode.fxml"));
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

        //parentProperty().addListener((o,oldVal, newVal) -> { superParent = (AnchorPane)getParent();});
        parentProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable,
                                Object oldValue, Object newValue) {
                superParent = (AnchorPane) getParent();

            }

        });
    }

    void updatePoint(Point2D point) {
        var local = getParent().sceneToLocal(point);
        relocate(
                (int) (local.getX() - offset.getX()),
                (int) (local.getY() - offset.getY())
        );
    }

    void nodeHandlers() {
        contextDragOver = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.ANY);
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
            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            getParent().setOnDragOver(contextDragOver);
            getParent().setOnDragDropped(contextDragDropped);

            offset = new Point2D(event.getX(), event.getY());
            updatePoint(new Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData ("type", getId());
            content.put(DragContainer.AddNode, container);

            startDragAndDrop (TransferMode.ANY).setContent(content);

            event.consume();

//               var content = new ClipboardContent();
//               content.put(stateAddNode, "node");
//               startDragAndDrop(TransferMode.ANY).setContent(content);
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

                ClipboardContent content = new ClipboardContent();
                DragContainer container = new DragContainer ();

                //pass the UUID of the source node for later lookup
                container.addData("source", getId());
                content.put(DragContainer.AddLink, container);
                startDragAndDrop (TransferMode.ANY).setContent(content);
                mouseEvent.consume();

//                    var content = new ClipboardContent();
//                    content.put(stateAddLink, "link");
//                    startDragAndDrop(TransferMode.ANY).setContent(content);
//                    mouseEvent.consume();
            }
        };

        linkDragDropped = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("link connect");
                getParent().setOnDragOver(null);
                getParent().setOnDragDropped(null);

                DragContainer container = (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);
                if (container == null)
                    return;

                link.setVisible(false);
                superParent.getChildren().remove(0);

                AnchorPane linkHandle = (AnchorPane) event.getSource();

                ClipboardContent content = new ClipboardContent();

                //pass the UUID of the target node for later lookup
                container.addData("target", getId());

                content.put(DragContainer.AddLink, container);

                event.getDragboard().setContent(content);
                event.setDropCompleted(true);
                event.consume();


//                    try {
//                        var link = new NodeLink();
//                    } catch (IOException exception) {
//                        exception.printStackTrace();
//                    }
//
//                    link.bindStartEnd(btn1, btn2);
//                    superParent.getChildren().add(0, link);

//                    var content = new ClipboardContent();
//                    content.put(stateAddLink, "link");
//                    startDragAndDrop(TransferMode.ANY).setContent(content);
//                    event.setDropCompleted(true);
//                    event.consume();
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

        contextLinkDagDropped = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
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
