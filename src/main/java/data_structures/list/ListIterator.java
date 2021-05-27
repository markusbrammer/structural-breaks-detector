package data_structures.list;

import java.util.Iterator;

/**
 * A lot of inspiration from here:
 * https://dzone.com/articles/creating-a-custom-linked-list-implementation
 * @param <E>
 */
public class ListIterator<E extends Comparable<E>> implements Iterator<Node<E>> {

    private Node<E> node;

    public ListIterator(Node<E> headNode) {
        node = headNode;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public Node<E> next() {
        Node<E> node1 = node;
        node = node.getNext();
        return node1;
    }

}
