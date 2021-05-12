package data;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Trees in Java, see this: https://stackoverflow.com/questions/8480284/storing-a-tree-structure-in-java
 *
 * The range tree is made with generic data types. It would make more sense to
 * just use numeric types, but I enjoyed the challenge.
 */
public class RangeTree {

    Node root;
    // double[] keys;
    double[] values;

    public RangeTree(double[] values) {
        // changed from RangeTree(double[] keys, double[] values) because keys are now their index in stead
        this.values = values;
        root = new Node();
        generateTree(root, 0, values.length - 1);
    }

    private void generateTree(Node node, int leftIndex, int rightIndex) {

        node.leftKey = leftIndex;
        node.rightKey = rightIndex;

        if (!node.isLeaf()) {

            int separatorIndex = (leftIndex + rightIndex) / 2;

            Node leftChild = new Node();
            node.left = leftChild;
            generateTree(leftChild, leftIndex, separatorIndex);

            Node rightChild = new Node();;
            node.right = rightChild;
            generateTree(rightChild, separatorIndex + 1, rightIndex);

            assignMinMax(node);

        } else {

            double value = values[leftIndex];
            node.min = value;
            node.max = value;

        }

    }

    public MinMax getMinMax(double minKey, double maxKey) {

        Queue<Node> pathToMin = pathToNode(root, minKey, new LinkedList<>());
        Queue<Node> pathToMax = pathToNode(root, maxKey, new LinkedList<>());

        MinMax minMax = new MinMax();
        minMax.merge(calcLeftPath(pathToMin, maxKey));
        minMax.merge(calcRightPath(pathToMax, minKey));

        return minMax;

    }

    private MinMax calcLeftPath(Queue<Node> pathToMin, double maxKey) {

        MinMax minMax = new MinMax();
        while (!pathToMin.isEmpty()) {

            Node node = pathToMin.poll();

            if (node.isLeaf()) {
                minMax.merge(new MinMax(node.min, node.max));
            } else {

                Node nextNode = pathToMin.peek();

                if (nextNode.rightKey < node.rightKey) {
                    // The next node is left child. Count right in min-max
                    Node rightChild = node.right;
                    if (rightChild.rightKey <= maxKey)
                        minMax.merge(new MinMax(rightChild.min, rightChild.max));
                }
            }

        }
        return minMax;

    }

    private MinMax calcRightPath(Queue<Node> pathToMax, double minKey) {

        MinMax minMax = new MinMax();
        while (!pathToMax.isEmpty()) {

            Node node = pathToMax.poll();

            if (node.isLeaf()) {
                minMax.merge(new MinMax(node.min, node.max));
            } else {
                Node nextNode = pathToMax.peek();

                if (nextNode.leftKey > node.leftKey) {
                    // The next node is right child. Count left in min-max
                    Node leftChild = node.left;
                    if (leftChild.leftKey >= minKey)
                        minMax.merge(new MinMax(leftChild.min, leftChild.max));
                }
            }

        }
        return minMax;

    }


    private Queue<Node> pathToNode(Node node, double destinationKey, Queue<Node> path) {

        Node leftNode = node.left;
        Node rightNode = node.right;

        path.add(node);

        if (node.isLeaf())
            return path;

        if (destinationKey >= leftNode.leftKey && destinationKey <= leftNode.rightKey) {
            return pathToNode(leftNode, destinationKey, path);
        } else if (destinationKey >= rightNode.leftKey && destinationKey <= rightNode.rightKey) {
            return pathToNode(rightNode, destinationKey, path);
        }

        return null;
    }


    private void assignMinMax(Node node) {

        double leftMin = node.left.min;
        double leftMax = node.left.max;
        double rightMin = node.right.min;
        double rightMax = node.right.max;

        double minVal = Math.min(leftMin, rightMin);
        double maxVal = Math.max(leftMax, rightMax);

        node.min = minVal;
        node.max = maxVal;

    }

    private class Node {

        Node left;
        Node right;

        double leftKey;
        double rightKey;

        // Max and min values for range [leftKey, rightKey]. Used for querying.
        double min;
        double max;

        boolean isLeaf() {
            return leftKey == rightKey;
        }

        @Override
        public String toString() {
            return "[" + min + ", " + max + "]";
        }

    }

}

//package data;
//
///**
// * Trees in Java, see this: https://stackoverflow.com/questions/8480284/storing-a-tree-structure-in-java
// *
// * The range tree is made with generic data types. It would make more sense to
// * just use numeric types, but I enjoyed the challenge.
// */
//public class RangeTree<K, V extends Comparable<V>> {
//
//    Node root;
//    K[] keys;
//    V[] values;
//
//    public RangeTree(K[] keys, V[] values) {
//        this.keys = keys;
//        this.values = values;
//        root = new Node();
//        generateTree(root, 0, keys.length - 1);
//    }
//
//    private void generateTree(Node node, int leftIndex, int rightIndex) {
//
//        node.leftKey = keys[leftIndex];
//        node.rightKey = keys[rightIndex];
//
//        if (node.leftKey.equals(node.rightKey)) {
//
//            Node leftChild = new Node();
//            int leftChildRightIndex = (leftIndex + rightIndex) / 2;
//            node.left = leftChild;
//            generateTree(leftChild, leftIndex, leftChildRightIndex);
//
//            Node rightChild = new Node();
//            int rightChildLeftIndex = (int) Math.ceil((leftIndex + rightIndex) / 2.);
//            node.right = rightChild;
//            generateTree(rightChild, rightChildLeftIndex, rightIndex);
//
//            assignMinMax(node);
//
//        } else {
//
//            V value = values[leftIndex];
//            node.min = value;
//            node.max = value;
//
//        }
//
//    }
//
//    public MinMax<V> getMinMax(K minKey, K maxKey) {
//
//        return getMinMaxHelper(root, minKey, maxKey);
//
//    }
//
//    private MinMax<V> getMinMaxHelper(Node node, K minKey, K maxKey) {
//
//
//
//    }
//
//    private void assignMinMax(Node node) {
//
//        V leftMin = node.left.min;
//        V leftMax = node.left.max;
//        V rightMin = node.right.min;
//        V rightMax = node.right.max;
//
//        V minVal = (leftMin.compareTo(rightMin) < 0 ? leftMin : rightMin);
//        V maxVal = (leftMax.compareTo(rightMax) > 0 ? leftMax : rightMax);
//
//        node.min = minVal;
//        node.max = maxVal;
//
//    }
//
//    // TODO Make methods to build range trees (build up from the bottom?)
//
//    private class Node {
//
//        Node left;
//        Node right;
//
//        K leftKey;
//        K rightKey;
//
//        // Max and min values for range [leftKey, rightKey]. Used for querying.
//        V min;
//        V max;
//
//    }
//
//}

