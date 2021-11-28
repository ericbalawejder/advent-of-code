package aoc.day23;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CircularLinkedList {

    private final Map<Integer, Node> nodeByLabel = new LinkedHashMap<>();
    private Node current;

    public CircularLinkedList(List<Integer> elements) {
        Node prev = null;
        Node first = null;
        for (Integer element : elements) {
            Node node = new Node();
            node.value = element;
            node.prev = prev;
            nodeByLabel.put(element, node);
            if (prev != null) {
                prev.next = node;
            } else {
                first = node;
            }
            prev = node;
        }
        prev.next = first;
        first.prev = prev;
        this.current = first;
    }

    public int getCurrentValue() {
        return current.value;
    }

    public void next() {
        current = current.next;
    }

    public List<Integer> getLabels(int nodes) {
        final List<Integer> labels = new ArrayList<>(nodes);
        Node node = current;
        for (int i = 0; i < nodes; i++) {
            node = node.next;
            labels.add(node.value);
        }
        return List.copyOf(labels);
    }

    public Node getCurrentNode() {
        return current;
    }

    public void insertAfter(Node s1, Node s2, int dest) {
        Node destination = nodeByLabel.get(dest);
        Node oldNext = destination.next;
        s1.prev.next = s2.next;
        s2.next.prev = s1.prev;
        destination.next = s1;
        s2.next = oldNext;
        oldNext.prev = s2;
        s1.prev = destination;
    }

    public void setCurrent(int value) {
        current = nodeByLabel.get(value);
    }

    public int size() {
        return nodeByLabel.size();
    }

    @Override
    public String toString() {
        return nodeByLabel + "\n" + "current= " + current;
    }

    public static final class Node {
        public int value;
        public Node prev;
        public Node next;

        @Override
        public String toString() {
            return String.valueOf(value);
        }

    }

}
