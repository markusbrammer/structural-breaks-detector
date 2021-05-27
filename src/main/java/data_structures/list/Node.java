package data_structures.list;


public class Node<E extends Comparable<E>> implements Comparable<Node<E>> {

    private E element;
    private Node<E> prev;
    private Node<E> next;

    public Node(E element) {
        this.element = element;
        prev = null;
        next = null;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public E getElement() {
        return element;
    }

    @Override
    public int compareTo(Node<E> node) {
        return element.compareTo(node.getElement());
    }

    @Override
    public String toString() {
        return element.toString();
    }
}


