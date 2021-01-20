package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.UUID;

interface Effective {
    void effect();
}

interface DeleteEffective {
    void deleteEffect();
}

class DraggableNode extends AnchorPane {


    @FXML
    AnchorPane rootPane;

    @FXML
    AnchorPane leftLinkPane;

    @FXML
    AnchorPane rightLinkPane;

    @FXML
    private Button deleteButton;

    @FXML
    private Label effectName;

    @FXML
    ImageView content;

    EventHandler<DragEvent> contextDragOver;
    EventHandler<DragEvent> contextDragDropped;

    EventHandler<MouseEvent> linkDragDetected;
    EventHandler<DragEvent> linkDragDropped;
    EventHandler<DragEvent> contextLinkDragOver;
    EventHandler<DragEvent> contextLinkDagDropped;

    NodeLink link = new NodeLink();
    boolean checkLine = false;
    private final ArrayList <String> linkIds = new ArrayList <String> ();
    Point2D offset = new Point2D(0.0, 0.0);

    AnchorPane superParent = null;

    DragType dragType = null;
    public Effective effect;
    public DeleteEffective deleteEffect;
    public boolean checkEffect = false;
    public ArrayList<Pair<String, Effect>> prevEffects = new ArrayList<>();


    DraggableNode() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../res/DraggableNode.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        content.setImage(new Image(new FileInputStream("src/res/joda.jpg")));
        setId(UUID.randomUUID().toString());

    }


    @FXML
    void initialize() {
        nodeHandlers();
        linkHandlers();

        deleteButton.setOnAction(actionEvent -> {

            Graph.deleteCheckLine(this.getId());
            Graph.deleteNode(this.getId());
            prevEffects.clear();
            for(var node : Graph.nodes) {
                node.prevEffects.clear();
                node.content.setEffect(null);
                node.checkEffect = false;
            }

            Graph.detoir();

            AnchorPane parent  = (AnchorPane) this.getParent();
            parent.getChildren().remove(this);
            System.out.println(parent.getChildren());
            System.out.println(linkIds);

            for (ListIterator<String> iterId = linkIds.listIterator();
                 iterId.hasNext();) {

                String id = iterId.next();

                for (ListIterator <Node> iterNode = parent.getChildren().listIterator();
                     iterNode.hasNext();) {

                    Node node = iterNode.next();

                    if (node.getId() == null)
                        continue;

                    if (node.getId().equals(id))
                        iterNode.remove();
                }

                iterId.remove();
            }
        });

        leftLinkPane.setOnMouseMoved(mouseEvent -> {
            leftLinkPane.setStyle("-fx-background-color: #778899");
        });

        leftLinkPane.setOnMouseExited(mouseEvent -> {
            leftLinkPane.setStyle("-fx-background-color:  #dcdcdc");
        });

        rightLinkPane.setOnMouseMoved(mouseEvent -> {
            rightLinkPane.setStyle("-fx-background-color: #778899");
        });

        rightLinkPane.setOnMouseExited(mouseEvent -> {
            rightLinkPane.setStyle("-fx-background-color:  #dcdcdc");
        });

        leftLinkPane.setOnDragDetected(linkDragDetected);
        leftLinkPane.setOnDragDropped(linkDragDropped);
        rightLinkPane.setOnDragDetected(linkDragDetected);
        rightLinkPane.setOnDragDropped(linkDragDropped);

        link.setVisible(false);

        parentProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                superParent = (AnchorPane) getParent();
            }
        });
    }

    void setType(DragType type) {
        dragType = type;

        switch (dragType) {
            case BLOOM:
                effect = ()-> {
                    Bloom bloom = new Bloom(0.2);
                    content.setEffect(bloom);
                    Graph.addPrevEffects(getId());
                    for(var effect : prevEffects) {
                        bloom.setInput(effect.getValue());
                    }

                    prevEffects.add(new Pair<>(getId(), bloom));
                };

                effectName.setText("Цветение");
                break;
            case SepiaTone:
                effect = ()-> {
                    SepiaTone sepiaTone = new SepiaTone();
                    Graph.addPrevEffects(getId());
                    for(var effect : prevEffects) {
                        sepiaTone.setInput(effect.getValue());
                    }
                    content.setEffect(sepiaTone);
                    prevEffects.add(new Pair<>(getId(), sepiaTone));
                };
                effectName.setText("Сепия");
                break;
            case MOTIONBLUR:
                effect = ()-> {
                    MotionBlur motionBlur = new MotionBlur();
                    motionBlur.setRadius(10.5);
                    motionBlur.setAngle(45);
                    Graph.addPrevEffects(getId());
                    for(var effect : prevEffects) {
                        motionBlur.setInput(effect.getValue());
                    }
                    content.setEffect(motionBlur);
                    prevEffects.add(new Pair<>(getId(), motionBlur));
                };
                effectName.setText("Блюр");
                break;

            case BLACKANDWHITE:
                effect = ()-> {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setSaturation(-1);
                    Graph.addPrevEffects(getId());
                    for(var effect : prevEffects) {
                        colorAdjust.setInput(effect.getValue());
                    }
                    content.setEffect(colorAdjust);
                    prevEffects.add(new Pair<>(getId(), colorAdjust));
                };
                effectName.setText("Черно-белое");
                break;

            case Reflection:
                effect = ()-> {
                    System.out.println("рефлексия");
                    Reflection reflection = new Reflection();
                    Graph.addPrevEffects(getId());
                    for(var effect : prevEffects) {
                        reflection.setInput(effect.getValue());
                    }
                    content.setEffect(reflection);
                    prevEffects.add(new Pair<>(getId(), reflection));
                };
                effectName.setText("Отражение");
                break;

            case ENDNODE:
                effectName.setText("Результат");
                deleteButton.setVisible(false);

                effect = ()-> {

                    // этот эффект не сработает, переменная нужна только в качестве контейнера
                    Bloom resEffect = new Bloom(1);
                    Graph.addPrevEffects(getId());
                    for (var effect : prevEffects) {
                        resEffect.setInput(effect.getValue());
                    }
                    content.setEffect(resEffect);

                };
                break;

            case STARTNODE:
                deleteButton.setVisible(false);
                effectName.setText("Изображение");
                break;

        }
    }

    public void registerLink(String linkId) {
        linkIds.add(linkId);
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
        });
    }

    void linkHandlers() {
        linkDragDetected = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(dragType == DragType.ENDNODE)
                    return;
                if(checkLine)
                    return;

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

                container.addData("source", getId());
                content.put(DragContainer.AddLink, container);
                startDragAndDrop (TransferMode.ANY).setContent(content);
                mouseEvent.consume();
            }
        };

        linkDragDropped = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                getParent().setOnDragOver(null);
                getParent().setOnDragDropped(null);

                DragContainer container = (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);
                if (container == null)
                    return;

                link.setVisible(false);
                superParent.getChildren().remove(0);

                ClipboardContent content = new ClipboardContent();
                container.addData("target", getId());
                content.put(DragContainer.AddLink, container);
                event.getDragboard().setContent(content);

                String sourceId = container.getValue("source");
                String targetId = container.getValue("target");
                if (sourceId != null && targetId != null) {
                    Graph.data.add(new Pair<>(sourceId, targetId));

                    try {
                        var link = new NodeLink();
                        superParent.getChildren().add(0, link);

                        DraggableNode source = null;
                        DraggableNode target = null;

                        for (Node n : superParent.getChildren()) {

                            if (n.getId() == null)
                                continue;

                            if (n.getId().equals(sourceId))
                                source = (DraggableNode) n;

                            if (n.getId().equals(targetId))
                                target = (DraggableNode) n;

                        }

                        if (source != null && target != null && target.getId() != Graph.startNode) {
                            link.bindStartEnd(source, target);
                            source.checkLine = true;
                            source.registerLink(link.getId());
                            registerLink(link.getId());
                            if(effect != null) {
                                Graph.detoir();
                            }

                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }

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
