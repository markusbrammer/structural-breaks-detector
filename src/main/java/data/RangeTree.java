package data;

/**
 * Trees in Java, see this: https://stackoverflow.com/questions/8480284/storing-a-tree-structure-in-java
 *
 * The range tree is made with generic data types. It would make more sense to
 * just use numeric types, but I enjoyed the challenge.
 */
public class RangeTree {

    Vertex root;
    double[] values;

    public RangeTree(double[] values) {
        this.values = values;
        root = new Vertex();
        generateTree(root, 0, values.length - 1);
    }

    private void generateTree(Vertex vertex, int leftIndex, int rightIndex) {

        vertex.leftKey = leftIndex;
        vertex.rightKey = rightIndex;

        if (!vertex.isLeaf()) {

            int separatorIndex = (leftIndex + rightIndex) / 2;

            Vertex leftChild = new Vertex();
            vertex.left = leftChild;
            generateTree(leftChild, leftIndex, separatorIndex);

            Vertex rightChild = new Vertex();;
            vertex.right = rightChild;
            generateTree(rightChild, separatorIndex + 1, rightIndex);

            // Min and max is calculated from the children
            vertex.minMax = MinMax.combine(leftChild.minMax, rightChild.minMax);

        } else {

            double value = values[leftIndex];
            vertex.minMax = new MinMax(value, value);

        }

    }

    public MinMax getMinMax(int leftKey, int rightKey) throws Exception {
        return getMinAndMaxHelper(leftKey, rightKey, root);
    }

    private MinMax getMinAndMaxHelper(int a, int b, Vertex x) throws Exception {

        if (x.isLeaf())
            return x.minMax;

        // The two paths are following each other, ie. this node is on both paths.
        if (x.left.inInterval(a) && x.left.inInterval(b)) {
            return getMinAndMaxHelper(a, b, x.left);
        } else if (x.right.inInterval(a) && x.right.inInterval(b)) {
            return getMinAndMaxHelper(a, b, x.right);
        }

        // The two paths are splitting up AT THIS NODE
        if (x.left.inInterval(a) && x.right.inInterval(b)) {
            return MinMax.combine(getMinAndMaxHelper(a, b, x.left),
                                  getMinAndMaxHelper(a, b, x.right));
        }

        // The two paths have already split apart. This means that the current
        // node is only on the path to ONE of the two endpoints, either the left
        // path or the right path.
        if (x.left.inInterval(a)) {
            return MinMax.combine(getMinAndMaxHelper(a, b, x.left), x.right.minMax);
        } else if (x.right.inInterval(a)) {
            return getMinAndMaxHelper(a, b, x.right);
        }

        if (x.right.inInterval(b)) {
            return MinMax.combine(getMinAndMaxHelper(a, b, x.right), x.left.minMax);
        } else if (x.left.inInterval(b)) {
            return getMinAndMaxHelper(a, b, x.left);
        }

        // If this is reached then the interval is not valid
        throw new Exception("The interval is not valid");
    }

    private class Vertex {

        Vertex left;
        Vertex right;

        int leftKey;
        int rightKey;

        MinMax minMax;

        public void setMinMax(MinMax minMax) {
            this.minMax = minMax;
        }

        boolean isLeaf() {
            return leftKey == rightKey;
        }

        boolean inInterval(int key) {
            return key >= leftKey && key <= rightKey;
        }

        @Override
        public String toString() {
            if (isLeaf()) {
                return "[" + leftKey + "]";
            }
            return "[" + leftKey + ", " + rightKey + "]";
        }

    }

}
