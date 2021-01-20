package sample;

import javafx.scene.effect.Bloom;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
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
                    if(!node.checkEffect) {
                        node.effect.effect();
                        node.checkEffect = true;
                    }
                }
            }
        }

    }

    static void deleteNode(String id) {
        System.out.println(data);
        Iterator<Pair<String, String>> it = data.iterator();
        while (it.hasNext()) {
            Pair<String, String> relationship = it.next();
            if(relationship.getKey().equals(id) || relationship.getValue().equals(id))
                it.remove();
        }
    }

    static DraggableNode searchById(String id) {
        for(var node : nodes) {
            if(node.getId().equals(id))
                return node;
        }
        return null;
    }

    static void deleteCheckLine(String id) {
        for(var relationship : data) {
            if(relationship.getValue().equals(id)) {
                searchById(relationship.getKey()).checkLine = false;
            }
        }
    }

    static void  addPrevEffects(String id) {
        for(var relationship : data) {
            if(relationship.getValue().equals(id)) {
                searchById(relationship.getValue()).prevEffects.addAll(searchById(relationship.getKey()).prevEffects);
            }
        }
    }
}