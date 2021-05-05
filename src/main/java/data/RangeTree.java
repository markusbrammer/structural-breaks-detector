package data;

/**
 * Trees in Java, see this: https://stackoverflow.com/questions/8480284/storing-a-tree-structure-in-java
 */
public class RangeTree {

    Node root;

    // TODO Make methods to build range trees (build up from the bottom?)

    private class Node {

        Node left;
        Node right;

        double leftRange;
        double rightRange;

        // Max and min values for range [leftRange, rightRange]. Used for querying.
        double min;
        double max;

    }

}


