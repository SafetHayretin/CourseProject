package CourseProject.DAO;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class GenerationalCachePQ<E> {

    private final int capacity;

    private final PriorityQueue<Node<E>> elements;

    private int readCounter = 0;

    public GenerationalCachePQ(int capacity) {
        Comparator<Node<E>> comparator = (o1, o2) -> {
            Integer o1reads = o1.getReads();
            Integer o2Reads = o2.getReads();

            return o1reads.compareTo(o2Reads);
        };

        this.capacity = capacity;
        elements = new PriorityQueue<>(capacity, comparator);
    }

    public void set(E value) {
        Node<E> newNode = new Node<>(value);
        if (!elements.contains(newNode) && elements.size() == capacity) {
            elements.poll();
            elements.offer(newNode);
            return;
        }
        elements.removeIf(eNode -> eNode.equals(newNode));
        elements.offer(newNode);
    }


    public E get(int index) {
        int READS_PER_GENERATION = 20;
        if (readCounter == READS_PER_GENERATION) {
            changeGeneration();
        }

        Node<E> node = findElement(index);

        if (node == null)
            return null;

        node.currentReads++;
        readCounter++;

        return node.element;
    }

    private Node<E> findElement(int index) {
        if (index < 0 || index >= elements.size())
            throw new IllegalArgumentException("Wrong index!");

        Iterator<Node<E>> iterator = elements.iterator();

        for (int i = 0; i < index; i++) {
            iterator.next();
        }

        return iterator.next();
    }

    private void changeGeneration() {
        for (Node<E> n : elements) {
            n.changeGeneration();
        }
        readCounter = 0;
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    public int size() {
        return elements.size();
    }
}
