package data;

/**
 * A range tree data structure for the time series
 */
public class RangeTree {

    Vertex root;
    double[] values;

    public RangeTree(double[] values) {
        this.values = values;
        root = new Vertex();
        generateTree(root, 0, values.length - 1);
    }

    /**
     * Generate a range tree from a vertex, a left index, and a rightIndex.
     * @param vertex When calling the method: The root. Is called recursively
     *              in the method itself
     * @param leftIndex The lower bound for the index range
     * @param rightIndex The upper bound for the index range
     */
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

            // The vertex is a leaf, so leftIndex = rightIndex
            double value = values[leftIndex];
            vertex.minMax = new MinMax(value, value);

        }

    }


    /**
     * Get the minimum and maximum value in an interval
     * @param leftKey The lower bound for the query index
     * @param rightKey The upper bound for the query index
     * @return The minimum and maximum as a MinMax object
     * @throws Exception The interval for the query is not correct
     */
    public MinMax getMinMax(int leftKey, int rightKey) throws Exception {
        return getMinAndMaxHelper(leftKey, rightKey, root);
    }

    /**
     * A recursive function for getting the min max in an interval. Called
     * helper because it is called from the method getMinMax which is public.
     * @param a Lower bound for interval query
     * @param b Upper bound for interval query
     * @param x The vertex. Choose root when initializing, updated recursively
     * @return The minimum and maximum as a MinMax object
     * @throws Exception The interval for the query is not correct/valid.
     */
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

    /**
     * A simple vertex object for the range tree
     */
    private class Vertex {

        Vertex left;
        Vertex right;

        int leftKey;
        int rightKey;

        MinMax minMax;

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
