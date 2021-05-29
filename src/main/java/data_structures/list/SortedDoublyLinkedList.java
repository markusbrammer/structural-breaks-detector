package data_structures.list;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * A class to represent the doubly linked list data structure.
 * A lot of inspiration from these sides:
 *  - https://www.javatpoint.com/doubly-linked-list-program-in-java
 *  - https://dzone.com/articles/creating-a-custom-linked-list-implementation
 * @param <E> A comparable data type. Needed for sorting.
 */
public class SortedDoublyLinkedList<E extends Comparable<E>>
        implements Iterable<E> {

    int length;
    Node<E> head;
    Node<E> tail;

    public SortedDoublyLinkedList() {
        length = 0;
        head = null;
        tail = null;
    }

    public void add(E element) {

        Node<E> newNode = new Node<>(element);

        if (head == null) {
            // The new node is the only element in the list
            head = newNode;
            tail = newNode;
        } else {
            // Add the node to the list that already contains other nodes
            addRec(newNode, head);
        }

        length++;

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

    public void remove(E element) {
        Node<E> node = firstNodeWithElement(element);
        if (node != null) {

            Node<E> prevNode = node.getPrev();
            Node<E> nextNode = node.getNext();

            if (prevNode != null) {
                prevNode.setNext(nextNode);
            } else {
                head = nextNode;
            }

            if (nextNode != null) {
                nextNode.setPrev(prevNode);
            } else {
                tail = prevNode;
            }

        }
    }

    public E getNextElement(E element) {
        // Find node with the element
        Node<E> node = firstNodeWithElement(element);
        if (node != null) {
            Node<E> nextNode = node.getNext();
            return nextNode == null ? null : nextNode.getElement();
        }

        return null;
    }

    private Node<E> firstNodeWithElement(E element) {
        Node<E> node = head;
        while (node != null) {
            if (node.getElement().compareTo(element) == 0) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    public Stream<E> stream() {
        Stream.Builder<E> builder = Stream.builder();
        this.forEach(builder::add);
        return builder.build();
    }

    public E getHeadElement() {
        return head.getElement();
    }

    public E getTailElement() {
        return tail.getElement();
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (E e : this) {
            s.append(e).append(" ");
        }
        return s.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator<>(head);
    }
}
