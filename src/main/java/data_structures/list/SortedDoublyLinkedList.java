package data_structures.list;

import java.util.Iterator;

/**
 * A class to represent the doubly linked list data structure.
 * A lot of inspiration from these sides:
 *  - https://www.javatpoint.com/doubly-linked-list-program-in-java
 *  - https://dzone.com/articles/creating-a-custom-linked-list-implementation
 * @param <E> A comparable data type. Needed for sorting.
 */
public class SortedDoublyLinkedList<E extends Comparable<E>>
        implements Iterable<Node<E>>{

    Node<E> head;
    Node<E> tail;

    public SortedDoublyLinkedList() {
        head = null;
        tail = null;
    }

    public void add(E element) {

        Node<E> newNode = new Node<>(element);

        // The new node is the only element in the list
        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }

        // Add the node to the list that already contains other nodes
        addRec(newNode, head);
    }

    /**
     * Add a new node to the doubly linked list. Recursively tries to place the
     * new node before the current node.
     * @param newNode The node to be placed into the linked list.
     * @param currentNode Initialize at head. Called recursively in the method.
     */
    private void addRec(Node<E> newNode, Node<E> currentNode) {

        // The element has the highest value and is placed at the tail
        if (currentNode == null) {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            return;
        }

        // The current node's value is smaller or equal to the new node's value.
        // Place the new node before the current node. If the current node is
        // the head, make the new node the head in stead.
        if (newNode.compareTo(currentNode) <= 0) {
            Node<E> prevNode = currentNode.getPrev();
            if (prevNode == null) {
                head = newNode;
            } else {
                prevNode.setNext(newNode);
                newNode.setPrev(prevNode);
            }
            currentNode.setPrev(newNode);
            newNode.setNext(currentNode);
            return;
        }

        // Recursive call: Insert before the next node
        addRec(newNode, currentNode.getNext());

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Node<E> n : this) {
            s.append(n).append(" ");
        }
        return s.toString();
    }

    @Override
    public Iterator<Node<E>> iterator() {
        return new ListIterator<>(head);
    }
}
