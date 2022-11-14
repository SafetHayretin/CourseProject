package CourseProject.DAO;

public class Node<V> {
     int currentReads;

     int previousRead;

     final V element;

     Node(V element) {
        this.element = element;
    }

     int getReads() {
        return currentReads + previousRead;
    }

     void changeGeneration() {
        previousRead = currentReads;
        currentReads = 0;
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Node)) {
            return false;
        }

        Node<V> n = (Node<V>) o;

        return element.equals(n.element);
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
