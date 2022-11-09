package CourseProject.DataBase;

import java.util.Iterator;

public class CircularLinkedList<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new CustomIterator<T>();
    }

    private class CustomIterator<E> implements Iterator<E> {
        Node customTail;
        Node customHead;
        int indexPos = 0;

        CustomIterator() {
            this.customHead = head;
            this.customTail = tail;
        }

        @Override
        public boolean hasNext() {
            if (size() >= indexPos + 1)
                return true;

            return false;
        }

        @Override
        public E next() {
            E val = (E) head.value;
            customTail = customHead;
            customHead = customHead.nextNode;
            return val;
        }
    }

    public class Node {
        public T value;
        public Node nextNode;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }

    public Node head = null;

    public Node tail = null;

    private int count = 0;

    public void addNode(T value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
        } else {
            tail.nextNode = newNode;
        }

        tail = newNode;
        tail.nextNode = head;
        count++;
    }

    public void deleteNode(T valueToDelete) {
        Node currentNode = head;
        if (head == null) { // the list is empty
            return;
        }
        do {
            Node nextNode = currentNode.nextNode;
            if (nextNode.value == valueToDelete) {
                if (tail == head) { // the list has only one single element
                    head = null;
                    tail = null;
                } else {
                    currentNode.nextNode = nextNode.nextNode;
                    if (head == nextNode) { //we're deleting the head
                        head = head.nextNode;
                    }
                    if (tail == nextNode) { //we're deleting the tail
                        tail = currentNode;
                    }
                }
                break;
            }
            currentNode = nextNode;
        } while (currentNode != head);
        count--;
    }

    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
