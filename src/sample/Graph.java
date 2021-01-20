package sample;

import javafx.scene.effect.Bloom;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;


class Graph {

    public static ArrayList<Pair<String, String>> data = new ArrayList<Pair<String, String>>();
    public static LinkedList<DraggableNode> nodes = new LinkedList<DraggableNode>();


    static public String startNode;
    static public String endNode;

    void addNode(String firstId, String secondId) {
        data.add(new Pair<>(firstId, secondId));
    }

    static void detoir() {
        String tempNode = startNode;
        while(tempNode != endNode) {

            boolean check = false;
            for (Pair<String, String> node : data) {
                if (node.getKey().equals(tempNode)) {
                    tempNode = node.getValue();
                    check = true;
                    break;
                }
            }

            if(!check)
                break;

            for(var node : nodes) {
                if(node.getId().equals(tempNode)) {
                    //TODO
                    //node.content.setEffect(new Bloom(0.2));
                    //node.content.setText(tempNode);
                    //new Filter("RED");
                }
            }
        }

    }
}